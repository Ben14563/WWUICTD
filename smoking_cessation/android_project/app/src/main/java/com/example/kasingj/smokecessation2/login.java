package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login extends AppCompatActivity {

    EditText USERNAME, PASSWORD;
    String username, password;
    Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // authenticates users: if successful, go to dashboard
    public void goToDashboard (View view) {

        USERNAME = (EditText) findViewById(R.id.userNameInput);
        PASSWORD = (EditText) findViewById(R.id.passwordInput);
        username = USERNAME.getText().toString();
        password = PASSWORD.getText().toString();

        DatabaseOperations db = new DatabaseOperations(ctx);
        Cursor cr = db.getUserAuth(db);
        boolean loginStatus = false;

        if (cr != null && cr.moveToFirst()) {
            do {
                if (username.equals(cr.getString(0)) && password.equals(cr.getString(1))) {
                    loginStatus = true;
                }
            } while (cr.moveToNext());
        }
        if (loginStatus == true) {
            User.getInstance().setUsername(username);
            Toast.makeText(getBaseContext(), "Login Successful!", Toast.LENGTH_LONG).show();

            cr.close();
            db.close();
            Intent intent = new Intent (this, Dashboard.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();
            USERNAME.setText("");
            PASSWORD.setText("");
        }

    }

    // go to sign up page
    public void goToSignUp (View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
