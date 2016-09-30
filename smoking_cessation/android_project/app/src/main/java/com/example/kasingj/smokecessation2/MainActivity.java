package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.regex.*;

public class MainActivity extends AppCompatActivity {

    private String time, id;
    private String username, firstName, lastName, age, gender, ethn, cigsPerDay, pricePerPack, yearSmoked;
    EditText FIRST_NAME, LAST_NAME, AGE, GENDER, ETHNICITY, CIGS_PER_DAY, PRICE_PER_PACK, YEARS_SMOKED;
    Context ctx = this;

    EditText USERNAME, PASSWORD, EMAIL, CON_PASS;
    String password, email, con_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
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
                    saveUserDemo();
                    //db.addUserDemo(db, username, id, "boop", "noop", "12", "Male", "White", cigsPerDay, pricePerPack, "sdfg d");

                    Toast.makeText(getBaseContext(), "Profile creation successful!", Toast.LENGTH_LONG).show();

                    db.close();
                    //go to dashboard

                    Log.d("finish button", "go to dashboard");
                    Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                    startActivity(intent);
                    finish();
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
                    String result = runner.addBuddyToUser(params[0], params[1]);
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
        //before going to dashboard  sanitize data
        if (checkInput() == true){

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

      /*  FIRST_NAME = (EditText) findViewById(R.id.firstNameInput);
        LAST_NAME = (EditText) findViewById(R.id.lastNameInput);
        AGE = (EditText) findViewById(R.id.ageInput);
        GENDER = (EditText) findViewById(R.id.genderInput);
        ETHNICITY = (EditText) findViewById(R.id.ethnicInput);*/

        /*firstName = FIRST_NAME.getText().toString();
        lastName = LAST_NAME.getText().toString();
        age = AGE.getText().toString();
        gender = GENDER.getText().toString();
        ethn = ETHNICITY.getText().toString();*/

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


        //used in gotodashboard.

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

            }


*/public boolean checkInput () {

      USERNAME = (EditText) findViewById(R.id.signUpUserInput);
      PASSWORD = (EditText) findViewById(R.id.signUpPassInput);
      CON_PASS = (EditText) findViewById(R.id.signUpPassConfirm);
      EMAIL = (EditText) findViewById(R.id.signUpEmailInput);
      CIGS_PER_DAY = (EditText)findViewById(R.id.cigsPerDayInput);
      PRICE_PER_PACK = (EditText)findViewById(R.id.costInput);
      YEARS_SMOKED = (EditText)findViewById(R.id.yearsInput);
      cigsPerDay = CIGS_PER_DAY.getText().toString();
      pricePerPack = PRICE_PER_PACK.getText().toString();
      yearSmoked = YEARS_SMOKED.getText().toString();
      username = USERNAME.getText().toString();
      password = PASSWORD.getText().toString();
      con_pass = CON_PASS.getText().toString();
      email = EMAIL.getText().toString();
      String regex = "\\d+(?:\\.\\d+)?";

      //cigsPerDay = (EditText)R.id.cigsPerDayInput ;

      DatabaseOperations dbAuth = new DatabaseOperations(ctx);
      Cursor cr = dbAuth.getUserAuth(dbAuth);
      boolean exist = false;

      //check if username exists
      if (cr != null && cr.moveToFirst()) {
          do {
              if (username.equals(cr.getString(0))) {
                  exist = true;
              }
          } while (cr.moveToNext());
      }

      if (exist == true) {
          Toast.makeText(getBaseContext(), "Username already exists", Toast.LENGTH_LONG).show();
          return false;
      }
      else if (username.equals("")) {
          Toast.makeText(getBaseContext(), "Must enter valid username", Toast.LENGTH_LONG).show();
          return false;
      }
      else if (email.equals("")) {
          Toast.makeText(getBaseContext(), "Email required", Toast.LENGTH_LONG).show();
          return false;
      }
      else if (password.equals("")) {
          Toast.makeText(getBaseContext(), "Password required", Toast.LENGTH_LONG).show();
          return false;
      }
      else if (con_pass.equals("")) {
          PASSWORD.setText("");
          Toast.makeText(getBaseContext(), "Must confirm password", Toast.LENGTH_LONG).show();
          return false;
      }
      else if (!password.equals(con_pass)) {
          PASSWORD.setText("");
          CON_PASS.setText("");
          Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_LONG).show();
          return false;
      } //check everything else.
      else if (!cigsPerDay.matches(regex) ){
          Toast.makeText(getBaseContext(), "cigs per day must be a number.", Toast.LENGTH_LONG).show();
          return false;
      }else if(!pricePerPack.matches(regex)){
          Toast.makeText(getBaseContext(), "cost per pack must be a number.", Toast.LENGTH_LONG).show();
          return false;
      }else if(!yearSmoked.matches(regex)){
          Toast.makeText(getBaseContext(), "num years smoked must be a number.", Toast.LENGTH_LONG).show();
          return false;
      }
          //valid username & password
          User.getInstance().setUsername(username);
          User.getInstance().setPassword(password);
          User.getInstance().setEmail(email);
          UserDemographics.getInstance().setCostPerPack(pricePerPack);
          UserDemographics.getInstance().setCigsPerDay(cigsPerDay);
      UserDemographics.getInstance().setNumYearsSmoked(yearSmoked);
          Log.d("Next Button", "initialized username, password, email");

          //add user to db
          DatabaseOperations db = new DatabaseOperations(ctx);
          db.addUserAuth(db, username, password, email);

      cr.close();
      dbAuth.close();
      return true;
    /*done checking authentification information*/
  }
}

