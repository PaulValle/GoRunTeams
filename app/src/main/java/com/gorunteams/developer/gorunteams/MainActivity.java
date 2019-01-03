package com.gorunteams.developer.gorunteams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gologin = (Button) findViewById(R.id.signin);
        gologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemintent = new Intent(MainActivity.this, login2.class);
                MainActivity.this.startActivity(itemintent);
            }
        });
    }




/*
    Button gosingin = (Button) findViewById(R.id.signin);
        gosingin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(view) {
            Intent itemintent = new Intent(MainActivity.this, login.class);
            MainActivity.this.startActivity(itemintent);
        }
    });
    */


}
