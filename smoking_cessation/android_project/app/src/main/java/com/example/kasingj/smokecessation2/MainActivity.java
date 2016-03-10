package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String time;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //creating fragments for intro activities
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Current Habits"));
        tabLayout.addTab(tabLayout.newTab().setText("Goals"));
        tabLayout.addTab(tabLayout.newTab().setText("Demographics"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

/*
        Button signUpButton = (Button)findViewById(R.id.signUpButton); // example button
        signUpButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    String result = new HttpRunner().getFriendInformation("which friend?");
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {

                }
            };
        }}); // end of async task for clicking a button the httpRunner is abstract but requires a unique url and functions to parse/connect to main thread.*/
    }




    public void goToDashboard (View view) {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        time = sdf.format(date).toString();

        User.getInstance().setTime(time);
        User.getInstance().setTotalDaysFree(0);
        User.getInstance().setLongestStreak(0);
        User.getInstance().setCurrentStreak(0);
        User.getInstance().setNumCravings(0);
        User.getInstance().setCravsRes(0);
        User.getInstance().setNumCigsSmoked(0);
        User.getInstance().setMoneySaved(0.00);
        User.getInstance().setLifeRegained(0);
        Log.d("Finish Button", "initialized user stats");

        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, User.getInstance().getUsername(), time, "0", "0", "0", "0", "0", "0", "0.00", "0");
        Toast.makeText(getBaseContext(), "Profile creation successful!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent (this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    public void goToFriends (View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
    }

    public void goToStatistics (View view) {
        Intent intent = new Intent (this, Statistics.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

