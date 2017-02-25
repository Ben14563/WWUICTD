package com.example.kasingj.smokecessation2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.Button;

import org.json.*;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Dashboard extends AppCompatActivity {

    private String username;
    private String time;
    private String id;
    private int totDaysFree;
    private int longStreak;
    private int currStreak;
    private int cravs;
    private int cravsRes;
    private int numSmokes;
    private double moneySaved;
    private int lifeReg;
    private String firstname;
    private String lastname;
    private String age;
    private String gender;
    private String ethnicity;
    private String cigsPerDay;
    private String pricePerPack;
    private String numYearsSmoked;
    private String totalMoneySaved;
    private Integer serverId;


    Context ctx = this;
    DatabaseOperations UserDAO = new DatabaseOperations(ctx);
    private UserService userService;
    private HttpServices httpServices;
    private SharedPreferences preferences;
    private UserEntity userEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userService = new UserService(this);
        httpServices = new HttpServices(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(getApplicationContext().getPackageName(), 1);
        int id = preferences.getInt(MainActivity.CURRENT_USER_ID, -1);

        userEntity = userService.getUserEntityWithPrimaryId(id);
        // update cravings resisted count
        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText( ""+userEntity.getCravingsResisted());
        // update money saved total still does not like parse line.
        //moneySaved = (cravsRes * Double.parseDouble( userEntity.getPricePerPack() )) / 20;
        totalMoneySaved = "$" + new DecimalFormat("##.##").format( Double.parseDouble(userEntity.getMoneySaved()) );
        TextView moneySavedAmount = (TextView) findViewById(R.id.moneySavedAmount);
        moneySavedAmount.setText(totalMoneySaved);

        // update total life regained
        TextView lifeRegText = (TextView) findViewById(R.id.lifeRegText);
        lifeRegText.setText(userEntity.getLifeRegained());


        userEntity = userService.getUserEntityWithPrimaryId(id);
        if(userEntity.getServerId() != -1 ){
            getFeed(userEntity);
        }
    }

    // Crave Button
    public void imCraving(View view) {
        //int id = preferences.getInt(MainActivity.CURRENT_USER_ID, -1);
        //int id = (userEntity !=null) ? userEntity.getID() : preferences.getInt(MainActivity.CURRENT_USER_ID, -1);
        userEntity = userService.getUserEntityWithPrimaryId(userEntity.getID());
        time = DatabaseOperations.getCurrTime();
        userEntity.setNumCravings(1 + Integer.parseInt(userEntity.getNumCravings()) );

        if( userEntity.getServerId() > 0)   {
            incrementFieldOnServer("cravings",userEntity);
        }else{
            httpServices.addUserToServer(userEntity);
        }

        userService.updateUser(userEntity);
        userService.addUserHist(userEntity, 0, 1, 0);

        Log.d("Crave Button", "Logged 1 craving");
//        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", userEntity.getUsername() + "'s Crave count: " + userEntity.getNumCravings() + " ************");
    }

    // Resist Craving Button
    public void resistCraving(View view) {

        //UserEntity userEntity = userService.getUserEntityWithPrimaryId(1);
        time = DatabaseOperations.getCurrTime();
        //cravsRes += 1;
        //lifeReg += 11;
        userEntity = userService.getUserEntityWithPrimaryId(userEntity.getID() );
        userEntity.setCravsRes(userEntity.getCravingsResisted()+1 );
        userEntity.setLifeRegained(Integer.parseInt(userEntity.getLifeRegained()) + 11);
        userEntity.setMoneySaved((userEntity.getCravingsResisted() * Double.parseDouble(userEntity.getPricePerPack()) / 20));

        //moneySaved = (cravsRes * Double.parseDouble(userEntity.getPricePerPack())) / 20;
        totalMoneySaved = "$" + new DecimalFormat("##.##").format( Double.parseDouble(userEntity.getMoneySaved()));

        if( userEntity.getServerId() > 0)   {
            incrementFieldOnServer("cravings_resisted",userEntity);
        }else{
            httpServices.addUserToServer(userEntity);
        }


        userService.updateUser(userEntity);
        userService.addUserHist(userEntity, 0, 0, 1);

        Log.d("Resist Craving Button", "Logged 1 craving resisted");
//        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", userEntity.getUsername() + "'s Crave Resist count: " + userEntity.getCravingsResisted() + " ************");

        TextView moneySavedAmount = (TextView) findViewById(R.id.moneySavedAmount);
        moneySavedAmount.setText(totalMoneySaved);

        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText("" + userEntity.getCravingsResisted());

        TextView lifeRegText = (TextView) findViewById(R.id.lifeRegText);
        lifeRegText.setText(userEntity.getLifeRegained());

    }

    // Smoked Button
    public void smoked(View view) {

        //UserEntity userEntity = userService.getUserEntityWithPrimaryId(1);
        time = DatabaseOperations.getCurrTime();
        //numSmokes += 1;
        userEntity = userService.getUserEntityWithPrimaryId(userEntity.getID() );
        userEntity.setNumCigsSmoked( Integer.parseInt(userEntity.getNumCigsSmoked() ) + 1);
        if( userEntity.getServerId() > 0)   {
            incrementFieldOnServer("cigs_smoked",userEntity);
        }else{
            httpServices.addUserToServer(userEntity);
        }

        //userService.saveUserEntity(userEntity);
        userService.updateUser(userEntity);
        userService.addUserHist(userEntity, 1, 0, 0);

        Log.d("Smoked Button", "Logged 1 smoked cigarette");
//        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", userEntity.getUsername() + "'s Smoke count: " + userEntity.getNumCigsSmoked() + " ************");


    }

    // Navigation Buttons
    public void goToDashboard (View view) {
        if(getClass() != Dashboard.class) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            finish();
        }
    }

    public void goToFriends (View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        finish();
    }

    public void goToStatistics (View view) {
        Intent intent = new Intent (this, Stats.class);
        startActivity(intent);
        finish();
    }


    public void incrementFieldOnServer(final String field ,final UserEntity mEntity) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.incrementField(""+ mEntity.getServerId(), field);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    //UserEntity userEntity = userService.getUserEntityWithPrimaryId(1);
                    if(userEntity.getServerId() != -1 ){
                        getFeed(userEntity);
                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }

    public void incrementLikesOnPost(final int postid, final FeedPost thisPost) {
        try {

            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result;
                    String reaction = convertReactionTableToResult(UserDAO.getUserReaction(UserDAO, userEntity.getID(), postid), postid);
                    if (reaction.equals("")) { // || .equals("dislike") -> when decrementLikes is written
                        result = runner.incrementLikes(postid);
                    } else {
                        result = "true"; //BRETT: What is result?
                    }
                    if (result == null) {
                        return "NULL";
                    }
                    if (reaction.equals("")) {
                        UserDAO.addUserReaction(UserDAO, userEntity.getID(), postid, "like");
                    } else if (reaction.equals("dislike")) {
                        //when decrementLikes is written
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    //UserEntity userEntity = userService.getUserEntityWithPrimaryId(1);
                    if(userEntity.getServerId() != -1 ){
                        getFeed(userEntity);
                    }
                }

            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }


    public void incrementDislikesOnPost(final int postid, final FeedPost thisPost) {
        try {

            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result;
                    String reaction = convertReactionTableToResult(UserDAO.getUserReaction(UserDAO, userEntity.getID(), postid), postid);
                    if (reaction.equals("")) { // || .equals("like") -> when decrementDislikes is written
                        result = runner.incrementDislikes(postid);
                    } else {
                        result = "true"; //BRETT: What is result?
                    }
                    if (result == null) {
                        return "NULL";
                    }
                    if (reaction.equals("")) {
                        int returned = UserDAO.addUserReaction(UserDAO, userEntity.getID(), postid, "dislike");
                    } else if (reaction.equals("like")) {
                        //when decrementDislikes is written
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                   // UserEntity userEntity = userService.getUserEntityWithPrimaryId(1);
                    if(userEntity.getServerId() != -1 ){
                        getFeed(userEntity);
                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }
    }

    public void getFeed(final UserEntity entity) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();

                    String result = runner.getFeedForUser( ""+entity.getServerId() );
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "********** feed: " + result);
                    try {
                        JSONArray arr = new JSONArray(result);
                        FeedPost[] posts = new FeedPost[arr.length()];
                        LinearLayout holder = (LinearLayout)findViewById(R.id.feedlayout);
                        holder.removeAllViews();

                        for (int i = 0; i < arr.length(); i++) {
                            String date = arr.getJSONObject(i).getString("date");
                            int dislikes = arr.getJSONObject(i).getInt("dislikes");
                            int likes = arr.getJSONObject(i).getInt("likes");
                            int feedid = arr.getJSONObject(i).getInt("feedid");
                            String description = arr.getJSONObject(i).getString("description");
                            Log.d("htt:add:postExecute", "********** feed: " + description);
                            posts[i] = new FeedPost(feedid, date, description, likes, dislikes, false);
                            View child = getLayoutInflater().inflate(R.layout.post, null);

                            TextView tv = (TextView)child.findViewById(R.id.description);
                            tv.setText(description);
                            tv = (TextView)child.findViewById(R.id.time);
                            tv.setText(date);
                            ImageView img = (ImageView)child.findViewById(R.id.img);

                            if (description.indexOf("resisted") > -1) {
                                img.setImageResource(R.drawable.no_smoking);
                                Log.d("ASDf","resisted");
                            } else if(description.indexOf("craving") > -1) {
                                img.setImageResource(R.drawable.craving);

                                Log.d("ASDF","craving");
                            } else if (description.indexOf("smoked") > -1) {
                                img.setImageResource(R.drawable.smoking);

                                Log.d("ASDf","smoked");
                            }

                            Button likebtn = (Button)child.findViewById(R.id.likes);
                            if (likes == 1) {
                                likebtn.setText(likes + " Like");
                            } else {
                                likebtn.setText(likes + " Likes");
                            }
                            likebtn.setTag(feedid);
                            setOnClick(likebtn, posts[i]);


                            Button dislikebtn = (Button)child.findViewById(R.id.dislikes);
                            dislikebtn.setTag(feedid);
                            setDisOnClick(dislikebtn, posts[i]);
                            if (dislikes == 1) {
                                dislikebtn.setText(dislikes + " Dislike");
                            } else {
                                dislikebtn.setText(dislikes + " Dislikes");
                            }
                            /* TODO: Test Bold to make sure it doesn't cause issues with the button size (>99 likes) */
                            String reaction = convertReactionTableToResult(UserDAO.getUserReaction(UserDAO, userEntity.getID(), feedid), feedid);
                            if (reaction.equals("like")) {
                                likebtn.setTextColor(Color.BLUE);
                                likebtn.setTypeface(null, Typeface.BOLD);
                            } else if (reaction.equals("dislike")) {
                                dislikebtn.setTextColor(Color.RED);
                                dislikebtn.setTypeface(null, Typeface.BOLD);
                            }

                            holder.addView(child);
                        }

                    } catch (JSONException e) {
                        Log.d("http:add:postExecute", "********** feed: failed to parse json");

                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }
    private String convertReactionTableToResult(Cursor cr, int postid){
        if(cr.moveToFirst()){
            do {
                if (postid == cr.getInt(1)) {
                    Log.d("convertReaction:success", "User has already " + cr.getString(2) + "d this post.");
                    return cr.getString(2);
                }
            } while(cr.moveToNext());
        }
        cr.close();
        Log.d("convertReaction:success", "User has not yet reacted to this post.");
        return "";
    }

    /* Wrappers for passing additional parameters */
    private void setOnClick(final Button btn, final FeedPost thisPost){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementLikesOnPost((int) ((Button) v).getTag(), thisPost);
            }
        });
    }
    private void setDisOnClick(final Button btn, final FeedPost thisPost){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementDislikesOnPost((int) ((Button) v).getTag(), thisPost);
            }
        });
    }
}
