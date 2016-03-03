package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard extends AppCompatActivity {

    static String username, time;
    static int totDaysFree;
    static int longStreak;
    static int currStreak;
    static int cravs;
    static int cravsRes;
    static int numSmokes;
    static int lifeReg;
    static double moneySaved;

    Button CRAVE_BUTTON;
    Button CRAVE_RES_BUTTON;
    Button SMOKED_BUTTON;
    Context ctx = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // When "I'm Craving" button is pressed
        CRAVE_BUTTON = (Button) findViewById(R.id.craveButton);
        CRAVE_BUTTON.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // pull from central database first

                // update current stats and push
                cravs += 1;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                time = sdf.format(date);

                DatabaseOperations db = new DatabaseOperations(ctx);
                db.addUserData(db, username, time, totDaysFree, longStreak, currStreak, cravs, cravsRes, numSmokes, moneySaved, lifeReg);
                Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
            }
        });

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
