package com.example.kasingj.smokecessation2;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Stats extends AppCompatActivity {

    BarChart barChart;
    Random random;
    ArrayList<String> dates;
    ArrayList<BarEntry> entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        barChart = (BarChart) findViewById(R.id.bargraph);

        createRandomGraph("2016/11/01", "2016/11/18");

//        ArrayList<String> months = new ArrayList<>();
//        months.add("January");
//        months.add("February");
//        months.add("March");
//        months.add("April");
//        months.add("May");
//        months.add("June");
//        months.add("July");
//        months.add("August");
//        months.add("September");
//        months.add("October");
//        months.add("November");
//        months.add("December");

        // https://www.youtube.com/watch?v=H6QxMBI2QH4#t=263.901119
    }

    public void createRandomGraph (String startDate, String endDate) {

        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date start = date.parse(startDate);
            Date end = date.parse(endDate);

            Calendar mStart = Calendar.getInstance();
            Calendar mEnd = Calendar.getInstance();
            mStart.clear();
            mEnd.clear();

            mStart.setTime(start);
            mEnd.setTime(end);

            dates = new ArrayList<>();
            dates = getList(mStart, mEnd);

            entries = new ArrayList<>();
            float max = 0f;
            float value = 0f;
            random = new Random();
            for (int i = 0; i < dates.size(); i++) {
                max = 100f;
                value = random.nextFloat() * max;
                entries.add(new BarEntry(value, i));
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }
        BarDataSet dataSet = new BarDataSet(entries, "Cigarettes Smoked");
        BarData data = new BarData(dataSet);
        barChart.setData(data);

        Description description = new Description();
        description.setText("Testing Bar Chart");
        barChart.setDescription(description);
    }

    public ArrayList<String> getList (Calendar startDate, Calendar endDate) {
        ArrayList<String> list = new ArrayList<>();
        while (startDate.compareTo(endDate) <= 0) {
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public String getDate (Calendar day) {
        String currentDate = day.get(Calendar.YEAR) + "/" + (day.get(Calendar.MONTH) + 1) +
                "/" + day.get(Calendar.DAY_OF_MONTH);

        try {
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(currentDate);
            currentDate = new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentDate;
    }
}





























