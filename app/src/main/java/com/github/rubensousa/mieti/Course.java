package com.github.rubensousa.mieti;


import com.orm.SugarRecord;
import com.orm.dsl.Column;


public class Course extends SugarRecord {

    /**
     * Ciências Básicas
     */
    public static final double CB = 1.0;

    /**
     * Ciências de Engenharia
     */
    public static final double CE = 1.5;

    /**
     * Engenharia Eletrónica e Computadores
     */
    public static final double EEC = 2.0;

    /**
     * Engenharia Informática
     */
    public static final double EI = 2.0;

    /**
     * Tecnologias e Sistemas de Informação
     */
    public static final double TSI = 2.0;

    /**
     * Engenharia de Telecomunicações
     */
    public static final double ET = 2.0;

    @Column(name = "name")
    private String mName;

    @Column(name = "year")
    private int mYear;

    @Column(name = "grade")
    private int mGrade;

    @Column(name = "ects")
    private double mECTS;

    @Column(name = "field")
    private double mField;

    @Column(name = "semester")
    private String mSemester;

    public Course() {
        mName = "";
        mYear = 0;
        mGrade = 0;
        mECTS = 0.0;
        mField = CB;
        mSemester = "";
    }

    public Course(String name, int year, int grade, double ects, double field, String semester) {
        mName = name;
        mYear = year;
        mGrade = grade;
        mECTS = ects;
        mField = field;
        mSemester = semester;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        this.mYear = year;
    }

    public int getGrade() {
        return mGrade;
    }

    public void setGrade(int mGrade) {
        this.mGrade = mGrade;
    }

    public double getECTS() {
        return mECTS;
    }

    public void setECTS(double mECTS) {
        this.mECTS = mECTS;
    }

    public double getField() {
        return mField;
    }

    public void setField(double mField) {
        this.mField = mField;
    }

    public String getSemester() {
        return mSemester;
    }

    public void setSemester(String mSemester) {
        this.mSemester = mSemester;
    }

    public Course clone() {
        return new Course(mName, mYear, mGrade, mECTS, mField, mSemester);
    }
}
