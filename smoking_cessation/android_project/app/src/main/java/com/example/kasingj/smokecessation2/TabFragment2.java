package com.example.kasingj.smokecessation2;

/**
 * Created by kasingj on 2/20/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment2 extends Fragment {

    /* Tab fragment 2 of 3 part of account setup, goals, ideal quit date, motivations
     * features are implemented in main activity */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_2, container, false);
    }
}