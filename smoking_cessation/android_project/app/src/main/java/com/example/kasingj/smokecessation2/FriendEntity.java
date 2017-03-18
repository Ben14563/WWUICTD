package com.example.kasingj.smokecessation2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by kasingj on 3/3/16.
 */
public class FriendEntity {
    /* ALL OF THE FRIEND FIELDS*/
    private String FRIEND_NAME="";
    private String TIME="";
    private int FRIEND_ID;
    private String EMAIL="";
    private String TOTAL_DAYS_FREE="";
    private String LONGEST_STREAK="";
    private String CURRENT_STREAK="";
    private String NUM_CRAVINGS="";
    private String CRAVINGS_RESISTED="";
    private String NUM_CIGS_SMOKED="";
    private String MONEY_SAVED="";
    private String LIFE_REGAINED="";
    private int PARENT_ID=-1;

    public int getParentId() {
        return PARENT_ID;
    }

    public void setParentId(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }


    //constructor
    public FriendEntity() {

    }

    public void setFriendObject(String friend_name, String email ,String time, String total_days_free, String longest_streak, String current_streak, String num_cravings,
                                String cravings_resisted, String num_cigs_smoked, String money_saved, String life_regained){
        FRIEND_NAME=friend_name; TIME=time; TOTAL_DAYS_FREE=total_days_free; LONGEST_STREAK=longest_streak;
        CURRENT_STREAK=current_streak; NUM_CRAVINGS=num_cravings; CRAVINGS_RESISTED=cravings_resisted;
        NUM_CIGS_SMOKED=num_cigs_smoked; MONEY_SAVED=money_saved; LIFE_REGAINED=life_regained;EMAIL=email;
    }

    protected void setUsername(String name) {
        FRIEND_NAME = name;
    }

    protected void setID(int id) { FRIEND_ID = id; }

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

    //JSON STRING Constructor
    public void friendObject(String JSON_Data){
        //parse String and fill variables
    }

    protected String[] getFields(){
        return new String[]{FRIEND_NAME, FRIEND_ID+"", TIME,TOTAL_DAYS_FREE,LONGEST_STREAK,CURRENT_STREAK,NUM_CRAVINGS,CRAVINGS_RESISTED,NUM_CIGS_SMOKED,MONEY_SAVED,LIFE_REGAINED};
    }

    protected String getUsername() {
        return FRIEND_NAME;
    }

    protected int getID() { return FRIEND_ID; }

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