package com.smd.health;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.smd.health.data.DataStorage;
import com.smd.health.data.IMealDao;
import com.smd.health.data.Meal;

public class SetStepActivity extends AppCompatActivity {
    EditText Stepcount;

    Button save;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Inflate();
    }

    public void Inflate() {

        setContentView(R.layout.setstepcount);
        Stepcount = (EditText) findViewById(R.id.stepgoal);

        save = (Button) findViewById(R.id.save_stepgoal_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savestep();
            }
        });

    }

    public void savestep() {

        // save your step here
    }





}
