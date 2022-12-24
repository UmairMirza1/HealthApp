package com.smd.health;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.smd.health.data.DataStorage;
import com.smd.health.data.IMealDao;
import com.smd.health.data.Meal;

public class NewMealActivity extends AppCompatActivity {

    EditText mealName;
    EditText calories;
    Button save;

    IMealDao mealDao = DataStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Inflate();
    }

    public void Inflate() {

        setContentView(R.layout.activity_new_meal);
        mealName = (EditText) findViewById(R.id.MealName);
        calories = (EditText) findViewById(R.id.MealCalories);
        save = (Button) findViewById(R.id.save_meal);


    }

    public void save(View v) {
        mealDao.saveMeal(new Meal(mealName.getText().toString(), calories.getText().toString()));
        onBackPressed();
    }
}
