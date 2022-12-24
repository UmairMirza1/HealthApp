package com.smd.health.data;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataStorage implements IStepCountDao, IMealDao {
    private String userId;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;

    //data cache
    private long stepCount = -1;
    private final Set<IStepCountListener> stepListeners = new HashSet<>();

    private MealsChangeListener mealsChangeListener = null;
    private ArrayList<Meal> meals = new ArrayList<>();

    public DataStorage(String userId) {
        database.setPersistenceEnabled(true);
        this.userId = userId;
        databaseRef = database.getReference("healthData").child(userId);

        attachListeners();
    }



    private void attachListeners() {
        databaseRef.child("meals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meals.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Meal meal = data.getValue(Meal.class);
                    meals.add(meal);
                }
                if (mealsChangeListener != null) {
                    mealsChangeListener.mealsChanged(meals);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseRef.child("steps").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long stepVal = snapshot.getValue(Long.class);
                if (stepVal == null) {
                    stepCount = 0;
                }
                else {
                    stepCount = stepVal;
                }
                for (IStepCountListener listener : stepListeners) {
                    listener.onStepCountUpdated(stepCount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void saveStepCount(long stepCount) {
        databaseRef.child("steps").setValue(stepCount);
    }

    @Override
    public long getStepCount() {
        return stepCount;
    }

    @Override
    public void attachStepCountListener(IStepCountListener listener) {
        if (stepCount != -1) {
            listener.onStepCountUpdated(stepCount);
        }
        stepListeners.add(listener);
    }

    @Override
    public void removeStepCountListener(IStepCountListener listener) {
        stepListeners.remove(listener);
    }

    @Override
    public void saveMeal(Meal meal) {
        databaseRef.child("meals").push().setValue(meal);
    }

    @Override
    public ArrayList<Meal> loadMeals() {
        return meals;
    }

    @Override
    public void setMealsChangedListener(MealsChangeListener listener) {
        mealsChangeListener = listener;
    }


    private static DataStorage instance = null;
    public static DataStorage getInstance() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (instance == null) {
            instance = new DataStorage(auth.getUid());
        }
        return instance;
    }
}
