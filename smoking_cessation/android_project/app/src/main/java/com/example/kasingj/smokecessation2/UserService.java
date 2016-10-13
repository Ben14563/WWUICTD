package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kasingj on 10/13/16.
 */
public class UserService {
    DatabaseOperations UserDAO;

    public UserService(Context context) {
        UserDAO = new DatabaseOperations(context);
    }

    public void addUserLocally(){
        String time= "";
        User.getInstance().setTime(time);
        User.getInstance().setTotalDaysFree(0);
        User.getInstance().setLongestStreak(0);
        User.getInstance().setCurrentStreak(0);
        User.getInstance().setNumCravings(0);
        User.getInstance().setCravsRes(0);
        User.getInstance().setMoneySaved(0.00);
        User.getInstance().setLifeRegained(0);

        Log.d("Finish Button", "initialized user stats");

        UserDAO.addUserStats(UserDAO, User.getInstance().getUsername(), time, "0", "0", "0", "0", "0", "0", "0.00", "0" ,
                User.getInstance().getCigsPerDay() ,User.getInstance().getPricePerPack(), User.getInstance().getNumYearsSmoked() , -1,0 );
        /* close database or end session? */
    }
}
