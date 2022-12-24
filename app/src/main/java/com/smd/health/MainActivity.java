package com.smd.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements StepCountFragment.StepListener {

    private  MealFragment mealFragment;
     private StepCountFragment stepFragment;
    private FrameLayout paneOne;
    private FrameLayout paneTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paneOne= (FrameLayout) findViewById(R.id.paneOne);
        paneTwo= (FrameLayout) findViewById(R.id.paneTwo);
        startService();
        createfragments();
        ///

    }

    private void createfragments() {

        mealFragment = new MealFragment();
        stepFragment = new StepCountFragment();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(paneTwo.getId(), mealFragment);
        transaction.replace(paneOne.getId(), stepFragment);
        transaction.commit();

    }


    private void startService() {
        Intent serviceIntent = new Intent(this, StepCounterService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    @Override
    public void update() {

        // do your listener sht here
    }
}