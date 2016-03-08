package com.example.kasingj.smokecessation2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;

/**
 * Created by Alvin on 3/3/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    // UserStats Query String
    public String CREATE_USER_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_TABLE_NAME + "(" +
            TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.TIME +
            " TEXT," + TableData.TableInfo.TOTAL_DAYS_FREE + " TEXT," + TableData.TableInfo.LONGEST_STREAK +
            " TEXT," + TableData.TableInfo.CURRENT_STREAK + " TEXT," + TableData.TableInfo.NUM_CRAVINGS +
            " TEXT," + TableData.TableInfo.CRAVINGS_RESISTED + " TEXT," + TableData.TableInfo.NUM_CIGS_SMOKED +
            " TEXT," + TableData.TableInfo.MONEY_SAVED + " TEXT," + TableData.TableInfo.LIFE_REGAINED + " TEXT);";

    // Create Database
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        // Create UserStats Table
        //sdb.execSQL("DROP TABLE IF EXISTS " + TableData.TableInfo.USER_TABLE_NAME);
        Log.d("Database Operations", "creating user table");
        sdb.execSQL(CREATE_USER_QUERY);
        Log.d("Database Operations", "UserStats table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        Log.d("Database Operations", "database exists");
    }

    // adding to UserStats Table
    public void addUserData(DatabaseOperations dbop, String username, String time, String totsDayFree, String longStreak, String currStreak,
                            String cravs, String cravsRes, String numSmokes, String moneySaved, String lifeReg) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.TIME, time);
        cv.put(TableData.TableInfo.TOTAL_DAYS_FREE, totsDayFree);
        cv.put(TableData.TableInfo.LONGEST_STREAK, longStreak);
        cv.put(TableData.TableInfo.CURRENT_STREAK, currStreak);
        cv.put(TableData.TableInfo.NUM_CRAVINGS, cravs);
        cv.put(TableData.TableInfo.CRAVINGS_RESISTED, cravsRes);
        cv.put(TableData.TableInfo.NUM_CIGS_SMOKED, numSmokes);
        cv.put(TableData.TableInfo.MONEY_SAVED, moneySaved);
        cv.put(TableData.TableInfo.LIFE_REGAINED, lifeReg);

        sq.insert(TableData.TableInfo.USER_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into UserStats Table");
    }

}
