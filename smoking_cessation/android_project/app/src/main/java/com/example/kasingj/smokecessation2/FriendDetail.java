package com.example.kasingj.smokecessation2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FriendDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        String itemId = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KEY);
        //get friend data from db
        FriendService friendService = new FriendService(getApplicationContext());
        FriendEntity  entity = friendService.getFriendById(Integer.parseInt(itemId));
        TextView tv = (TextView) findViewById(R.id.detail);
        tv.setText("buddyId: " + entity.getID() +" buddy email "+ entity.getEmail() +" parentId: "+ entity.getParentId() +" money saved:"+ entity.getMoneySaved() ) ;

    }
}
