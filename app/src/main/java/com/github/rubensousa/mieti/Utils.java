package com.github.rubensousa.mieti;


import android.content.Context;

import com.orm.SugarTransactionHelper;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Utils {

    /**
     * Ordenar alfabeticamente e por semestre
     *
     * @param courses Lista de UCs a ordenar
     * @return 0 se as UCs tiverem o mesmo nome e forem do mesmo semestre
     */
    public static void sortCourses(List<Course> courses) {
        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course lhs, Course rhs) {
                Collator collator = Collator.getInstance(Locale.getDefault());
                int cmp = lhs.getSemester().compareTo(rhs.getSemester());
                if (cmp == 0) {
                    return collator.compare(lhs.getName(), rhs.getName());
                } else {
                    return cmp;
                }
            }
        });
    }

    public static boolean addCourses(final Context context) {
        final List<Course> courses = new ArrayList<>();

        // 1st year
        // name, year, grade, ects, field, semester
        courses.add(new Course(context.getString(R.string.year1_uc1), 1, 0, 5.0, Course.CB, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year1_uc2), 1, 0, 7.5, Course.EEC, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year1_uc3), 1, 0, 5.0, Course.CB, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year1_uc4), 1, 0, 7.5, Course.TSI, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year1_uc5), 1, 0, 5.0, Course.TSI, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year1_uc6), 1, 0, 5.0, Course.CB, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year1_uc7), 1, 0, 5.0, Course.CE, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year1_uc8), 1, 0, 5.0, Course.CB, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year1_uc9), 1, 0, 7.5, Course.EI, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year1_uc10), 1, 0, 7.5, Course.EEC, context.getString(R.string.semester_2)));

        // 2nd year
        courses.add(new Course(context.getString(R.string.year2_uc1), 2, 0, 5.0, Course.CB, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year2_uc2), 2, 0, 5.0, Course.CB, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year2_uc3), 2, 0, 5.0, Course.EEC, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year2_uc4), 2, 0, 5.0, Course.EEC, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year2_uc5), 2, 0, 10.0, Course.EI, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year2_uc6), 2, 0, 5.0, Course.EEC, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year2_uc7), 2, 0, 5.0, Course.CE, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year2_uc8), 2, 0, 5.0, Course.CE, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year2_uc9), 2, 0, 10.0, Course.TSI, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year2_uc10), 2, 0, 5.0, Course.EEC, context.getString(R.string.semester_2)));

        // 3rd year
        courses.add(new Course(context.getString(R.string.year3_uc1), 3, 0, 10.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year3_uc2), 3, 0, 5.0, Course.EEC, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year3_uc3), 3, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year3_uc4), 3, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year3_uc5), 3, 0, 5.0, Course.EI, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year3_uc6), 3, 0, 10.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year3_uc7), 3, 0, 5.0, Course.EEC, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year3_uc8), 3, 0, 5.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year3_uc9), 3, 0, 5.0, Course.EI, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year3_uc10), 3, 0, 5.0, Course.TSI, context.getString(R.string.semester_2)));

        // 4th year
        courses.add(new Course(context.getString(R.string.year4_uc1), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year4_uc2), 4, 0, 5.0, Course.EI, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year4_uc3), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year4_uc4), 4, 0, 10.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year4_uc5), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year4_uc6), 4, 0, 10.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year4_uc7), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year4_uc8), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year4_uc9), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_2)));
        courses.add(new Course(context.getString(R.string.year4_uc10), 4, 0, 5.0, Course.ET, context.getString(R.string.semester_2)));


        courses.add(new Course(context.getString(R.string.year5_uc1), 5, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year5_uc2), 5, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year5_uc3), 5, 0, 5.0, Course.ET, context.getString(R.string.semester_1)));
        courses.add(new Course(context.getString(R.string.year5_uc4), 5, 0, 45.0, Course.ET, context.getString(R.string.annual)));

        SugarTransactionHelper.doInTransaction(new SugarTransactionHelper.Callback() {
            @Override
            public void manipulateInTransaction() {
                for (Course course : courses) {
                    course.save();
                }
            }
        });


        return true;
    }


}
