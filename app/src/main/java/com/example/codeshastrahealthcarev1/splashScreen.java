package com.example.codeshastrahealthcarev1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    public healthcare application;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        application = new healthcare();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!application.loggedIn)
                startActivity(new Intent(splashScreen.this,login.class));
                else
                    startActivity(new Intent(splashScreen.this,MainActivity.class));
            }
        },SPLASH_TIME_OUT);

    }
}
