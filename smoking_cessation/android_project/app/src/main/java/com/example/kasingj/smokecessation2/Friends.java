package com.example.kasingj.smokecessation2;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class Friends extends AppCompatActivity {

    ArrayList<FriendEntity> buddies = new ArrayList<>();

    UserService userService;
    UserEntity userEntity;
    FriendService friendService;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(getApplicationContext().getPackageName(), 1);
        int id = preferences.getInt(MainActivity.CURRENT_USER_ID, -1);
        userService = new UserService(this);
        userEntity = userService.getUserEntityWithPrimaryId(id);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //pull buddies from db
        friendService = new FriendService(this);
        buddies = friendService.getAllFriends(userEntity.getID());

//        for(int i = 0; i< 60; i++){
//            //create friendsObjects
//            FriendEntity friend = new FriendEntity();
//            friend.setEmail("John@Gmail.com" + i);
//            buddies.add(friend);
//        }

        DataItemAdapter adapter = new DataItemAdapter(this,buddies);
        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.rvItems);
        recyclerView.setAdapter(adapter);
        //getBuddies();
    }


    public void goToInvite (View view) {
        Intent intent = new Intent (this, Invite.class);
        startActivity(intent);
    }

    public void goToDashboard (View view) {
        Intent intent = new Intent (this, Dashboard.class);
        startActivity(intent);
    }

    public void goToFriends (View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
    }

    public void goToStatistics (View view) {
        Intent intent = new Intent (this, Statistics.class);
        startActivity(intent);
    }

    public void getBuddies() {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null

                    HttpRunner runner = new HttpRunner();
                    String result = runner.getAllBuddies(userEntity.getServerId() + "");
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);

                    try {
                        LinearLayout holder = (LinearLayout)findViewById(R.id.friendlist);
                        JSONArray arr = new JSONArray(result);
                        FeedPost[] posts = new FeedPost[arr.length()];


                        for (int i = 0; i < arr.length(); i++) {
                            String name = arr.getJSONObject(i).getString("name");

                            if(!name.equals(userEntity.getUsername()) ) {
                                String time = arr.getJSONObject(i).getString("time");
                                String total_days_free = arr.getJSONObject(i).getString("days_free");
                                String money_saved = arr.getJSONObject(i).getString("money_saved");
                                String life_regained = arr.getJSONObject(i).getString("life_regained");
                                String cigs_per_day = arr.getJSONObject(i).getString("cigs_per_day");
                                String email = arr.getJSONObject(i).getString("email");

                                //not on jsonobject
                                String current_streak = "0";
                                String longest_streak = "0";
                                String num_cravings = "0";
                                String cravings_resisted = "0";
                                String num_cigs_smoked = "0";

                                FriendEntity friend = new FriendEntity();

                                friend.setFriendObject(name,email, time, total_days_free, longest_streak, current_streak, num_cravings, cravings_resisted, num_cigs_smoked, money_saved, life_regained);
                                FriendDAO dbop = new FriendDAO(getApplicationContext());
                                friend.setParentId(userEntity.getID());

                                dbop.addFriendStats(dbop, friend);
                            }
                        }

                    } catch (JSONException e){

                    }
//                    try {
//                        LinearLayout holder = (LinearLayout)findViewById(R.id.friendlist);
//                        JSONArray arr = new JSONArray(result);
//                        FeedPost[] posts = new FeedPost[arr.length()];
//
//                        for (int i = 0; i < arr.length(); i++) {
//                            String name = arr.getString(i);
//
//                            if(!name.equals(userEntity.getUsername())) {
//                                //create friend entity add to list
//                                FriendEntity ent = new FriendEntity();
//                                ent.setEmail(arr.getString(i));
//                                buddies.add(ent);
//                            }
//                        }
//
//                    } catch (JSONException e){
//
//                    }








//                    LinearLayout holder = (LinearLayout)findViewById(R.id.friendlist);
//                    try {
//                        JSONArray arr = new JSONArray(result);
//                        FeedPost[] posts = new FeedPost[arr.length()];
//                        holder.removeAllViews();
//
//                        for (int i = 0; i < arr.length(); i++) {
//                            View child = getLayoutInflater().inflate(R.layout.friend, null);
//                            TextView tv = (TextView) child.findViewById(R.id.name);
//                            String name = arr.getString(i);
//                            tv.setText(name);
//                            if(!name.equals(userEntity.getUsername())) {
//                                holder.addView(child);
//                            }
//                        }
//                    } catch (JSONException e) {
//
//                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }
}
