package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import javax.microedition.khronos.egl.EGLDisplay;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MainActivity extends AppCompatActivity {

    private String time, id;
    private String username, firstName, lastName, age, gender, ethn, cigsPerDay, pricePerPack, yearSmoked;
    EditText FIRST_NAME, LAST_NAME, AGE, GENDER, ETHNICITY, CIGS_PER_DAY, PRICE_PER_PACK, YEARS_SMOKED;
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

//                FIRST_NAME = (EditText) findViewById(R.id.firstNameInput);
//                LAST_NAME = (EditText) findViewById(R.id.lastNameInput);
//                AGE = (EditText) findViewById(R.id.ageInput);
//                GENDER = (EditText) findViewById(R.id.genderInput);
//                ETHNICITY = (EditText) findViewById(R.id.ethnicInput);
//
//                firstName = FIRST_NAME.getText().toString();
//                lastName = LAST_NAME.getText().toString();
//                age = AGE.getText().toString();
//                gender = GENDER.getText().toString();
//                ethn = ETHNICITY.getText().toString();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

//                PRICE_PER_PACK = (EditText) findViewById(R.id.costInput);
//                pricePerPack = PRICE_PER_PACK.getText().toString();

                CIGS_PER_DAY = (EditText) findViewById(R.id.cigsPerDayInput);
                PRICE_PER_PACK = (EditText) findViewById(R.id.costInput);
                YEARS_SMOKED = (EditText) findViewById(R.id.yearsInput);

                cigsPerDay = CIGS_PER_DAY.getText().toString();
                pricePerPack = PRICE_PER_PACK.getText().toString();
                yearSmoked = YEARS_SMOKED.getText().toString();

                UserDemographics.getInstance().setCigsPerDay(cigsPerDay);
                UserDemographics.getInstance().setCostPerPack(pricePerPack);
                UserDemographics.getInstance().setNumYearsSmoked(yearSmoked);

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

    public void addUserToServer() {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.addUser(User.getInstance().getUsername(), User.getInstance().getEmail(), "20", "10.00");
                    Log.d("http:doInBackground", "***** newUser id: " + result);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "********** newUser id: " + result);
                    User.getInstance().setID(result); //result may be json so need to parse.
                    id = result;
                    time = DatabaseOperations.getCurrTime();
                    username = User.getInstance().getUsername();

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
                    db.addUserStats(db, username, id, time, "0", "0", "0", "0", "0", "0", "0.00", "0");
                    //saveUserDemo();
                    Toast.makeText(getBaseContext(), "Profile creation successful!", Toast.LENGTH_LONG).show();

                    db.close();
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }

    public void getUserStats(String friendId){
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.getUserInformation(params[0]);
                    Log.d("http:getUserStats", "**user: "+params[0]+ "data: " + result);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:data:postExecute", "fiend data" + result);

                    //result will be  json so need to parse. need function to parse data add to database
                    //do stuff with user data
                }
            };

            task.execute(friendId);
        } finally {
            Log.d("Main:addTaskfail","async failed, or main failed");
        }

    }

    public void addBuddyToUser(String friendId, String email){
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.addBuddyToUser(params[0],params[1]);
                    Log.d("http:addBuddyToUser", "**user: "+params[0]+ "data: " + result);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("addBuddy:postExecute", "fiend data" + result);
                    //if no data returned then delete on post execute.
                }
            };

            task.execute(friendId, email);
        } finally {
            Log.d("Main:addTaskfail","async failed, or main failed");
        }

    }

    public void goToDashboard(View view) {
        ConnectivityManager connMgr = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null && networkInfo.isConnected()) {

            Log.d("goToDashBoard", "*********No_Connectivity***********");
            Toast.makeText(getBaseContext(), "Please connect to the internet before proceeding", Toast.LENGTH_LONG).show();
        } else {
            // fetch data
            //add user to database
            addUserToServer(); //should populate user ID field

            if (User.getInstance().getID().equals("")) {
                //addUserToServer(); //should populate user ID field
                if (User.getInstance().getID().equals("")) {
                    try {
                        Toast.makeText(getBaseContext(), "Please wait for account creation", Toast.LENGTH_LONG).show();
                        Thread.sleep(5000);

                    } catch (InterruptedException e) {
                        Log.d("Main:Dashboard:sleep", "sleep interrupted");
                    }
                }
                Log.d("Main:Dashboard:sleep", "user id= " + User.getInstance().getID());


                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void goToFriends(View view) {
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
    }

    public void goToStatistics(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    // save user demographics
    public void saveUserDemo() {

        FIRST_NAME = (EditText) findViewById(R.id.firstNameInput);
        LAST_NAME = (EditText) findViewById(R.id.lastNameInput);
        AGE = (EditText) findViewById(R.id.ageInput);
        GENDER = (EditText) findViewById(R.id.genderInput);
        ETHNICITY = (EditText) findViewById(R.id.ethnicInput);

        firstName = FIRST_NAME.getText().toString();
        lastName = LAST_NAME.getText().toString();
        age = AGE.getText().toString();
        gender = GENDER.getText().toString();
        ethn = ETHNICITY.getText().toString();

        DatabaseOperations dbDemo = new DatabaseOperations(ctx);
        dbDemo.addUserDemo(dbDemo, username, id, firstName, lastName, age, gender, ethn, cigsPerDay, pricePerPack, yearSmoked);

        dbDemo.close();
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
  /*  private class asyncAddFriends extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //result is the json string of the request. might be null
            HttpRunner runner = new HttpRunner();
            String result = runner.addUser(User.getInstance().getUsername(),User.getInstance().getEmail(), "20" , "10.00"  );
            Log.d("http:doInBackground","***** newUser id: "+result );
            if (result == null){
                return "NULL";
            }
            return result;
        }
        @Override
        protected void onPostExecute(String result) {
            //expecting the user id
            Log.d("htt:add:postExecute","********** newUser id: "+result );
            User.getInstance().setID(result); //result may be json so need to parse.
        }
    }
*/

public void clearButton(View v){
    TextView tv = (TextView)findViewById(R.id.motivationBox);
    TextView tv2 = (TextView)findViewById(R.id.motivationBox2);
    TextView tv3 = (TextView)findViewById(R.id.motivationBox3);
    tv.setText("");
    tv2.setText("");
    tv3.setText("");
}



}

