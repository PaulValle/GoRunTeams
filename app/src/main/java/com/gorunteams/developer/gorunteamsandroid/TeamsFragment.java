package com.gorunteams.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TeamsFragment extends Fragment {

    public TeamsFragment() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_teams, container, false);
        final TextView tv = (TextView) inf.findViewById(R.id.txtteam);
        //tv.setText("sdsd");

        Button btnNewTeam = (Button) inf.findViewById(R.id.btnTeams);
        btnNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("qqqqqqqqqqqqqqqqqqqqqq");

                View inf2 = inflater.inflate(R.layout.fragment_teams, container, false);
                return inf2;

            }
        });







        return inf;
    }


}