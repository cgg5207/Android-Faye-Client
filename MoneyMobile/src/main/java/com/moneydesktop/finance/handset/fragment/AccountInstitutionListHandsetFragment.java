package main.java.com.moneydesktop.finance.handset.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import main.java.com.moneydesktop.finance.ApplicationContext;
import main.java.com.moneydesktop.finance.R;
import main.java.com.moneydesktop.finance.data.Enums.FragmentType;
import main.java.com.moneydesktop.finance.database.Institution;
import main.java.com.moneydesktop.finance.database.InstitutionDao;
import main.java.com.moneydesktop.finance.database.PowerQuery;
import main.java.com.moneydesktop.finance.database.QueryProperty;
import main.java.com.moneydesktop.finance.shared.fragment.BaseFragment;
import main.java.com.moneydesktop.finance.tablet.adapter.AddNewInstitutionAdapter;
import main.java.com.moneydesktop.finance.util.Fonts;
import main.java.com.moneydesktop.finance.views.LabelEditText;

import java.util.List;

public class AccountInstitutionListHandsetFragment extends BaseFragment{

    private TextView mSearchIcon;
    private LabelEditText mSearchField;
    private ListView mInstitutionList;
    private AddNewInstitutionAdapter mAdapter;

    private QueryProperty mWherePopularity = new QueryProperty(InstitutionDao.TABLENAME, InstitutionDao.Properties.Popularity, "!= ?");

    @Override
    public FragmentType getType() {
        return null;
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.label_account_add_institution).toUpperCase();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public static AccountInstitutionListHandsetFragment newInstance() {

        AccountInstitutionListHandsetFragment frag = new AccountInstitutionListHandsetFragment();

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mRoot = inflater.inflate(R.layout.handset_add_account_select_institution, null);

        mSearchIcon = (TextView)mRoot.findViewById(R.id.handset_search_institution_img);
        mSearchField = (LabelEditText)mRoot.findViewById(R.id.handset_search_institution_box);
        mInstitutionList = (ListView)mRoot.findViewById(R.id.handset_search_institution_list);

        setupFonts();
        setupView();

        return mRoot;
    }

    private void setupFonts() {
        Fonts.applyGlyphFont(mSearchIcon, 20);
        Fonts.applySecondaryItalicFont(mSearchField, 18);
    }

    private void setupView() {
        final InstitutionDao dao = ApplicationContext.getDaoSession().getInstitutionDao();

        PowerQuery powerQuery = new PowerQuery(dao);
        powerQuery.where(mWherePopularity, "0");
        List<Institution> institutions = dao.queryRaw(powerQuery.toString(), powerQuery.getSelectionArgs());

        mAdapter = new AddNewInstitutionAdapter(mActivity, R.layout.tablet_add_bank_institution_list_item, institutions, mSearchField, mInstitutionList);
        mInstitutionList.setAdapter(mAdapter);
        mAdapter.initializeData();

        mInstitutionList.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v,int position, long id)
            {
                loadConnectScreenFragment(a, position);
            }
        });
    }

    private void loadConnectScreenFragment(AdapterView<?> a, int position) {
        Institution selectedInstitution = (Institution)a.getItemAtPosition(position);

        AccountOptionsCredentialsHandsetFragment frag = AccountOptionsCredentialsHandsetFragment.newInstance(selectedInstitution);
        mActivity.pushFragment(getId(), frag);
    }

}
