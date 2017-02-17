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
        UserEntity entity=convertRowToUserEntity(cr);
        return entity;
    }

    public UserEntity getCurrentUser(Context ctx){
        SharedPreferences preference = ctx.getSharedPreferences(ctx.getApplicationContext().getPackageName() , 1 );
        int id = preference.getInt(MainActivity.CURRENT_USER_ID, -1);
        return id==-1 ? null:getUserEntityWithPrimaryId(id);
    }

    public int saveUserEntity(UserEntity entity){
        String time= UserDAO.getCurrTime();
        Log.d("Finish Button", "initialized user stats");
        entity = UserDAO.addUserStats(UserDAO, entity);
        return entity.getID();
    }

    public UserEntity getUserIfAuthorized(String username, String password){
        int authId = UserDAO.isUserAuthorized(UserDAO, username, password);
        if (authId == -1){
            return null;
        }
        Cursor cr = UserDAO.getUserStats(UserDAO, username);
        UserEntity entity = convertRowToUserEntity(cr);
        return entity;
    }

    public int addUserCredentials(String username, String password, String email){
        int id = UserDAO.addUserAuth(UserDAO, username, password, email);
        return id;
    }

    public void addUserHist(UserEntity entity, int smoked, int crave, int resist) {
        UserDAO.addUserHistory(UserDAO, entity, smoked, crave, resist);
    }

    private UserEntity convertRowToUserEntity(Cursor cr){
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
            entity.setUsername(username);

            Log.d("Entered Dashboard", "User object updated");
        }
        cr.close();
        return entity;
    }

    public void updateUser(UserEntity userEntity) {
        UserDAO.updateUser(UserDAO, userEntity);
    }
}
