package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard extends AppCompatActivity {

    private String username;
    private String time;
    private int totDaysFree;
    private int longStreak;
    private int currStreak;
    private int cravs;
    private int cravsRes;
    private int numSmokes;
    private double moneySaved;
    private int lifeReg;

    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        updateUser();



        // When "I'm Craving" button is pressed
//        CRAVE_BUTTON = (Button) findViewById(R.id.craveButton);
//        CRAVE_BUTTON.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//                // pull from central database first
//                // implement changes to data
//                // update user object
//
//                // push updated stats to local database
//                cravs += 1;
//                Date date = new Date();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
//                time = sdf.format(date);
//
//                DatabaseOperations db = new DatabaseOperations(ctx);
//                db.addUserData(db, username, time, totDaysFree, longStreak, currStreak, cravs, cravsRes, numSmokes, moneySaved, lifeReg);
//                Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
//            }
//        });

    }

    public void updateUser() {

        DatabaseOperations db = new DatabaseOperations(ctx);
        Cursor cr = db.getUserStats(db, User.getInstance().getUsername());

        if (cr != null && cr.moveToFirst()) {

            username = cr.getString(0);
            totDaysFree = Integer.parseInt(cr.getString(2));
            longStreak = Integer.parseInt(cr.getString(3));
            currStreak = Integer.parseInt(cr.getString(4));
            cravs = Integer.parseInt(cr.getString(5));
            cravsRes = Integer.parseInt(cr.getString(6));
            numSmokes = Integer.parseInt(cr.getString(7));
            moneySaved = Double.parseDouble(cr.getString(8));
            lifeReg = Integer.parseInt(cr.getString(9));

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

    // Action Buttons
    public void imCraving(View view) {

        updateUser();
        cravs += 1;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        time = sdf.format(date).toString();

        User.getInstance().setNumCravings(cravs);

        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, username, time, Integer.toString(totDaysFree),Integer.toString(longStreak), Integer.toString(currStreak),
                Integer.toString(cravs), Integer.toString(cravsRes), Integer.toString(numSmokes), Double.toString(moneySaved),
                Integer.toString(lifeReg));
        Log.d("Crave Button", "Logged 1 craving");
        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", User.getInstance().getNumCravings() + " ************");
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
    
}
