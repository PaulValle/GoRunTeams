package com.gorunteams.developer.gorunteams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        TextView goregister = (TextView) findViewById(R.id.register);
        goregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemintent = new Intent(login2.this, MainActivity.class);
                login2.this.startActivity(itemintent);
            }
        });

        Button goresume = (Button) findViewById(R.id.login);
        goresume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemintent = new Intent(login2.this, resume.class);
                login2.this.startActivity(itemintent);
            }
        });

    }
}
