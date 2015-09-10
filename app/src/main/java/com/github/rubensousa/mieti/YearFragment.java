package com.github.rubensousa.mieti;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class YearFragment extends Fragment implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    public static final String YEAR = "year";
    public static final String CURRENT_COURSE_POSITION = "position";

    private List<Course> mCourses;
    private List<TextView> mGrades;
    private List<TableRow> mRows;
    private CourseListener mCourseListener;
    private AlertDialog mAlertDialog;
    private int mCurrentCoursePosition = -1;
    private boolean mPausing = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.yearfragment_courses, container, false);

        Bundle args = getArguments();
        int year = args.getInt(YEAR);
        TableLayout tableLayout = (TableLayout) layout.findViewById(R.id.tableLayout);
        mCourses = Course.find(Course.class, "year = ?", year + "");
        mGrades = new ArrayList<>();
        mRows = new ArrayList<>();
        Utils.sortCourses(mCourses);

        int rows = tableLayout.getChildCount();

        for (int i = 1; i < rows; i++) {

            final TableRow row = (TableRow) tableLayout.getChildAt(i);
            mRows.add(row);

            if (i > mCourses.size()) {
                row.setVisibility(View.GONE);
            } else {
                Course course = mCourses.get(i - 1);

                if (course.getGrade() > 0) {
                    checkTableRow(row, true);
                }

                row.setOnClickListener(this);

                int columns = row.getChildCount();

                for (int j = 0; j < columns; j++) {
                    final TextView textView = (TextView) row.getChildAt(j);
                    switch (j) {
                        case 0:
                            textView.setText(course.getName());
                            break;
                        case 1:
                            mGrades.add(textView);

                            if (course.getGrade() > 0) {
                                textView.setText(course.getGrade() + "");
                            } else {
                                textView.setText(getString(R.string.default_grade));
                            }
                            break;
                        case 2:
                            textView.setText(course.getSemester());
                            break;
                        case 3:
                            textView.setText(String.format("%.1f", course.getECTS()));
                            break;
                    }
                }
            }
        }

        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentCoursePosition = savedInstanceState.getInt(CURRENT_COURSE_POSITION, mCurrentCoursePosition);
            if (mCurrentCoursePosition != -1) {
                onClick(mRows.get(mCurrentCoursePosition));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_COURSE_POSITION, mCurrentCoursePosition);
    }

    @Override
    public void onClick(View v) {

        if (v instanceof TableRow) {
            final TableRow row = (TableRow) v;
            final Course course = mCourses.get(mRows.indexOf(row));
            mCurrentCoursePosition = mCourses.indexOf(course);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
            View view = getLayoutInflater(null).inflate(R.layout.yearfragment_picker, null);
            NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
            numberPicker.setDisplayedValues(getResources().getStringArray(R.array.grades));
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(11);
            numberPicker.setValue(course.getGrade() == 0 ? 0 : course.getGrade() - 9);
            numberPicker.setOnValueChangedListener(this);
            mAlertDialog = builder.setView(view)
                    .setTitle(course.getName())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();

            mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (!mPausing) {
                        mCurrentCoursePosition = -1;
                    }
                }
            });
            mAlertDialog.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAlertDialog != null) {
            if (mAlertDialog.isShowing()) {
                mPausing = true;
                mAlertDialog.dismiss();
            }
        }
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Course course = mCourses.get(mCurrentCoursePosition);
        if (newVal == 0) {
            course.setGrade(0);
            checkTableRow(mRows.get(mCurrentCoursePosition), false);
        } else {
            course.setGrade(newVal + 9);
            checkTableRow(mRows.get(mCurrentCoursePosition), true);
        }

        if (course.getGrade() > 0) {
            mGrades.get(mCourses.indexOf(course)).setText(course.getGrade() + "");
        } else {
            mGrades.get(mCourses.indexOf(course)).setText(getString(R.string.default_grade));
        }

        mCourseListener.updateCourse(course);
        course.save();
    }

    private void checkTableRow(View row, boolean check) {
        if (check) {
            row.setBackgroundResource(R.drawable.row_checked_background);
        } else {
            row.setBackgroundResource(R.drawable.row_default_background);
        }
    }

    public void setCourseListener(CourseListener courseListener) {
        mCourseListener = courseListener;
    }


    public interface CourseListener {
        void updateCourse(Course course);
    }


}
