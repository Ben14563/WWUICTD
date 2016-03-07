package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button LOGIN_BUTTON = (Button) findViewById(R.id.loginButton);
        LOGIN_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx =null;
                DatabaseOperations db = new DatabaseOperations(ctx);
                //need to verify user exists.

            }
        });
    }


    public void goToDashboard (View view) {
        Intent intent = new Intent (this, Dashboard.class);
        startActivity(intent);
    }

    public void goToSignUp (View view) {
        Intent intent = new Intent (this, SignUp.class);
        startActivity(intent);
    }
}
