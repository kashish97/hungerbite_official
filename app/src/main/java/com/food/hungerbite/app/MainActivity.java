package com.food.hungerbite.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


     String APP_UPDATE_SERVER_URL = "https://play.google.com/store/apps/details?id=com.food.hungerbite.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // UpdateChecker.checkForDialog(MainActivity.this, APP_UPDATE_SERVER_URL, true, true);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent startActivity = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(startActivity);
                finish();
            }
        }, 5000);


    }



}

