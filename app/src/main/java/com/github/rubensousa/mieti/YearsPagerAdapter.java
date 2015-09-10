package com.github.rubensousa.mieti;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class YearsPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTabsNames = new ArrayList<>();
    private List<View> mTabViews = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private YearFragment.CourseListener mCourseListener;

    public YearsPagerAdapter(Activity activity, FragmentManager fragmentManager, YearFragment.CourseListener courseListener) {
        super(fragmentManager);
        mLayoutInflater = activity.getLayoutInflater();
        mCourseListener = courseListener;
    }

    public void initHighlight(int position) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (i == position) {
                mTabViews.get(i).setAlpha(1);
            } else {
                mTabViews.get(i).setAlpha((float) 0.7);
            }
        }
    }

    public View createTabView(final int position) {
        TextView tabview = (TextView) mLayoutInflater.inflate(R.layout.tab_view, null);
        tabview.setText(mTabsNames.get(position));
        mTabViews.add(tabview);
        return tabview;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsNames.get(position);
    }

    public void addFragment(Fragment fragment, String tabName) {
        mFragments.add(fragment);
        mTabsNames.add(tabName);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragments.set(position, fragment);

        if (mCourseListener != null) {
            try {
                ((YearFragment) fragment).setCourseListener(mCourseListener);
            } catch (ClassCastException e) {
                throw new ClassCastException(fragment.toString()
                        + " must be of type YearFragment");
            }
        }
        return fragment;
    }

}
