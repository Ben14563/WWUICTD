package com.example.kasingj.smokecessation2;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by kasingj on 3/4/16.
 */
public class User {


    private static String USER_NAME="";
    private static String PASSWORD="";
    private static String EMAIL="";
    private static String TIME="";
    private static String TOTAL_DAYS_FREE="";
    private static String LONGEST_STREAK="";
    private static String CURRENT_STREAK="";
    private static String NUM_CRAVINGS="";
    private static String CRAVINGS_RESISTED="";
    private static String NUM_CIGS_SMOKED="";
    private static String MONEY_SAVED="";
    private static String LIFE_REGAINED="";
    private static HashSet<String> friends = new HashSet<String>(); //
    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }

    protected boolean addFriend(String friendUserName){
        return friends.add(friendUserName);
    }

    protected HashSet<String> getFriends(){
        return friends;
    }

    protected void setFields(String user_name,String time, String total_days_free, String longest_streak, String current_streak, String num_cravings,
                             String cravings_resisted, String num_cigs_smoked, String money_saved, String life_regained){
        USER_NAME=user_name; TIME=time; TOTAL_DAYS_FREE=total_days_free; LONGEST_STREAK=longest_streak;
        CURRENT_STREAK=current_streak; NUM_CRAVINGS=num_cravings; CRAVINGS_RESISTED=cravings_resisted;
        NUM_CIGS_SMOKED=num_cigs_smoked; MONEY_SAVED=money_saved; LIFE_REGAINED=life_regained;
    }

    protected void setUsername(String name) {
        USER_NAME = name;
    }

    protected void setPassword(String pass) { PASSWORD = pass; }

    protected void setEmail(String email) { EMAIL = email; }

    protected void setTime(String time) {
        TIME = time;
    }

    protected void setTotalDaysFree(int daysFree) {
        TOTAL_DAYS_FREE = Integer.toString(daysFree);
    }

    protected void setLongestStreak(int longestStreak) {
        LONGEST_STREAK = Integer.toString(longestStreak);
    }

    protected void setCurrentStreak(int currStreak) {
        CURRENT_STREAK = Integer.toString(currStreak);
    }

    protected void setNumCravings(int numCravs) {
        NUM_CRAVINGS = Integer.toString(numCravs);
    }

    protected void setCravsRes(int numCravs) {
        CRAVINGS_RESISTED = Integer.toString(numCravs);
    }

    protected void setNumCigsSmoked(int cigs) {
        NUM_CIGS_SMOKED = Integer.toString(cigs);
    }

    protected void setMoneySaved(double money) {
        MONEY_SAVED = Double.toString(money);
    }

    protected void setLifeRegained(int life) {
        LIFE_REGAINED = Integer.toString(life);
    }

    protected String[] getFields(){
        return new String[]{USER_NAME, TIME,TOTAL_DAYS_FREE,LONGEST_STREAK,CURRENT_STREAK,NUM_CRAVINGS,CRAVINGS_RESISTED,NUM_CIGS_SMOKED,MONEY_SAVED,LIFE_REGAINED};
    }

    protected String getUsername() {
        return USER_NAME;
    }

    protected String getPassword() { return PASSWORD; }

    protected String getEmail() { return EMAIL; }

    protected String getTime() {
        return TIME;
    }

    protected String getTotalDaysFree() {
        return TOTAL_DAYS_FREE;
    }

    protected String getLongestStreak() {
        return LONGEST_STREAK;
    }

    protected String getCurrentStreak() {
        return CURRENT_STREAK;
    }

    protected String getNumCravings() {
        return NUM_CRAVINGS;
    }

    protected String getCravingsResisted() {
        return CRAVINGS_RESISTED;
    }

    protected String getNumCigsSmoked() {
        return NUM_CIGS_SMOKED;
    }

    protected String getMoneySaved() {
        return MONEY_SAVED;
    }

    protected String getLifeRegained() {
        return LIFE_REGAINED;
    }
}
