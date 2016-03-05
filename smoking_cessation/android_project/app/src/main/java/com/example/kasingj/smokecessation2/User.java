package com.example.kasingj.smokecessation2;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by kasingj on 3/4/16.
 */
public class User {


    private static String USER_NAME="";
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

    protected String[] getFields(){
        return new String[]{USER_NAME, TIME,TOTAL_DAYS_FREE,LONGEST_STREAK,CURRENT_STREAK,NUM_CRAVINGS,CRAVINGS_RESISTED,NUM_CIGS_SMOKED,MONEY_SAVED,LIFE_REGAINED};
    }
}
