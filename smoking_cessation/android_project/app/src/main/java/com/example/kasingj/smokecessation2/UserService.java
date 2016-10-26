package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.SharedPreferences;
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

    public UserEntity getUserEntityWithPrimaryId(int primaryId){

        Cursor cr = UserDAO.getUserWithPrimaryId(UserDAO, primaryId);
        //map object
        UserEntity entity=null;

        if (cr != null && cr.moveToFirst()) {
            entity = new UserEntity();
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

            entity.setID( Integer.parseInt(id) );
            entity.setTotalDaysFree(totDaysFree);
            entity.setLongestStreak(longStreak);
            entity.setCurrentStreak(currStreak);
            entity.setNumCravings(cravs);
            entity.setCravsRes(cravsRes);
            entity.setNumCigsSmoked(numSmokes);
            entity.setMoneySaved(moneySaved);
            entity.setLifeRegained(lifeReg);
            entity.setCigsPerDay(cigsPerDay);
            entity.setPricePerPack(pricePerPack);
            entity.setNumYearsSmoked(numYearsSmoked);
            entity.setServerId(serverId);

            Log.d("Entered Dashboard", "User object updated");
        }
        cr.close();
        return entity;
    }

    public int saveUserEntity(UserEntity entity){
        String time= UserDAO.getCurrTime();
        Log.d("Finish Button", "initialized user stats");
        entity = UserDAO.addUserStats(UserDAO, entity);
        return entity.getID();
    }

    public UserEntity getUserIfAuthorized(String username, String password){
        //call to the dao
        int authId = UserDAO.isUserAuthorized(UserDAO,username,password);
        Cursor cr = UserDAO.getUserStats(UserDAO,username);
        UserEntity entity = new UserEntity();
        return entity;
    }




}
