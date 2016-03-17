package com.example.kasingj.smokecessation2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Invite extends AppCompatActivity {

    EditText EMAIL;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void addFriend (View view) {
        // check if friend exists
        EMAIL = (EditText) findViewById(R.id.inviteInput);
        email = EMAIL.getText().toString();


        // if exists, add friend to friends list, go back to friends page
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        finish();
    }

}
