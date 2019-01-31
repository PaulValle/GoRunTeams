package com.gorunteams.developer.gorunteamsandroid;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
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
                //tv.setText("EventoClick");

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

// define your view here that found in popup_layout
// for example let consider you have a button

                Button btn = (Button) popupView.findViewById(R.id.btnTeams);

// finally show up your popwindow
                popupWindow.showAsDropDown(popupView, 0, 0);
               // popupWindow.update(50,50,30,80);
                //showPopup(this);
               /* View inf2 = inflater.inflate(R.layout.fragment_teams, container, false);
                return inf2;
                */
            }
        });

        return inf;
    }

/*
    public void showPopup(View anchorView) {

        View popupView = getLayoutInflater().inflate(R.layout.popup_equipo, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView tv = (TextView) popupView.findViewById(R.id.txtnomequipo);

        tv.setText("oliii");

        // Initialize more widgets from `popup_layout.xml`


        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
                location[0], location[1] + anchorView.getHeight());

    }
    */

}