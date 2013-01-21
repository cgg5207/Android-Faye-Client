package com.moneydesktop.finance.tablet.fragment;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.moneydesktop.finance.ApplicationContext;
import com.moneydesktop.finance.BaseFragment;
import com.moneydesktop.finance.R;
import com.moneydesktop.finance.animation.AnimationFactory.FlipDirection;
import com.moneydesktop.finance.animation.FlipXAnimation;
import com.moneydesktop.finance.data.Constant;
import com.moneydesktop.finance.database.Transactions;
import com.moneydesktop.finance.model.EventMessage;
import com.moneydesktop.finance.shared.FilterViewHolder;
import com.moneydesktop.finance.tablet.adapter.FilterTabletAdapter;
import com.moneydesktop.finance.tablet.fragment.TransactionsDetailTabletFragment.onBackPressedListener;
import com.moneydesktop.finance.views.UltimateListView;

import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

@TargetApi(11)
public class TransactionsTabletFragment extends BaseFragment implements onBackPressedListener, OnChildClickListener {
	
	public final String TAG = this.getClass().getSimpleName();
	
	private static final int MOVE_DURATION = 400;
	
	private RelativeLayout mContainer;
	private FrameLayout mDetail;
	private ImageView mFakeCell;
	private View mCellView;
	private int mCenterX, mCellX, mCellY, mHeight;
	private TransactionsDetailTabletFragment mDetailFragment;
	
    private UltimateListView mFiltersList;
    private FilterTabletAdapter mAdapter;
	
	private boolean mStartFix = true;
	
	private AnimatorListener mListenerShow = new AnimatorListener() {
        
        @Override
        public void onAnimationStart(Animator animation) {}
        
        @Override
        public void onAnimationRepeat(Animator animation) {}
        
        @Override
        public void onAnimationEnd(Animator animation) {

            Animation cellFlip = flipCell(true);
            Animation detailFlip = flipDetailView(true);
            detailFlip.setAnimationListener(mShowFinished);

            mFakeCell.startAnimation(cellFlip);
            
            mDetail.setVisibility(View.VISIBLE);
            mDetail.startAnimation(detailFlip);
        }
        
        @Override
        public void onAnimationCancel(Animator animation) {}
    };
    
    private AnimationListener mShowFinished = new AnimationListener() {
        
        @Override
        public void onAnimationStart(Animation animation) {}
        
        @Override
        public void onAnimationRepeat(Animation animation) {}
        
        @Override
        public void onAnimationEnd(Animation animation) {
            
            mFakeCell.setVisibility(View.INVISIBLE);
            
            mAnimating = false;
        }
    };
    
    private AnimationListener mListenerHide = new AnimationListener() {
        
        @Override
        public void onAnimationStart(Animation animation) {}
        
        @Override
        public void onAnimationRepeat(Animation animation) {}
        
        @Override
        public void onAnimationEnd(Animation animation) {

            mDetail.setVisibility(View.INVISIBLE);
            
            AnimatorSet set = moveCell(false);
            set.addListener(mHideFinished);
            
            set.start();
        }
    };
    
    private AnimatorListener mHideFinished = new AnimatorListener() {
        
        @Override
        public void onAnimationStart(Animator animation) {}
        
        @Override
        public void onAnimationRepeat(Animator animation) {}
        
        @Override
        public void onAnimationEnd(Animator animation) {
            
            mContainer.setVisibility(View.INVISIBLE);
            mFakeCell.setVisibility(View.INVISIBLE);
            mCellView.setVisibility(View.VISIBLE);
            
            mAnimating = false;
        }
        
        @Override
        public void onAnimationCancel(Animator animation) {}
    };
	
	private Interpolator mInterpolator = new Interpolator() {
        
        @Override
        public float getInterpolation(float t) {
            if ((t) < (1 / 2.75)) {
                return (7.5625f * t * t);
            } else if (t < (2 / 2.75)) {
                return (7.5625f * (t -= (1.5f / 2.75f)) * t + .75f);
            } else if (t < (2.5 / 2.75)) {
                return (7.5625f * (t -= (2.25f / 2.75f)) * t + .9375f);
            } else {
                return (7.5625f * (t -= (2.625f / 2.75f)) * t + .984375f);
            }
        }
    };
	
	private boolean mAnimating = false;
	
	public static TransactionsTabletFragment newInstance() {
			
	    TransactionsTabletFragment fragment = new TransactionsTabletFragment();
	
        Bundle args = new Bundle();
        fragment.setArguments(args);
        
        return fragment;
	}
	
	public void setDetailFragment(TransactionsDetailTabletFragment fragment) {
	    mDetailFragment = fragment;
	    mDetailFragment.setListener(this);
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity.onFragmentAttached(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();

        this.mActivity.updateNavBar(getFragmentTitle());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mRoot = inflater.inflate(R.layout.tablet_transactions_view, null);
		
		setupView();
		setupFilterList();
		
		TransactionsPageTabletFragment frag = TransactionsPageTabletFragment.newInstance();
      
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, frag);
        ft.commit();
        
        setDetailFragment(TransactionsDetailTabletFragment.newInstance());

        ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment, mDetailFragment);
        ft.commit();
		
		return mRoot;
	}
	
	private void setupView() {

        mFakeCell = (ImageView) mRoot.findViewById(R.id.cell);
	    mContainer = (RelativeLayout) mRoot.findViewById(R.id.detail_container);
	    mDetail = (FrameLayout) mRoot.findViewById(R.id.detail_fragment);
	    
	    if (ApplicationContext.isLargeTablet()) {
	        mDetail.getLayoutParams().width *= Constant.LARGE_TABLET_SCALE;
            mDetail.getLayoutParams().height *= Constant.LARGE_TABLET_SCALE;
	    }
	    
	    mContainer.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                configureDetailView();
            }
        });
	    
        mFiltersList = (UltimateListView) mRoot.findViewById(R.id.filters);
        mFiltersList.setDividerHeight(0);
        mFiltersList.setDivider(null);
        mFiltersList.setChildDivider(null);
	}
	
	private void setupFilterList() {

	    List<Pair<String, List<FilterViewHolder>>> data = new ArrayList<Pair<String, List<FilterViewHolder>>>();
        
        for (int j = 0; j < Constant.FILTERS.length; j++) {

            List<FilterViewHolder> subItems = new ArrayList<FilterViewHolder>();
            
            if (j == 0) {

                for (int i = 0; i < Constant.FOLDER_TITLE.length; i++) {
                    FilterViewHolder holder = new FilterViewHolder();
                    holder.mText = getString(Constant.FOLDER_TITLE[i]);
                    holder.mSubText = getString(Constant.FOLDER_SUBTITLE[i]);
                    holder.mQuery = Constant.FOLDER_QUERIES[i];
                    subItems.add(holder);
                }
            }
            
            Pair<String, List<FilterViewHolder>> temp = new Pair<String, List<FilterViewHolder>>(getString(Constant.FILTERS[j]), subItems);
            data.add(temp);
        }
        
        mAdapter = new FilterTabletAdapter(mActivity, mFiltersList, data);
        mAdapter.setAutomaticSectionLoading(true);
        mFiltersList.setAdapter(mAdapter);
        mFiltersList.setOnChildClickListener(this);
        mFiltersList.setSelectedChild(0, 0, true);
	}
	
	public void showTransactionDetails(View view, int offset, Transactions transaction) {
	    
	    if (mDetailFragment != null) {
	        mDetailFragment.updateTransaction(transaction);
	    }
	    
	    mCellView = view;
	    
        int[] location = new int[2];
        mCellView.getLocationOnScreen(location);
        
        mHeight = view.getHeight();
        
        mCellY = (location[1] - offset);
        mCellX = location[0];
        
        mCenterX = (int) (view.getWidth() / 2.0f);
	    
        Bitmap b = Bitmap.createBitmap(mCellView.getWidth(), mCellView.getHeight(), Bitmap.Config.ARGB_8888);                
        Canvas c = new Canvas(b);
        mCellView.layout(0, 0, mCellView.getLayoutParams().width, mCellView.getLayoutParams().height);
        mCellView.draw(c);
        
        mFakeCell.setImageBitmap(b);

        mFakeCell.setX(mCellX);
        mFakeCell.setY(mCellY);
	    
	    // Fix for some sort of initialization delay
            
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            
            @Override
            public void run() {

                configureDetailView();
            }
        }, 100);
	}
	
	public void configureDetailView() {
	    
	    if (mAnimating) {
	        return;
	    }
	    
	    if (mCellView.getVisibility() == View.VISIBLE) {

            mAnimating = true;
            
            final AnimatorSet set = moveCell(true);
            set.addListener(mListenerShow);
            
            mCellView.setVisibility(View.INVISIBLE);
            mFakeCell.setVisibility(View.VISIBLE);
            mContainer.setVisibility(View.VISIBLE);

            set.start();
            
        } else {

            mAnimating = true;
            
            if (mDetailFragment != null) {
                mDetailFragment.updateTransaction(null);
            }
            
            Animation cellFlip = flipCell(false);
            Animation detailFlip = flipDetailView(false);
            detailFlip.setAnimationListener(mListenerHide);
            
            mFakeCell.setVisibility(View.VISIBLE);
            mFakeCell.startAnimation(cellFlip);
            mDetail.startAnimation(detailFlip);
        }
	}
	
	private AnimatorSet moveCell(final boolean out) {
	    
	    final float startY = out ? mCellY : mDetail.getY() - mHeight;
	    final float endY = out ? mDetail.getY() - mHeight : mCellY;
        
        final float startX = out ? mCellX : mDetail.getX();
        final float endX = out ? mDetail.getX() : mCellX;
        
        final float startAlpha = out ? 0 : 1;
        final float endAlpha = out ? 1 : 0;
        
        final ObjectAnimator moveY = ObjectAnimator.ofFloat(mFakeCell, "y", startY, endY);
        moveY.setInterpolator(new DecelerateInterpolator(3));
        moveY.setDuration(MOVE_DURATION);
        final ObjectAnimator moveX = ObjectAnimator.ofFloat(mFakeCell, "x", startX, endX);
        moveX.setInterpolator(new DecelerateInterpolator(3));
        moveY.setDuration(MOVE_DURATION);
        final ObjectAnimator fade = ObjectAnimator.ofFloat(mContainer, "alpha", startAlpha, endAlpha);
        fade.setDuration(out ? 300 : 400);
        
        final AnimatorSet set = new AnimatorSet();
        set.play(moveX).with(moveY).with(fade);
        
        return set;
	}
	
	private Animation flipCell(boolean up) {
	    
	    int duration = up ? 800 : 375;
	    
	    FlipDirection dir = up ? FlipDirection.TOP_BOTTOM : FlipDirection.BOTTOM_TOP;
        
        Animation flip = new FlipXAnimation(dir, (int) (mCenterX + mDetail.getX()), (int) mDetail.getY());
        flip.setDuration(duration);
        
        if (up) {
            flip.setInterpolator(mInterpolator);
        }
        
        return flip;
	}
	
	private Animation flipDetailView(boolean up) {

        int duration = up ? 800 : 375;
        
	    FlipDirection dir = up ? FlipDirection.IN_TOP_BOTTOM : FlipDirection.OUT_BOTTOM_TOP;
        
        Animation flip = new FlipXAnimation(dir, (int) (mDetail.getWidth() / 2.0f), 0);
        flip.setDuration(duration);
        
        if (up) {
            flip.setInterpolator(mInterpolator);
        }
        
        return flip;
	}

	@Override
	public String getFragmentTitle() {
		return getString(R.string.title_activity_transactions);
	}

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onFragmentBackPressed() {
        configureDetailView();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        mFiltersList.setSelectedChild(groupPosition, childPosition, true);
        FilterViewHolder holder = (FilterViewHolder) v.getTag();
        
        // Notify transaction list view of new filter
        EventBus.getDefault().post(new EventMessage().new FilterEvent(holder.mQuery));
        
        // Expand any existing sub sections (PAYEES)
        if (holder != null && holder.mSubSection != null) {
            mAdapter.expandSubSection(groupPosition, childPosition, holder.mSubSection);
        }
        
        return true;
    }
}
