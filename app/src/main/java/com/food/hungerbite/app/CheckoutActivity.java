package com.food.hungerbite.app;

import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.badoualy.stepperindicator.StepperIndicator;

import mehdi.sakout.aboutpage.AboutPage;

public class CheckoutActivity extends AppCompatActivity   {
    int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
       ConstraintLayout activity_checkout = (ConstraintLayout)findViewById(R.id.activity_checkout);
        String[] mySteps = {"Order Confirmed", "In Kitchen", "Out for Delivery", "Delivered"};
        final StepperIndicator indicator = findViewById(R.id.stepper_indicator);
        if(i==0){
            for(i=0; i<2; i++){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        indicator.setCurrentStep(i);
                    }
                }, 5000);
            }
        }

        else if(i==2){
            for(i=2; i<4; i++){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        indicator.setCurrentStep(i);
                    }
                }, 5000);
            }
        }



        View aboutPage = new AboutPage(this)
                .setDescription("Thank You For Ordering with Hungerbite.com")

                .addGroup("Connect with us")
                .addEmail("hungerbiteldh@gmail.com")
                .addWebsite("http://www.hungerbite.com/")
                .addFacebook("hungerbite")
                .addYoutube("UCvvuEk5zz5VZLf422xOeipw")
                .addPlayStore("com.food.hungerbite.app")
                .addInstagram("hungerbite_")
                .create();
        activity_checkout.addView(aboutPage,1);
    }


}
