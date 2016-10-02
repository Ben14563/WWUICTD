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
        public static final String PASSWORD = "password";
        public static final String ID = "id";
        public static final String FRIEND_ID = "friend_id";
        public static final String FRIEND_NAME = "friend";
        public static final String EMAIL = "email";
        public static final String TIME = "time";
        public static final String TOTAL_DAYS_FREE = "total_days_free";
        public static final String LONGEST_STREAK = "longest_streak";
        public static final String CURRENT_STREAK = "current_streak";
        public static final String NUM_CRAVINGS = "num_cravings";
        public static final String CRAVINGS_RESISTED = "cravings_resisted";
        public static final String NUM_CIGS_SMOKED = "num_cigs_smoked";
        public static final String MONEY_SAVED = "money_saved";
        public static final String LIFE_REGAINED = "life_regained";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String AGE = "age";
        public static final String GENDER = "gender";
        public static final String ETHNICITY = "ethnicity";
        public static final String CIGS_PER_DAY = "cigs_per_day";
        public static final String PRICE_PER_PACK = "price_per_pack";
        public static final String NUM_YEARS_SMOKED = "num_years_smoked";
        public static final String DATABASE_NAME = "app_data2"; //change this to reset db then back to dbop change dp version back to 1
        public static final String USER_DEMO_TABLE_NAME = "user_demo";
        public static final String USER_AUTH_TABLE_NAME = "user_auth";
        public static final String USER_TABLE_NAME = "user_stats";
        public static final String USER_FRIENDS_TABLE_NAME = "user_friends";
        public static final String FRIENDS_TABLE_NAME = "friends_stats";
        public static final String SERVER_ID = "server_id";
        public static final String USER_AUTH_ID = "user_auth_id";
        public static final String FRIEND_OF_ID = "friend_of_id";

    }

}
