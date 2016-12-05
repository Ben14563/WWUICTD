package com.example.kasingj.smokecessation2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Invite extends AppCompatActivity {

    EditText EMAIL;
    String email;
    //HttpServices mHttpServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mHttpServices = new HttpServices(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void addFriend(View view) {
        // check if friend exists
        EMAIL = (EditText) findViewById(R.id.inviteInput);
        email = EMAIL.getText().toString();
        UserService userService = new UserService(this);
        SharedPreferences preference = getSharedPreferences( getApplicationContext().getPackageName() , 1 );
        int id = preference.getInt(MainActivity.CURRENT_USER_ID,-1);
        UserEntity userEntity = userService.getUserEntityWithPrimaryId(id);
        addFriend(email,userEntity);
        // if exists, add friend to friends list, go back to friends page
        Intent intent = new Intent(this, Friends.class);
        startActivity(intent);
        finish();
    }

    public void addFriend(final String email, final UserEntity entity) {
        try {
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    //result is the json string of the request. might be null
                    HttpRunner runner = new HttpRunner();
                    String result = runner.addBuddyToUser(entity.getServerId()+"", email);
                    if (result == null) {
                        return "NULL";
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String result) {
                    //expecting the user id
                    Log.d("htt:add:postExecute", "**********  updated field: " + result);
                    if (result == "false") {
                        Toast.makeText(getBaseContext(), "Friend not found", Toast.LENGTH_LONG).show();
                    }else{
                        //mHttpServices.getBuddies(user);
                    }
                }
            };

            task.execute("param");
        } finally {
            Log.d("Main:addTaskfail", "async failed, or main failed");
        }

    }

}
