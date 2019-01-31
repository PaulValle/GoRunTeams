package com.gorunteams.developer.gorunteamsandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        Button btnNewTeam = (Button) inf.findViewById(R.id.btnTeams);
        btnNewTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv.setText("EventoClick");

                View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_equipo, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                Button btn = (Button) popupView.findViewById(R.id.btncerrar);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                    });



                popupWindow.showAsDropDown(popupView, 0, 0);

            }
        });

        return inf;
    }

}