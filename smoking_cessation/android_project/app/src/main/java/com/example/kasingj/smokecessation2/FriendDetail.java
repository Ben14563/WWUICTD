package com.example.kasingj.smokecessation2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class FriendDetail extends AppCompatActivity {

    LineChart compareChart;

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

        // Setting up and loading graph
        compareChart = (LineChart) findViewById(R.id.compareChart);

        // no description text
        compareChart.getDescription().setEnabled(false);

        // enable touch gestures
        compareChart.setTouchEnabled(true);

        compareChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        compareChart.setDragEnabled(true);
        compareChart.setScaleEnabled(true);
        compareChart.setDrawGridBackground(false);
        compareChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        compareChart.setPinchZoom(true);

        // set an alternative background color
        compareChart.setBackgroundColor(Color.LTGRAY);

        // add data
        setData(5, 10);

        compareChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = compareChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
//        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        XAxis xAxis = compareChart.getXAxis();
//        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = compareChart.getAxisLeft();
//        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

    }

    private void setData(int count, float range) {
        ArrayList<Entry> friend1YVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult);
            friend1YVals.add(new Entry(i, val));
        }

        ArrayList<Entry> friend2YVals = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float mult = range / 2f;
            float val = (float) (Math.random() * mult);
            friend2YVals.add(new Entry(i, val));
        }

        LineDataSet set1, set2;

        if (compareChart.getData() != null &&
                compareChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) compareChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) compareChart.getData().getDataSetByIndex(1);
            set1.setValues(friend1YVals);
            set2.setValues(friend2YVals);
            compareChart.getData().notifyDataChanged();
            compareChart.notifyDataSetChanged();
        }
        else {
            // create a data set and give it a type
            set1 = new LineDataSet(friend1YVals, "You");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            set2 = new LineDataSet(friend2YVals, "Friend");
            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the datasets
            LineData data = new LineData(set1, set2);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            compareChart.setData(data);
        }
    }
}
