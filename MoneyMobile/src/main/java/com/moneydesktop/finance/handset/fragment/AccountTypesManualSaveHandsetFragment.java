package main.java.com.moneydesktop.finance.handset.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import main.java.com.moneydesktop.finance.R;
import main.java.com.moneydesktop.finance.data.Enums.FragmentType;
import main.java.com.moneydesktop.finance.data.SyncEngine;
import main.java.com.moneydesktop.finance.database.AccountType;
import main.java.com.moneydesktop.finance.database.BankAccount;
import main.java.com.moneydesktop.finance.shared.fragment.BaseFragment;
import main.java.com.moneydesktop.finance.util.DialogUtils;
import main.java.com.moneydesktop.finance.util.Fonts;
import main.java.com.moneydesktop.finance.util.UiUtils;

public class AccountTypesManualSaveHandsetFragment extends BaseFragment {

    private AccountType mSelectedAccountType;
    private EditText mAccountName, mCurrentBalance;
    private TextView mSave;

    public void setSelectedAccountType(AccountType mSelectedAccountType) {
        this.mSelectedAccountType = mSelectedAccountType;
    }

    @Override
    public FragmentType getType() {
        return null;
    }

    @Override
    public String getFragmentTitle() {
        return getString(R.string.add_account_label_handset).toUpperCase();
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public static AccountTypesManualSaveHandsetFragment newInstance(AccountType accountType) {

        AccountTypesManualSaveHandsetFragment frag = new AccountTypesManualSaveHandsetFragment();
        frag.setSelectedAccountType(accountType);

        Bundle args = new Bundle();
        frag.setArguments(args);

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        mRoot = inflater.inflate(R.layout.handset_save_manual_account, null);

        mAccountName = (EditText)mRoot.findViewById(R.id.handset_add_bank_manually_account_name);
        mCurrentBalance = (EditText)mRoot.findViewById(R.id.handset_add_bank_manually_current_balance_edittext);
        mSave = (TextView)mRoot.findViewById(R.id.handset_add_bank_manually_save);

        Fonts.applyPrimaryBoldFont(mSave, 14);

        setupView();

        return mRoot;
    }

    private void setupView() {
        mSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                saveManualBank(v);
            }
        });
    }

    private void saveManualBank(View v) {
        String accountName = mAccountName.getText().toString();

        if (accountName.equals("")) {
            DialogUtils.alertDialog(getString(R.string.add_account_manually_cannot_save),
                    getString(R.string.add_account_manually_cannot_save_message),
                    mActivity);

        } else {

            UiUtils.hideKeyboard(mActivity, v);
            createManualBankAccount(mSelectedAccountType);

            mActivity.clearBackStack();
        }
    }

    private void createManualBankAccount(AccountType selectedAccountType) {

        double balance = Double.parseDouble(mCurrentBalance.getText().toString());
        String name = mAccountName.getText().toString();

        BankAccount bankAccount = BankAccount.createBankAccount(selectedAccountType, balance, name);
        bankAccount.insertSingle();

        SyncEngine.sharedInstance().syncBankAccount(bankAccount);
    }

}