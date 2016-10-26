package com.example.kasingj.smokecessation2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alvin on 3/3/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    // UserDemo Query String
    public String CREATE_USER_DEMO_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_DEMO_TABLE_NAME + "(" +
            TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.ID + " TEXT," + TableData.TableInfo.FIRST_NAME +
            " TEXT," + TableData.TableInfo.LAST_NAME + " TEXT," + TableData.TableInfo.AGE +
            " TEXT," + TableData.TableInfo.GENDER + " TEXT," + TableData.TableInfo.ETHNICITY +
            " TEXT," + TableData.TableInfo.CIGS_PER_DAY + " TEXT," + TableData.TableInfo.PRICE_PER_PACK +
            " TEXT," + TableData.TableInfo.NUM_YEARS_SMOKED + " TEXT);";

    // FriendStats Query String
    public String CREATE_FRIENDS_STATS_QUERY = "CREATE TABLE " + TableData.TableInfo.FRIENDS_TABLE_NAME + "(" +
            TableData.TableInfo.ID + " INTEGER PRIMARY KEY," + TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.FRIEND_NAME +
            " TEXT," + TableData.TableInfo.FRIEND_ID + " TEXT," + TableData.TableInfo.EMAIL + " TEXT," + TableData.TableInfo.TIME + " TEXT," +
            " TEXT," + TableData.TableInfo.TOTAL_DAYS_FREE + " TEXT," + TableData.TableInfo.LONGEST_STREAK +
            " TEXT," + TableData.TableInfo.CURRENT_STREAK + " TEXT," + TableData.TableInfo.NUM_CRAVINGS +
            " TEXT," + TableData.TableInfo.CRAVINGS_RESISTED + " TEXT," + TableData.TableInfo.NUM_CIGS_SMOKED +
            " TEXT," + TableData.TableInfo.MONEY_SAVED + " TEXT," + TableData.TableInfo.LIFE_REGAINED + " TEXT," +
            TableData.TableInfo.USER_SERVER_ID + " INTEGER," + TableData.TableInfo.FRIEND_OF_ID + " INTEGER);";

    // UserStats Query String
    public String CREATE_USER_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_TABLE_NAME + "(" +
            TableData.TableInfo.ID + " INTEGER PRIMARY KEY," + TableData.TableInfo.USER_NAME +
            " TEXT," + TableData.TableInfo.TIME + " TEXT," + TableData.TableInfo.TOTAL_DAYS_FREE + " TEXT," +
            TableData.TableInfo.LONGEST_STREAK +
            " TEXT," + TableData.TableInfo.CURRENT_STREAK + " TEXT," + TableData.TableInfo.NUM_CRAVINGS +
            " TEXT," + TableData.TableInfo.CRAVINGS_RESISTED + " TEXT," + TableData.TableInfo.NUM_CIGS_SMOKED +
            " TEXT," + TableData.TableInfo.MONEY_SAVED + " TEXT," + TableData.TableInfo.LIFE_REGAINED + " TEXT," +
            TableData.TableInfo.CIGS_PER_DAY + " TEXT," + TableData.TableInfo.PRICE_PER_PACK +
            " TEXT," + TableData.TableInfo.NUM_YEARS_SMOKED + " TEXT," + TableData.TableInfo.USER_SERVER_ID + " INTEGER," +
            TableData.TableInfo.USER_AUTH_ID + " INTEGER);";

    // UserAuthentication Query String
    public String CREATE_USER_AUTH_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_AUTH_TABLE_NAME + "(" +
            TableData.TableInfo.ID + " INTEGER PRIMARY KEY," + TableData.TableInfo.USER_NAME + " TEXT," +
            TableData.TableInfo.PASSWORD + " TEXT," + TableData.TableInfo.EMAIL + " TEXT);";

    //FeedPosts Query string
    public String CREATE_USER_FEED_QUERY= "CREATE TABLE " + TableData.TableInfo.USER_FEED_TABLE_NAME + "(" +
            TableData.TableInfo.ID + " INTEGER PRIMARY KEY," + TableData.TableInfo.DISLIKES + " TEXT," +
            TableData.TableInfo.LIKES + " TEXT," + TableData.TableInfo.FEED_ID + " TEXT," + TableData.TableInfo.DESCRIPTION + " TEXT," +
            TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.USER_SERVER_ID + " TEXT" + TableData.TableInfo.DATE + " TEXT)";



    //DAY_STATS Query string
    public String CREATE_DAY_STATS = "CREATE TABLE " + TableData.TableInfo.DAY_STATS_TABLE_NAME + "(" +
            TableData.TableInfo.ID + " INTEGER PRIMARY KEY," + TableData.TableInfo.DATE + " TEXT," +
            TableData.TableInfo.USER_AUTH_ID + " INTEGER," + TableData.TableInfo.CIGS_SMOKED_ON_DATE + " TEXT," + TableData.TableInfo.CRAVINGS_RESISTED_ON_DATE + " TEXT," +
            TableData.TableInfo.CRAVINGS_ON_DATE + " TEXT," +
            TableData.TableInfo.USER_NAME + " TEXT);";

    // Create Database
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        // Create user_auth Table
        Log.d("Database Operations", "creating user_auth table");
        sdb.execSQL(CREATE_USER_AUTH_QUERY);
        Log.d("Database Operations", "user_auth table created");

        // Create user_demo Table
        Log.d("Database Operations", "creating user_demo table");
        sdb.execSQL(CREATE_USER_DEMO_QUERY);
        Log.d("Database Operations", "user_demo table created");

        // Create user_stats Table
        Log.d("Database Operations", "creating user_stats table");
        sdb.execSQL(CREATE_USER_QUERY);
        Log.d("Database Operations", "user_stats table created");

        // Create friends_stats Table
        Log.d("Database Operations", "creating friends_stats table");
        sdb.execSQL(CREATE_FRIENDS_STATS_QUERY);
        Log.d("Database Operations", "friends_stats table created");

        // Create friends_stats Table
        Log.d("Database Operations", "creating friends_stats table");
        sdb.execSQL(CREATE_USER_FEED_QUERY);
        Log.d("Database Operations", "friends_stats table created");

        sdb.execSQL(CREATE_DAY_STATS);
        Log.d(" Database Operations", "day stats table created ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {

        // Create friends_stats Table
        Log.d("Database Operations", "creating friends_stats table");
        sdb.execSQL(CREATE_FRIENDS_STATS_QUERY);
        Log.d("Database Operations", "friends_stats table created");
    }

    // get current time
    public static String getCurrTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a");
        String time = sdf.format(date).toString();
        return time;
    }

    // pulling user authentication data
    public Cursor getUserAuth(DatabaseOperations dbop) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.ID, TableData.TableInfo.USER_NAME, TableData.TableInfo.PASSWORD};
        Cursor cr = sq.query(TableData.TableInfo.USER_AUTH_TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }

    // pulling from user_demo table
    public Cursor getUserDemo(DatabaseOperations dbop, String username) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.ID, TableData.TableInfo.FIRST_NAME, TableData.TableInfo.LAST_NAME,
                TableData.TableInfo.AGE, TableData.TableInfo.GENDER, TableData.TableInfo.ETHNICITY, TableData.TableInfo.CIGS_PER_DAY,
                TableData.TableInfo.PRICE_PER_PACK, TableData.TableInfo.NUM_YEARS_SMOKED};
        String where = TableData.TableInfo.USER_NAME + " = ?";
        String[] whereArgs = new String[] {username};
        Cursor cr = sq.query(TableData.TableInfo.USER_DEMO_TABLE_NAME, columns, where, whereArgs, null, null, null);
        return cr;
    }

    // pulling from friend_stats table
    public Cursor getFriendStats(DatabaseOperations dbop, String username, String friendID) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.FRIEND_NAME, TableData.TableInfo.FRIEND_ID,
                TableData.TableInfo.EMAIL, TableData.TableInfo.TIME, TableData.TableInfo.TOTAL_DAYS_FREE, TableData.TableInfo.LONGEST_STREAK,
                TableData.TableInfo.CURRENT_STREAK, TableData.TableInfo.NUM_CRAVINGS, TableData.TableInfo.CRAVINGS_RESISTED,
                TableData.TableInfo.NUM_CIGS_SMOKED, TableData.TableInfo.MONEY_SAVED, TableData.TableInfo.LIFE_REGAINED};
        String where = TableData.TableInfo.USER_NAME + " = ? AND " + TableData.TableInfo.FRIEND_ID + " = ?";
        String[] whereArgs = new String[] {username, friendID};
        String orderBy = TableData.TableInfo.TIME;
        Cursor cr = sq.query(TableData.TableInfo.FRIENDS_TABLE_NAME, columns, where, whereArgs, null, null, orderBy);
        return cr;
    }

    // pulling from user_stats table
    public Cursor getUserStats(DatabaseOperations dbop, String username) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.ID, TableData.TableInfo.TIME, TableData.TableInfo.TOTAL_DAYS_FREE,
                TableData.TableInfo.LONGEST_STREAK, TableData.TableInfo.CURRENT_STREAK, TableData.TableInfo.NUM_CRAVINGS,
                TableData.TableInfo.CRAVINGS_RESISTED, TableData.TableInfo.NUM_CIGS_SMOKED, TableData.TableInfo.MONEY_SAVED,
                TableData.TableInfo.LIFE_REGAINED, TableData.TableInfo.CIGS_PER_DAY, TableData.TableInfo.PRICE_PER_PACK, TableData.TableInfo.NUM_YEARS_SMOKED,
                TableData.TableInfo.USER_SERVER_ID, TableData.TableInfo.USER_AUTH_ID} ;
        String where = TableData.TableInfo.USER_NAME + " = ?";
        String[] whereArgs = new String[] {username};
        String orderBy = TableData.TableInfo.TIME + " DESC LIMIT 1";
        Cursor cr = sq.query(TableData.TableInfo.USER_TABLE_NAME, columns, where, whereArgs, null, null, orderBy);
        return cr;
    }

    // adding to user_auth Table
    public void addUserAuth(DatabaseOperations dbop, String username, String password, String email) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.PASSWORD, password);
        cv.put(TableData.TableInfo.EMAIL, email);

        sq.insert(TableData.TableInfo.USER_AUTH_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_auth table");
    }

    // adding to user_demo Table
    public void addUserDemo(DatabaseOperations dbop, String username, String id, String firstName,
                            String lastName, String age, String gender, String ethnicity,
                            String cigsPerDay, String pricePerPack, String numYearsSmoked) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.ID, id);
        cv.put(TableData.TableInfo.FIRST_NAME, firstName);
        cv.put(TableData.TableInfo.LAST_NAME, lastName);
        cv.put(TableData.TableInfo.AGE, age);
        cv.put(TableData.TableInfo.GENDER, gender);
        cv.put(TableData.TableInfo.ETHNICITY, ethnicity);
        cv.put(TableData.TableInfo.CIGS_PER_DAY, cigsPerDay);
        cv.put(TableData.TableInfo.PRICE_PER_PACK, pricePerPack);
        cv.put(TableData.TableInfo.NUM_YEARS_SMOKED, numYearsSmoked);

        sq.insert(TableData.TableInfo.USER_DEMO_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_demo Table");
    }

    // adding to user_stats Table
    public UserEntity addUserStats(DatabaseOperations dbop,UserEntity entity){

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
        cv.put(TableData.TableInfo.CIGS_PER_DAY,entity.getCigsPerDay());
        cv.put(TableData.TableInfo.PRICE_PER_PACK, entity.getPricePerPack());
        cv.put(TableData.TableInfo.NUM_YEARS_SMOKED, entity.getNumYearsSmoked());
        cv.put(TableData.TableInfo.USER_SERVER_ID, entity.getServerId());
        cv.put(TableData.TableInfo.USER_AUTH_ID, entity.getUserAuthId());

        result = sq.insert(TableData.TableInfo.USER_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_stats Table");
        entity.setID(result );
        return entity;
    }

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

    public void updateServerIdForUser(DatabaseOperations dbop, String serverId, int userId) {
        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USER_SERVER_ID, serverId);
        String whereClause =  "id='" + userId;
        int updateResult = sq.update(TableData.TableInfo.USER_TABLE_NAME, cv, whereClause, null);

        Log.d("Database Operations", "Updated server id for current user update result:"+updateResult);
    }

    public void addFeedPosts(DatabaseOperations dbop) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, "");
        sq.insert(TableData.TableInfo.USER_DEMO_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_demo Table");
    }

    public Cursor getUserWithPrimaryId(DatabaseOperations dbop, int primaryId){
        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.ID, TableData.TableInfo.TIME, TableData.TableInfo.TOTAL_DAYS_FREE,
                TableData.TableInfo.LONGEST_STREAK, TableData.TableInfo.CURRENT_STREAK, TableData.TableInfo.NUM_CRAVINGS,
                TableData.TableInfo.CRAVINGS_RESISTED, TableData.TableInfo.NUM_CIGS_SMOKED, TableData.TableInfo.MONEY_SAVED,
                TableData.TableInfo.LIFE_REGAINED, TableData.TableInfo.CIGS_PER_DAY, TableData.TableInfo.PRICE_PER_PACK, TableData.TableInfo.NUM_YEARS_SMOKED,
                TableData.TableInfo.USER_SERVER_ID, TableData.TableInfo.USER_AUTH_ID} ;

        String where = TableData.TableInfo._ID + " = ?";
        String[] whereArgs = new String[] {primaryId+""};
        String orderBy = TableData.TableInfo.TIME + " DESC LIMIT 1";
        Cursor cr = sq.query(TableData.TableInfo.USER_TABLE_NAME, columns, where, whereArgs, null, null, orderBy);
        return cr;
    }


    public int isUserAuthorized(DatabaseOperations ,String username, String password){
        return -1;
    }
}
