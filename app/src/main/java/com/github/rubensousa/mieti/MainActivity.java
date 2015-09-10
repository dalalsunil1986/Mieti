package com.github.rubensousa.mieti;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, YearFragment.CourseListener {

    public static final String CURRENT_TAB = "current_tab";

    private int mCurrentTab;
    private ViewPager mViewPager;
    private TextView mAverageTextView;
    private TextView mBachelorAverageTextView;
    private TextView mECTSTextView;
    private HashMap<String, Course> mCourses;
    private double mSum = 0;
    private double mSumBachelor = 0;
    private double mAverage = 0;
    private double mBachelorAverage = 0;
    private double mECTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Course course = Course.first(Course.class);

            if (course == null) {
                Utils.addCourses(this);
            }

        } else {
            mCurrentTab = savedInstanceState.getInt(CURRENT_TAB);
        }

        mAverageTextView = (TextView) findViewById(R.id.average);
        mBachelorAverageTextView = (TextView) findViewById(R.id.bachelor_average);
        mECTSTextView = (TextView) findViewById(R.id.ects);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name_caps));

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        YearsPagerAdapter yearsPagerAdapter = new YearsPagerAdapter(this, getSupportFragmentManager(), this);
        String[] tabs = getResources().getStringArray(R.array.tabs);
        mCourses = new HashMap<>();

        for (int i = 0; i < tabs.length; i++) {
            YearFragment yearFragment = new YearFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(YearFragment.YEAR, i + 1);
            yearFragment.setArguments(bundle);
            yearsPagerAdapter.addFragment(yearFragment, tabs[i]);
        }

        mViewPager.setAdapter(yearsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);


        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            final TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(yearsPagerAdapter.createTabView(i));
            }
        }

        mTabLayout.setOnTabSelectedListener(this);

        yearsPagerAdapter.initHighlight(0);


        if (mCurrentTab != 0) {
            TabLayout.Tab tab = mTabLayout.getTabAt(mCurrentTab);
            if (tab != null) {
                tab.select();
            }
        }


        loadInfo();
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        View v = tab.getCustomView();
        if (v != null) {
            v.setAlpha(1);
        }

        mCurrentTab = tab.getPosition();

        if (mViewPager.getCurrentItem() != mCurrentTab) {
            mViewPager.setCurrentItem(mCurrentTab);
        }


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        View v = tab.getCustomView();
        if (v != null) {
            v.setAlpha((float) 0.7);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private void loadInfo() {
        List<Course> courses = Course.listAll(Course.class);

        for (Course course : courses) {
            if (course.getGrade() > 0) {
                mCourses.put(course.getName(), course.clone());
                mECTS += course.getECTS();

                if (course.getYear() >= 4) {
                    mSum += course.getField() * course.getECTS();
                    mAverage += course.getField() * course.getECTS() * course.getGrade();
                } else { // SUM Fi x ECTSi x Ni / SUM Fi x ECTSi
                    mSum += course.getField() * course.getECTS();
                    mSumBachelor = mSum;
                    mAverage += course.getField() * course.getECTS() * course.getGrade();
                    mBachelorAverage = mAverage;
                }

            }
        }
        setInfo();
    }

    private void setInfo() {
        if (mSum != 0) {
            mAverageTextView.setText(String.format("%.3f", mAverage / mSum));
            mBachelorAverageTextView.setText(String.format("%.3f", mBachelorAverage / mSumBachelor));
        } else {
            mAverageTextView.setText(getString(R.string.default_grade));
            mBachelorAverageTextView.setText(getString(R.string.default_grade));
        }


        if (mECTS % 5 != 0) {
            mECTSTextView.setText(String.format("%.1f", mECTS) + "/300");
        } else {
            mECTSTextView.setText(String.format("%.0f", mECTS) + "/300");
        }

    }

    @Override
    public void updateCourse(Course course) {

        if (mCourses.containsKey(course.getName())) {
            Course previous = mCourses.get(course.getName());

            if (previous.getGrade() == 0 && course.getGrade() >= 10) {
                updateAverage(course, true);
            }

            if (previous.getGrade() >= 10 && course.getGrade() == 0) {
                updateAverage(previous, false);
            }

            if (previous.getGrade() >= 10 && course.getGrade() >= 10) {
                updateAverage(previous, false);
                updateAverage(course, true);
            }

        } else {

            if (course.getGrade() >= 10) {
                updateAverage(course, true);
            } else {
                updateAverage(course, false);
            }
        }

        mCourses.put(course.getName(), course.clone());

        setInfo();
    }

    private void updateAverage(Course course, boolean plus) {
        if (plus) {
            mECTS += course.getECTS();
            mAverage += course.getField() * course.getECTS() * course.getGrade();
            mSum += course.getField() * course.getECTS();
            if (course.getYear() < 4) {
                mBachelorAverage += course.getField() * course.getECTS() * course.getGrade();
                mSumBachelor += course.getField() * course.getECTS();
            }
        } else {
            mECTS -= course.getECTS();
            mAverage -= course.getField() * course.getECTS() * course.getGrade();
            mSum -= course.getField() * course.getECTS();
            if (course.getYear() < 4) {
                mBachelorAverage -= course.getField() * course.getECTS() * course.getGrade();
                mSumBachelor -= course.getField() * course.getECTS();
            }
        }
    }


}
