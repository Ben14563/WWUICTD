package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText(User.getInstance().getCravingsResisted());

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

    // Crave Button
    public void imCraving(View view) {

        updateUser();
        time = DatabaseOperations.getCurrTime();
        cravs += 1;

        User.getInstance().setNumCravings(cravs);

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

        User.getInstance().setCravsRes(cravsRes);

        DatabaseOperations db = new DatabaseOperations(ctx);
        db.addUserStats(db, username, User.getInstance().getID(), time, Integer.toString(totDaysFree), Integer.toString(longStreak), Integer.toString(currStreak),
                Integer.toString(cravs), Integer.toString(cravsRes), Integer.toString(numSmokes), Double.toString(moneySaved),
                Integer.toString(lifeReg));
        Log.d("Resist Craving Button", "Logged 1 craving resisted");
        Toast.makeText(getBaseContext(), "Motivational Quote", Toast.LENGTH_LONG).show();
        Log.d("********TEST: ", User.getInstance().getUsername() + "'s Crave Resist count: " + User.getInstance().getCravingsResisted() + " ************");

        TextView resCraveText = (TextView) findViewById(R.id.resCraveCount);
        resCraveText.setText(User.getInstance().getCravingsResisted());

        db.close();
    }

    // Smoked Button
    public void smoked(View view) {

        updateUser();
        time = DatabaseOperations.getCurrTime();
        numSmokes += 1;

        User.getInstance().setNumCigsSmoked(numSmokes);

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
    
}
