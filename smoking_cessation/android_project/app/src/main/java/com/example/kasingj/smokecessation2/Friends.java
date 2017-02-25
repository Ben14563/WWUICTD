package com.example.kasingj.smokecessation2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {

    ArrayList<FriendEntity> buddies = new ArrayList<>();

    UserService userService;
    HttpServices httpServices;
    UserEntity userEntity;
    FriendService friendService;
    SharedPreferences preferences;
    DataItemAdapter adapter;
    RecyclerView recyclerView;

    private final BroadcastReceiver Updated= new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            buddies = friendService.getAllFriends(userEntity.getID());
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(getApplicationContext().getPackageName(), 1);
        int id = preferences.getInt(MainActivity.CURRENT_USER_ID, -1);
        userService = new UserService(this);
        userEntity = userService.getUserEntityWithPrimaryId(id);
        httpServices = new HttpServices(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //pull buddies from db
        friendService = new FriendService(this);
        buddies = friendService.getAllFriends(userEntity.getID());

        adapter = new DataItemAdapter(this,buddies);
        recyclerView = (RecyclerView) findViewById( R.id.rvItems);
        recyclerView.setAdapter(adapter);
        httpServices.getBuddies(userEntity);

        //register observer to update list items
        registerReceiver(Updated, new IntentFilter("data_inserted"));

    }

    /* unregister the receiver */
    @Override
    protected void onStop() {
      unregisterReceiver(Updated);
      super.onStop();
    }


    public void goToDashboard (View view) {
      Intent intent = new Intent(this, Dashboard.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      finish();
      overridePendingTransition(0,0);
      startActivity(intent);
      overridePendingTransition(0,0);
    }

    public void goToFriends (View view) {
      Intent intent = new Intent(this, Friends.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      finish();
      overridePendingTransition(0,0);
      startActivity(intent);
      overridePendingTransition(0,0);
    }

    public void goToStatistics (View view) {
      Intent intent = new Intent (this, Stats.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      finish();
      overridePendingTransition(0,0);
      startActivity(intent);
      overridePendingTransition(0,0);
    }

    public void goToInvite (View view) {
      Intent intent = new Intent (this, Invite.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
      finish();
      overridePendingTransition(0,0);
      startActivity(intent);
      overridePendingTransition(0,0);
    }

//    public void getBuddies() {
//        try {
//            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
//                @Override
//                protected String doInBackground(String... params) {
//                    //result is the json string of the request. might be null
//
//                    HttpRunner runner = new HttpRunner();
//                    String result = runner.getAllBuddies(userEntity.getServerId() + "");
//                    if (result == null) {
//                        return "NULL";
//                    }
//                    return result;
//                }
//
//                @Override
//                protected void onPostExecute(String result) {
//                    //expecting the user id
//                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
//
//                    try {
//                        LinearLayout holder = (LinearLayout)findViewById(R.id.friendlist);
//                        JSONArray arr = new JSONArray(result);
//                        FeedPost[] posts = new FeedPost[arr.length()];
//
//
//                        for (int i = 0; i < arr.length(); i++) {
//                            String name = arr.getJSONObject(i).getString("name");
//
//                            if(!name.equals(userEntity.getUsername()) ) {
//                                JSONObject json = arr.getJSONObject(i);
//                                String time = !json.isNull("time") ? json.getString("time"):"";
//                                String total_days_free = !json.isNull("days_free") ? json.getString("days_free"):"";
//                                String money_saved = !json.isNull("money_saved") ? json.getString("money_saved"):"";
//                                String life_regained = !json.isNull("life_regained") ? json.getString("life_regained"):"";
//                                String cigs_per_day = !json.isNull("cigs_per_day") ? json.getString("cigs_per_day"):"";
//                                String email = !json.isNull("email") ? json.getString("email"):"";
//
//                                //not on jsonobject
//                                String current_streak = "0";
//                                String longest_streak = "0";
//                                String num_cravings = "0";
//                                String cravings_resisted = "0";
//                                String num_cigs_smoked = "0";
//
//                                FriendEntity friend = new FriendEntity();
//
//                                friend.setFriendObject(name,email, time, total_days_free, longest_streak, current_streak, num_cravings, cravings_resisted, num_cigs_smoked, money_saved, life_regained);
//                                FriendDAO dbop = new FriendDAO(getApplicationContext());
//                                friend.setParentId(userEntity.getID());
//
//                                dbop.addFriendStats(dbop, friend);
//                            }
//                        }
//
//                    } catch (JSONException e){
//
//                    }
//                }
//            };
//
//            task.execute("param");
//        } finally {
//            Log.d("Main:addTaskfail", "async failed, or main failed");
//        }
//
//    }
}
