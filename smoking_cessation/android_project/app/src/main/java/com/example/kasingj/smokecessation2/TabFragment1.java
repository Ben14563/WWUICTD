package com.example.kasingj.smokecessation2;

/**
 * Created by kasingj on 2/20/16.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class TabFragment1 extends Fragment {
    /* Tab fragment 1 of 3 part of account setup, gets data on habits
     * features are implemented in main activity */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }
}