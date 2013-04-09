package main.java.com.moneydesktop.finance.handset.fragment;

import main.java.com.moneydesktop.finance.shared.fragment.GrowFragment;
import main.java.com.moneydesktop.finance.tablet.fragment.AccountSummaryTabletFragment;
import main.java.com.moneydesktop.finance.tablet.fragment.SpendingChartTabletFragment;
import main.java.com.moneydesktop.finance.tablet.fragment.TransactionsChartTabletFragment;

public class DashboardFragmentFactory {

    public static GrowFragment getHandsetInstance(int position) {

        switch (position) {
            case 0:
                return AccountSummaryHandsetFragment.getInstance(position);
            case 1:
                return TransactionSummaryHandsetFragment.getInstance(position);
            case 2:
                return SpendingChartSummaryHandsetFragment.newInstance(position);
        }

        return null;
    }

    public static GrowFragment getTabletInstance(int position) {

        switch (position) {
            case 0:
                return AccountSummaryTabletFragment.newInstance(position);
            case 1:
                return TransactionsChartTabletFragment.newInstance(position);
            case 2:
                return SpendingChartTabletFragment.newInstance(position);
        }

        return null;
    }
}
