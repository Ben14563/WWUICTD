package com.example.kasingj.smokecessation2;

import android.provider.BaseColumns;

/**
 * Created by Alvin on 3/3/2016.
 */
public class TableData {

    public TableData() {

    }

    public static abstract class TableInfo implements BaseColumns {

        public static final String USER_NAME = "user_name";
        public static final String FRIEND = "friend";
        public static final String TIME = "time";
        public static final String TOTAL_DAYS_FREE = "total_days_free";
        public static final String LONGEST_STREAK = "longest_streak";
        public static final String CURRENT_STREAK = "current_streak";
        public static final String NUM_CRAVINGS = "num_cravings";
        public static final String CRAVINGS_RESISTED = "cravings_resisted";
        public static final String NUM_CIGS_SMOKED = "num_cigs_smoked";
        public static final String MONEY_SAVED = "money_saved";
        public static final String LIFE_REGAINED = "life_regained";
        public static final String DATABASE_NAME = "user_info";
        public static final String USER_TABLE_NAME = "user_stats";
        public static final String USER_FRIENDS_TABLE_NAME = "user_friends";
        public static final String FRIENDS_TABLE_NAME = "friends_stats";
    }

}
