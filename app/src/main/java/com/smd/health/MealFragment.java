package com.smd.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smd.health.data.DataStorage;
import com.smd.health.data.IMealDao;
import com.smd.health.data.Meal;

import java.util.ArrayList;


public class MealFragment extends Fragment {
    private IMealDao mealDao = DataStorage.getInstance();
    private ArrayList<Meal> Dataset = mealDao.loadMeals();
    private MealAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.meal_fragment_layout, container, false);

        // Button for launching new activity
        Button newMeal = view.findViewById(R.id.btn_newmeal);
        newMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewMealActivity.class);
                startActivity(intent);
            }
        });

        // setting recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //setting adapter
        MealAdapter adp = new MealAdapter(Dataset);
        mAdapter = adp;
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        mealDao.setMealsChangedListener(meals -> {
            Dataset = meals;
            adp.notifyDataSetChanged();
        });

        return view;


    }

}
