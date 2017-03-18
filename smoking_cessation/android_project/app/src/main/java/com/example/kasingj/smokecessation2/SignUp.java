package com.example.kasingj.smokecessation2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    /*The sign up page is used if the user needs to make an account. User provides username,password
    which is verified against the database and added, does not require. internet. but final fragment will
    so maybe stop them here if there is no internet.*/

    EditText USERNAME, PASSWORD, EMAIL, CON_PASS, CIGS_PER_DAY, PRICE_PER_PACK,NUM_YEARS_SMOKED;
    String username, password, email, con_pass, cigsPerDay, pricePerPack,numYearsSmoked ;
    Context ctx = this;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userService = new UserService(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void goToMain (View view) {

        USERNAME = (EditText) findViewById(R.id.signUpUserInput);
        PASSWORD = (EditText) findViewById(R.id.signUpPassInput);
        CON_PASS = (EditText) findViewById(R.id.signUpPassConfirm);
        EMAIL = (EditText) findViewById(R.id.signUpEmailInput);
        CIGS_PER_DAY = (EditText) findViewById(R.id.signUpCigsPerDay);
        PRICE_PER_PACK = (EditText) findViewById(R.id.signUpPricePerPack);
        NUM_YEARS_SMOKED = (EditText) findViewById(R.id.signUpNumYearsSmoked);
        username = USERNAME.getText().toString();
        password = PASSWORD.getText().toString();
        con_pass = CON_PASS.getText().toString();
        cigsPerDay = CIGS_PER_DAY.getText().toString();
        pricePerPack = PRICE_PER_PACK.getText().toString();
        numYearsSmoked = NUM_YEARS_SMOKED.getText().toString();
        email = EMAIL.getText().toString();


        //DatabaseOperations dbAuth = new DatabaseOperations(ctx);
        //Cursor cr = dbAuth.getUserAuth(dbAuth);
        //boolean exist = false;

        UserEntity user = userService.getUserIfAuthorized(username,password);

        //check if username exists

        if (user != null) {
            Toast.makeText(getBaseContext(), "Username already exists", Toast.LENGTH_LONG).show();
        }
        else if (username.equals("")) {
            Toast.makeText(getBaseContext(), "Must enter valid username", Toast.LENGTH_LONG).show();
        }
        else if (email.equals("")) {
            Toast.makeText(getBaseContext(), "Email required", Toast.LENGTH_LONG).show();
        }
        else if (password.equals("")) {
            Toast.makeText(getBaseContext(), "Password required", Toast.LENGTH_LONG).show();
        }
        else if (con_pass.equals("")) {
            PASSWORD.setText("");
            Toast.makeText(getBaseContext(), "Must confirm password", Toast.LENGTH_LONG).show();
        }else if (cigsPerDay.equals("")) {
            PASSWORD.setText("");
            Toast.makeText(getBaseContext(), "Must confirm cigs per day", Toast.LENGTH_LONG).show();
        } else if (pricePerPack.equals("")) {
            PASSWORD.setText("");
            Toast.makeText(getBaseContext(), "Must confirm price per pack", Toast.LENGTH_LONG).show();
        }else if (numYearsSmoked.equals("")) {
            PASSWORD.setText("");
            Toast.makeText(getBaseContext(), "Must confirm num years smoked", Toast.LENGTH_LONG).show();
        }
        else if (!password.equals(con_pass)) {
            PASSWORD.setText("");
            CON_PASS.setText("");
            Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_LONG).show();
        }
        else {
            //valid username & password
            UserEntity userEntity = new UserEntity();

            userEntity.setUsername(username);
            userEntity.setPassword(password);
            userEntity.setEmail(email);
            userEntity.setCigsPerDay(cigsPerDay);
            userEntity.setPricePerPack(pricePerPack);
            userEntity.setNumYearsSmoked(numYearsSmoked);
            userEntity.setLongestStreak(0);
            userEntity.setCurrentStreak(0);
            userEntity.setTotalDaysFree(0);
            userEntity.setNumCravings(0);
            userEntity.setCravsRes(0);
            userEntity.setNumCigsSmoked(0);
            userEntity.setMoneySaved(0);

            Log.d("Next Button", "initialized username, password, email");
            //save user entity
            int id = userService.saveUserEntity(userEntity);
            SharedPreferences preference = getSharedPreferences( getApplicationContext().getPackageName() , 1 );
            SharedPreferences.Editor editor = preference.edit();
            editor.putInt(MainActivity.CURRENT_USER_ID ,id);
            editor.commit();

            int result = preference.getInt(MainActivity.CURRENT_USER_ID,-1);

            //add user to db
            DatabaseOperations db = new DatabaseOperations(ctx);
            db.addUserAuth(db, username, password, email);

            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
