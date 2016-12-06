package com.example.kasingj.smokecessation2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FriendDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        String itemId = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KEY);
        //get friend data from db
        FriendService friendService = new FriendService(getApplicationContext());
        FriendEntity  friend = friendService.getFriendById(Integer.parseInt(itemId));

        UserService userService = new UserService(this);
        UserEntity user = userService.getCurrentUser(getApplicationContext());

        //row money saved
        TextView userMoneySaved = (TextView) findViewById(R.id.money_saved_user);
        TextView buddyMoneySaved = (TextView) findViewById(R.id.money_saved_buddy);
        userMoneySaved.setText( user.getMoneySaved() == null ? "-1" : user.getMoneySaved()+"" );
        buddyMoneySaved.setText( friend.getMoneySaved() == null ? "-1" : friend.getMoneySaved()+"" );

        TextView userLifeRegained = (TextView) findViewById(R.id.life_regained_user);
        TextView buddyLifeRegained= (TextView) findViewById(R.id.life_regained_buddy);
        userLifeRegained.setText( user.getLifeRegained() == null ? "-1" : user.getLifeRegained()+"" );
        buddyLifeRegained.setText( friend.getLifeRegained() == null ? "-1" : friend.getLifeRegained()+"" );

        TextView userCurrentStreak = (TextView) findViewById(R.id.current_streak_user);
        TextView buddyCurrentStreak = (TextView) findViewById(R.id.current_streak_buddy);
        userCurrentStreak.setText( user.getCurrentStreak() == null ? "-1" : user.getCurrentStreak()+"" );
        buddyCurrentStreak.setText( friend.getCurrentStreak() == null ? "-1" : friend.getCurrentStreak()+"" );

        TextView userLongestStreak = (TextView) findViewById(R.id.longest_streak_user);
        TextView buddyLongestStreak = (TextView) findViewById(R.id.longest_streak_buddy);
        userLongestStreak.setText( user.getLongestStreak() == null ? "-1" : user.getLongestStreak()+"" );
        buddyLongestStreak.setText( friend.getLongestStreak() == null ? "-1" : friend.getLongestStreak()+"" );




    }
}
