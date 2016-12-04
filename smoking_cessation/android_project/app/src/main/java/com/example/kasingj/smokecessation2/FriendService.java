package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by kasingj on 12/3/16.
 */

public class FriendService {
    FriendDAO DAO;
    public FriendService(Context context) {
        DAO = new FriendDAO(context);
    }

    public FriendEntity addFriendStats(DatabaseOperations dbop,FriendEntity entity){
        return DAO.addFriendStats(dbop,entity);
    }

    public ArrayList<FriendEntity> getAllFriends(int primaryId){
        Cursor cr = DAO.getAllFriends(DAO,primaryId);
        return buildFriendList(cr);
    }

    private ArrayList<FriendEntity> buildFriendList(Cursor cr) {
            FriendEntity entity;
        ArrayList<FriendEntity> friends= new ArrayList<>();

            if(cr == null){return null;}

            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                FriendEntity friend = convertToEntity(cr);
                friends.add( friend );
                cr.moveToNext();
            }
        cr.close();
        return friends;
    }

    private FriendEntity convertToEntity(Cursor cr) {
        FriendEntity entity;
        entity = new FriendEntity();
        String username = cr.getString(0);
        int id = Integer.parseInt(cr.getString(1));
        String time = cr.getString(2);

        int totDaysFree = Integer.parseInt(cr.getString(3));
        int longStreak = Integer.parseInt(cr.getString(4));
        int currStreak = Integer.parseInt(cr.getString(5));
        int numCravings = Integer.parseInt(cr.getString(6));
        int cravsResisted = Integer.parseInt(cr.getString(7));
        int numCigsSmoked = Integer.parseInt(cr.getString(8));
        double moneySaved = Double.parseDouble(cr.getString(9));
        //int lifeReg = Integer.parseInt(cr.getString(10));
        //int userServerId = Integer.parseInt(cr.getString(11));
        int friendOfId = Integer.parseInt(cr.getString(12));
        String email = cr.getString(13);

        entity.setID( id );
        entity.setTime(time);
        entity.setTotalDaysFree(totDaysFree);
        entity.setLongestStreak(longStreak);
        entity.setCurrentStreak(currStreak);
        entity.setNumCravings(numCravings);
        entity.setCravsRes(cravsResisted);
        entity.setNumCigsSmoked(numCigsSmoked);
        entity.setParentId(friendOfId);
        entity.setEmail(email);
        entity.setMoneySaved(moneySaved);
        entity.setLifeRegained(0);
        entity.setUsername(username);
        return entity;
    }

    public FriendEntity getFriendById(int primaryKey){
        Cursor cr = DAO.getFriendWithPrimaryId(DAO,primaryKey);
        if(cr.moveToFirst()){
            return convertToEntity(cr);
        }
        return null;
    }

}
