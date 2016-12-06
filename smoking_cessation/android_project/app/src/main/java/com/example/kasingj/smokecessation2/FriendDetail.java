package com.example.kasingj.smokecessation2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

        //buddy name
        TextView buddyName = (TextView) findViewById(R.id.buddyName);
        buddyName.setText( friend.getEmail() == null ? "buddy":friend.getEmail() );

        //row money saved
        TextView userMoneySaved = (TextView) findViewById(R.id.money_saved_user);
        TextView buddyMoneySaved = (TextView) findViewById(R.id.money_saved_buddy);
        userMoneySaved.setText( user.getMoneySaved() == null ? "-1" : "$"+user.getMoneySaved() );
        buddyMoneySaved.setText( friend.getMoneySaved() == null ? "-1" : "$"+friend.getMoneySaved() );

        TextView userLifeRegained = (TextView) findViewById(R.id.life_regained_user);
        TextView buddyLifeRegained= (TextView) findViewById(R.id.life_regained_buddy);
        userLifeRegained.setText( user.getLifeRegained() == null ? "-1" : user.getLifeRegained()+" min" );
        buddyLifeRegained.setText( friend.getLifeRegained() == null ? "-1" : friend.getLifeRegained()+" min" );

        TextView userCurrentStreak = (TextView) findViewById(R.id.current_streak_user);
        TextView buddyCurrentStreak = (TextView) findViewById(R.id.current_streak_buddy);
        userCurrentStreak.setText( user.getCurrentStreak() == null ? "-1" : user.getCurrentStreak()+" days" );
        buddyCurrentStreak.setText( friend.getCurrentStreak() == null ? "-1" : friend.getCurrentStreak()+" days" );

        TextView userLongestStreak = (TextView) findViewById(R.id.longest_streak_user);
        TextView buddyLongestStreak = (TextView) findViewById(R.id.longest_streak_buddy);
        userLongestStreak.setText( user.getLongestStreak() == null ? "-1" : user.getLongestStreak()+" days" );
        buddyLongestStreak.setText(friend.getLongestStreak() == null ? "-1" : friend.getLongestStreak() + " days");

        ImageView userMoneySavedImage = (ImageView) findViewById(R.id.money_saved_img_user);
        ImageView buddyMoneySavedImage = (ImageView) findViewById(R.id.money_saved_img_buddy);
//        userMoneySavedImage.setImageResource(R.drawable.checkmark);
//
//        ImageView userLifeRegainedImage = (ImageView) findViewById(R.id.life_regained_img_user);
//        //ImageView buddyLifeRegainedImg = (ImageView) findViewById(R.id.life_regained_img_buddy);
//        userLifeRegainedImage.setBackgroundResource(R.drawable.checkmark);

        /* compare values and set accordingly*/
        //money
        ImageView win = ( Double.parseDouble(user.getMoneySaved()) > Double.parseDouble(friend.getMoneySaved()))
                ? userMoneySavedImage : buddyMoneySavedImage;
        win.setImageResource(R.drawable.checkmark);


        //life regained
        ImageView userLifeRegainedImage = (ImageView) findViewById(R.id.life_regained_img_user);
        ImageView buddyLifeRegainedImage = (ImageView) findViewById(R.id.life_regained_img_buddy);

        ImageView win2 = ( Integer.parseInt(user.getLifeRegained()) > Integer.parseInt(friend.getLifeRegained()))
                ? userLifeRegainedImage : buddyLifeRegainedImage;
        win2.setImageResource(R.drawable.checkmark);


        //life regained
        ImageView userCurrentStreakImage = (ImageView) findViewById(R.id.current_streak_img_user);
        ImageView buddyCurrentStreakImage = (ImageView) findViewById(R.id.current_streak_img_buddy);

        ImageView win3 = ( Integer.parseInt(user.getCurrentStreak()) >= Integer.parseInt(friend.getCurrentStreak()))
                ? userCurrentStreakImage : buddyCurrentStreakImage;
        win3.setImageResource(R.drawable.checkmark);

        //longest streak
        //life regained
        ImageView userLongestStreakImage = (ImageView) findViewById(R.id.longest_streak_img_user);
        ImageView buddyLongestStreakImage = (ImageView) findViewById(R.id.longest_streak_img_buddy);

        ImageView win4 = ( Integer.parseInt(user.getLongestStreak()) >= Integer.parseInt(friend.getLongestStreak()))
                ? userLongestStreakImage : buddyLongestStreakImage;
        win4.setImageResource(R.drawable.checkmark);



    }
}
