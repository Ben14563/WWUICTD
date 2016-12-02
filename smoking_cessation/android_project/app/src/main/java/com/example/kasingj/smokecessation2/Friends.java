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
        for(int i = 0; i< 12; i++){
            //create friendsObjects
            FriendEntity friend = new FriendEntity();
            friend.setEmail("John@Gmail.com");
            buddies.add(friend);
        }

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
                    LinearLayout holder = (LinearLayout)findViewById(R.id.friendlist);
                    try {
                        JSONArray arr = new JSONArray(result);
                        FeedPost[] posts = new FeedPost[arr.length()];
                        holder.removeAllViews();

                        for (int i = 0; i < arr.length(); i++) {
                            View child = getLayoutInflater().inflate(R.layout.friend, null);
                            TextView tv = (TextView) child.findViewById(R.id.name);
                            String name = arr.getString(i);
                            tv.setText(name);
                            if(!name.equals(userEntity.getUsername())) {
                                holder.addView(child);
                            }
                        }
                    } catch (JSONException e) {

                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }
}
