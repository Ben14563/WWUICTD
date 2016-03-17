package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.Button;

import org.json.*;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateUser();
        updateUserDemo();
        // update cravings resisted count
        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText(User.getInstance().getCravingsResisted());

        // update money saved total
        moneySaved = (cravsRes * Double.parseDouble(UserDemographics.getInstance().getCostPerPack())) / 20;
        totalMoneySaved = "$" + new DecimalFormat("##.##").format(moneySaved);
        TextView moneySavedAmount = (TextView) findViewById(R.id.moneySavedAmount);
        moneySavedAmount.setText(totalMoneySaved);

        // update total life regained
        TextView lifeRegText = (TextView) findViewById(R.id.lifeRegText);
        lifeRegText.setText(User.getInstance().getLifeRegained());
        getFeed();
    }

    public void updateUserDemo() {

        DatabaseOperations db = new DatabaseOperations(ctx);
        Cursor cr = db.getUserDemo(db, User.getInstance().getUsername());

        if (cr != null && cr.moveToFirst()) {

            firstname = cr.getString(2);
            lastname = cr.getString(3);
            age = cr.getString(4);
            gender = cr.getString(5);
            ethnicity = cr.getString(6);
            cigsPerDay = cr.getString(7);
            pricePerPack = cr.getString(8);
            numYearsSmoked = cr.getString(9);

            UserDemographics.getInstance().setFirstName(firstname);
            UserDemographics.getInstance().setLastName(lastname);
            UserDemographics.getInstance().setAge(age);
            UserDemographics.getInstance().setGender(gender);
            UserDemographics.getInstance().setEthnicity(ethnicity);
            UserDemographics.getInstance().setCigsPerDay(cigsPerDay);
            UserDemographics.getInstance().setCostPerPack(pricePerPack);
            UserDemographics.getInstance().setNumYearsSmoked(numYearsSmoked);
            Log.d("Entered Dashboard", "User demo object updated");
        }
        cr.close();
    }

    public void updateUser() {

        DatabaseOperations db = new DatabaseOperations(ctx);
        Cursor cr = db.getUserStats(db, User.getInstance().getUsername());

        if (cr != null && cr.moveToFirst()) {

            username = cr.getString(0);
            id = cr.getString(1);
            totDaysFree = Integer.parseInt(cr.getString(3));
            longStreak = Integer.parseInt(cr.getString(4));
            currStreak = Integer.parseInt(cr.getString(5));
            cravs = Integer.parseInt(cr.getString(6));
            cravsRes = Integer.parseInt(cr.getString(7));
            numSmokes = Integer.parseInt(cr.getString(8));
            moneySaved = Double.parseDouble(cr.getString(9));
            lifeReg = Integer.parseInt(cr.getString(10));

//            User.getInstance().setID(id);
            User.getInstance().setTotalDaysFree(totDaysFree);
            User.getInstance().setLongestStreak(longStreak);
            User.getInstance().setCurrentStreak(currStreak);
            User.getInstance().setNumCravings(cravs);
            User.getInstance().setCravsRes(cravsRes);
            User.getInstance().setNumCigsSmoked(numSmokes);
            User.getInstance().setMoneySaved(moneySaved);
            User.getInstance().setLifeRegained(lifeReg);
            Log.d("Entered Dashboard", "User object updated");
        }
        cr.close();
    }

    // Crave Button
    public void imCraving(View view) {

        updateUser();
        time = DatabaseOperations.getCurrTime();
        cravs += 1;

        User.getInstance().setNumCravings(cravs);
        incrementFieldOnServer("cravings");
        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, username, User.getInstance().getID() ,time, Integer.toString(totDaysFree),Integer.toString(longStreak), Integer.toString(currStreak),
                Integer.toString(cravs), Integer.toString(cravsRes), Integer.toString(numSmokes), Double.toString(moneySaved),
                Integer.toString(lifeReg));
        Log.d("Crave Button", "Logged 1 craving");
        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", User.getInstance().getUsername() + "'s Crave count: " + User.getInstance().getNumCravings() + " ************");
        db.close();
    }

    // Resist Craving Button
    public void resistCraving(View view) {

        updateUser();
        time = DatabaseOperations.getCurrTime();
        cravsRes += 1;
        lifeReg += 11;

        moneySaved = (cravsRes * Double.parseDouble(UserDemographics.getInstance().getCostPerPack())) / 20;
        totalMoneySaved = "$" + new DecimalFormat("##.##").format(moneySaved);

        User.getInstance().setCravsRes(cravsRes);
        User.getInstance().setLifeRegained(lifeReg);
        incrementFieldOnServer("cravings_resisted");
        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, username, User.getInstance().getID(), time, Integer.toString(totDaysFree), Integer.toString(longStreak), Integer.toString(currStreak),
                Integer.toString(cravs), Integer.toString(cravsRes), Integer.toString(numSmokes), Double.toString(moneySaved),
                Integer.toString(lifeReg));
        Log.d("Resist Craving Button", "Logged 1 craving resisted");
        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", User.getInstance().getUsername() + "'s Crave Resist count: " + User.getInstance().getCravingsResisted() + " ************");

        TextView moneySavedAmount = (TextView) findViewById(R.id.moneySavedAmount);
        moneySavedAmount.setText(totalMoneySaved);

        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText(User.getInstance().getCravingsResisted());

        TextView lifeRegText = (TextView) findViewById(R.id.lifeRegText);
        lifeRegText.setText(User.getInstance().getLifeRegained());

        db.close();
    }

    // Smoked Button
    public void smoked(View view) {

        updateUser();
        time = DatabaseOperations.getCurrTime();
        numSmokes += 1;

        User.getInstance().setNumCigsSmoked(numSmokes);
        incrementFieldOnServer("cigs_smoked");

        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, username, User.getInstance().getID() ,time, Integer.toString(totDaysFree),Integer.toString(longStreak), Integer.toString(currStreak),
                Integer.toString(cravs), Integer.toString(cravsRes), Integer.toString(numSmokes), Double.toString(moneySaved),
                Integer.toString(lifeReg));
        Log.d("Smoked Button", "Logged 1 smoked cigarette");
        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", User.getInstance().getUsername() + "'s Smoke count: " + User.getInstance().getNumCigsSmoked() + " ************");
        db.close();

    }

    // Navigation Buttons
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


    public void incrementFieldOnServer(final String field) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.incrementField(User.getInstance().getID(), field);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    getFeed();
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }

    public void incrementLikesOnPost(final int postid) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.incrementLikes(postid);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    getFeed();
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }


    public void incrementDislikesOnPost(final int postid) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.incrementDislikes(postid);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    getFeed();
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }

    public void getFeed() {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.getFeedForUser(User.getInstance().getID());
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
                            posts[i] = new FeedPost(feedid, date, description, likes, dislikes);
                            View child = getLayoutInflater().inflate(R.layout.post, null);
                            TextView tv = (TextView)child.findViewById(R.id.description);
                            tv.setText(description);
                            tv = (TextView)child.findViewById(R.id.time);
                            tv.setText(date);
                            Button likebtn = (Button)child.findViewById(R.id.likes);
                            if (likes == 1) {
                                likebtn.setText(likes + " Like");
                            } else {
                                likebtn.setText(likes + " Likes");
                            }
                            likebtn.setTag(feedid);
                            likebtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    incrementLikesOnPost((int) ((Button) v).getTag());
                                }
                            });

                            Button dislikebtn = (Button)child.findViewById(R.id.dislikes);
                            dislikebtn.setTag(feedid);
                            dislikebtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    incrementDislikesOnPost((int) ((Button) v).getTag());
                                }
                            });
                            if (dislikes == 1) {
                                dislikebtn.setText(dislikes + " Dislike");
                            } else {
                                dislikebtn.setText(dislikes + " Dislikes");
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
    
}
