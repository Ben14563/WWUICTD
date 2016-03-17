package com.example.kasingj.smokecessation2;

/**
 * Created by Alvin on 3/16/2016.
 */
public class UserDemographics {

    private static String FIRST_NAME="";
    private static String LAST_NAME="";
    private static String AGE="";
    private static String GENDER="";
    private static String ETHNICITY="";
    private static String CIGS_PER_DAY="";
    private static String COST_PER_PACK="";
    private static String NUM_YEARS_SMOKED="";
    private static UserDemographics ourInstance = new UserDemographics();

    public static UserDemographics getInstance() {
        return ourInstance;
    }

    private UserDemographics() {
    }

    // setters
    protected void setFirstName(String name) {
        FIRST_NAME = name;
    }

    protected void setLastName(String name) {
        LAST_NAME = name;
    }

    protected void setAge(String age) {
        AGE = age;
    }

    protected void setGender(String gender) {
        GENDER = gender;
    }

    protected void setEthnicity (String ethn) {
        ETHNICITY = ethn;
    }

    protected void setCigsPerDay (String cigs) {
        CIGS_PER_DAY = cigs;
    }

    protected void setCostPerPack(String cost) {
        COST_PER_PACK = cost;
    }

    protected void setNumYearsSmoked (String num) {
        NUM_YEARS_SMOKED = num;
    }

    // getters
    protected String getFirstName() {
        return FIRST_NAME;
    }

    protected String getLastName() {
        return LAST_NAME;
    }

    protected String getAge() {
        return AGE;
    }

    protected String getGender() {
        return GENDER;
    }

    protected String getEthnicity() {
        return ETHNICITY;
    }

    protected String getCigsPerDay() {
        return CIGS_PER_DAY;
    }

    protected String getCostPerPack() {
        return COST_PER_PACK;
    }

    protected String getNumYearsSmoked() {
        return NUM_YEARS_SMOKED;
    }

}
