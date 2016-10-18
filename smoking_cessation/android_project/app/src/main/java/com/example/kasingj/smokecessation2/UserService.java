package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.database.Cursor;
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
        String time= UserDAO.getCurrTime();
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

    public void updateUser() {

        //UserDAO = new DatabaseOperations(ctx);
        Cursor cr = UserDAO.getUserStats(UserDAO, User.getInstance().getUsername());
        if (cr != null && cr.moveToFirst()) {

            String username = cr.getString(0);
            String id = cr.getString(1);
            int totDaysFree = Integer.parseInt(cr.getString(3));
            int longStreak = Integer.parseInt(cr.getString(4));
            int currStreak = Integer.parseInt(cr.getString(5));
            int cravs = Integer.parseInt(cr.getString(6));
            int cravsRes = Integer.parseInt(cr.getString(7));
            int numSmokes = Integer.parseInt(cr.getString(8));
            double moneySaved = Double.parseDouble(cr.getString(9));
            int lifeReg = Integer.parseInt(cr.getString(10));
            String cigsPerDay = cr.getString(11);
            String pricePerPack = cr.getString(12);
            String numYearsSmoked = cr.getString(13);
            int serverId = cr.getInt(14);


            User.getInstance().setID(id);
            User.getInstance().setTotalDaysFree(totDaysFree);
            User.getInstance().setLongestStreak(longStreak);
            User.getInstance().setCurrentStreak(currStreak);
            User.getInstance().setNumCravings(cravs);
            User.getInstance().setCravsRes(cravsRes);
            User.getInstance().setNumCigsSmoked(numSmokes);
            User.getInstance().setMoneySaved(moneySaved);
            User.getInstance().setLifeRegained(lifeReg);
            User.getInstance().setCigsPerDay(cigsPerDay);
            User.getInstance().setPricePerPack(pricePerPack);
            User.getInstance().setNumYearsSmoked(numYearsSmoked);
            User.getInstance().setServerId(serverId);

            Log.d("Entered Dashboard", "User object updated");
        }
        cr.close();
    }

    /* testing branch comment */
}
