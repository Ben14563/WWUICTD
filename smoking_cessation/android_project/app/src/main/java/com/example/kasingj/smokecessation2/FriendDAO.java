package com.example.kasingj.smokecessation2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by kasingj on 12/3/16.
 */
public class FriendDAO extends DatabaseOperations {

    public FriendDAO(Context context) {
        super(context);
    }

    /* database methods fore adding to the friend table */
    // adding to friends_stats Table
    public void addFriendStats(DatabaseOperations dbop, String username, String friendName, String friendID, String email, String time, String totDaysFree,
                               String longStreak, String currStreak, String numCraves, String cravesRes, String numCigsSmoked, String moneySaved, String lifeReg) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.FRIEND_NAME, friendName);
        cv.put(TableData.TableInfo.FRIEND_ID, friendID);
        cv.put(TableData.TableInfo.EMAIL, email);
        cv.put(TableData.TableInfo.TIME, time);
        cv.put(TableData.TableInfo.TOTAL_DAYS_FREE, totDaysFree);
        cv.put(TableData.TableInfo.LONGEST_STREAK, longStreak);
        cv.put(TableData.TableInfo.CURRENT_STREAK, currStreak);
        cv.put(TableData.TableInfo.NUM_CRAVINGS, numCraves);
        cv.put(TableData.TableInfo.CRAVINGS_RESISTED, cravesRes);
        cv.put(TableData.TableInfo.NUM_CIGS_SMOKED, numCigsSmoked);
        cv.put(TableData.TableInfo.MONEY_SAVED, moneySaved);
        cv.put(TableData.TableInfo.LIFE_REGAINED, lifeReg);

        sq.insert(TableData.TableInfo.FRIENDS_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into friends_stats Table");
    }

    /* get friend with primary id is friend_id? */

    public Cursor getFriendWithPrimaryId(DatabaseOperations dbop, int primaryId){
        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = buildFriendColumns();
        String where = TableData.TableInfo.ID + " = ?";
        String[] whereArgs = new String[] {primaryId+""};
        String orderBy = TableData.TableInfo.TIME + " DESC LIMIT 1";
        Cursor cr = sq.query(TableData.TableInfo.FRIENDS_TABLE_NAME, columns, where, whereArgs, null, null, orderBy);
        return cr;
    }

    /* get all friends of current user */

    public Cursor getAllFriends(DatabaseOperations dbop, int friendOfId){
        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = buildFriendColumns();
        String where = TableData.TableInfo.FRIEND_OF_ID+"= ?";
        String[] whereArgs = new String[] {friendOfId+""};
        Cursor cr = sq.query(TableData.TableInfo.FRIENDS_TABLE_NAME, columns, where, whereArgs, null, null, null);
        return cr;
    }

    @NonNull
    private String[] buildFriendColumns() {
        return new String[]{TableData.TableInfo.USER_NAME, TableData.TableInfo.ID, TableData.TableInfo.TIME, TableData.TableInfo.TOTAL_DAYS_FREE,
                TableData.TableInfo.LONGEST_STREAK, TableData.TableInfo.CURRENT_STREAK, TableData.TableInfo.NUM_CRAVINGS,
                TableData.TableInfo.CRAVINGS_RESISTED, TableData.TableInfo.NUM_CIGS_SMOKED, TableData.TableInfo.MONEY_SAVED,
                TableData.TableInfo.LIFE_REGAINED,TableData.TableInfo.USER_SERVER_ID, TableData.TableInfo.FRIEND_OF_ID, TableData.TableInfo.EMAIL};
    }

    /*id INTEGER PRIMARY KEY,user_name TEXT,friend TEXT,friend_id TEXT,email TEXT,time TEXT, TEXT,total_days_free TEXT,longest_streak TEXT,
    current_streak TEXT,num_cravings TEXT,cravings_resisted TEXT,num_cigs_smoked TEXT,money_saved TEXT,life_regained TEXT,user_server_id INTEGER,friend_of_id INTEGER);*/
    /* add friend to friend table */

    public FriendEntity addFriendStats(DatabaseOperations dbop,FriendEntity entity, int parentId){
        long result = (long)-1;
        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, entity.getUsername() );
        cv.put(TableData.TableInfo.TIME, getCurrTime() );
        cv.put(TableData.TableInfo.TOTAL_DAYS_FREE, entity.getTotalDaysFree());
        cv.put(TableData.TableInfo.LONGEST_STREAK, entity.getLongestStreak());
        cv.put(TableData.TableInfo.CURRENT_STREAK, entity.getCurrentStreak());
        cv.put(TableData.TableInfo.NUM_CRAVINGS, entity.getNumCravings());
        cv.put(TableData.TableInfo.CRAVINGS_RESISTED, entity.getCravingsResisted());
        cv.put(TableData.TableInfo.NUM_CIGS_SMOKED, entity.getNumCigsSmoked());
        cv.put(TableData.TableInfo.MONEY_SAVED, entity.getMoneySaved());
        cv.put(TableData.TableInfo.LIFE_REGAINED, entity.getLifeRegained());
        cv.put(TableData.TableInfo.FRIEND_OF_ID, entity.getParentId());
        cv.put(TableData.TableInfo.EMAIL, entity.getEmail());

        //String whereClause = "email='" + entity.getEmail()+"'" "and " ;

        String where = TableData.TableInfo.EMAIL + " = ? AND " + TableData.TableInfo.FRIEND_OF_ID +" = ?";
        String[] whereArgs = new String[] {entity.getEmail()+"",parentId+""};
        int update = sq.update(TableData.TableInfo.FRIENDS_TABLE_NAME, cv, where,whereArgs);

        if(update == 0){
            result = sq.insert(TableData.TableInfo.FRIENDS_TABLE_NAME, null, cv);
        }

        Log.d("Database Operations", "One row inserted into user_stats Table");
        entity.setID( (int) result);
        return entity;
    }
}

/*
* - Try to update any existing row
UPDATE players
SET user_name='steven', age=32
WHERE user_name='steven';

-- If no update happened (i.e. the row didn't exist) then insert one
INSERT INTO players (user_name, age)
SELECT 'steven', 32
WHERE (Select Changes() = 0);
*
* */