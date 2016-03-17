package com.example.kasingj.smokecessation2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import java.util.logging.Handler;

public class Splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent = new Intent(Splash.this, login.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
