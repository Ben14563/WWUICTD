package com.example.kasingj.smokecessation2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    Button NEXT_BUTTON;
    EditText user_name;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NEXT_BUTTON = (Button) findViewById(R.id.signUpNextButton);
        NEXT_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = (EditText) findViewById(R.id.signUpUserInput);
                username = user_name.toString();

                User.getInstance().setUsername(username);

            }
        });
    }

    public void goToMain (View view) {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

}
