package com.about.octos.aboutoctos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutTabHostFragment extends Fragment {
    private static Context mContext;
    private static Resources mKraken;

    private TabAdapter mAdapter;
    private PagerSlidingTabStrip mTabs;
    private ViewPager mPager;

    private static int[] mFragments = new int[] {
            R.string.team_octos_project,
            R.string.team_octos_members,
            R.string.team_octos_supporters,
    };

    private static final int[] mFragmentsKraken = new int[] {
            R.string.team_octos_project,
            R.string.team_octos_members,
            R.string.team_octos_kraken,
            R.string.team_octos_supporters,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mKraken = getResources();

        if (mKraken.getBoolean(R.bool.device_has_kraken)) {
            mFragments = mFragmentsKraken;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedinstanceState) {
        View main = inflater.inflate(R.layout.fragment_about_main, container, false);
        mAdapter = new TabAdapter(getChildFragmentManager());
        mPager = (ViewPager) main.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mTabs = (PagerSlidingTabStrip) main.findViewById(R.id.tabs);
        mTabs.setViewPager(mPager);
        return main;
    }

    private static class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mContext.getString(mFragments[position]);
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public float getPageWidth(int position) {
            return (1.0f);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new AboutFragment();
                case 1:
                    return new AboutCrewFragment();
                case 2:
                    if (!mKraken.getBoolean(R.bool.device_has_kraken)) {
                        return new AboutSupportersFragment();
                    }
                    else
                    {
                        return new AboutKrakenFragment();
                    }
                case 3:
                    if (mKraken.getBoolean(R.bool.device_has_kraken)) {
                        return new AboutSupportersFragment();
                    }
            }
            return null;
        }
    }
}
