/*package com.gorunteams.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ResumeFragment extends Fragment {

    public ResumeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resume, container, false);

    }


}*/





package com.gorunteams.developer.gorunteamsandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class ResumeFragment extends Fragment {

    public ResumeFragment() {}
    String texto;
    String texto2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null){


            String texto = getArguments().getString("mail");
            texto2 = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_resume, container, false);
        TextView tv = (TextView) inf.findViewById(R.id.txtMAIL);
        tv.setText("aqui va"+texto2);
        return inf;
    }


    public void setText(String text){
        TextView textView = (TextView) getView().findViewById(R.id.txtMAIL);
        textView.setText(text);
    }




}

