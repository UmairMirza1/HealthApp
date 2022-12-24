package com.smd.health;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smd.health.data.Meal;

public class StepCountFragment extends Fragment {

    ProgressBar bar;
    TextView goal;
    TextView count;
    AppCompatButton button;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step_count_fragment, container, false);

        bar=(ProgressBar) view.findViewById(R.id.progress_bar);

        //This is the call to set the progress of the progress bar through the listener
        // pass the daily step here or whatever

        bar.setProgress(90);
        button= view.findViewById(R.id.setstepcount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setstepgoal();
            }
        });
        count = view.findViewById(R.id.stepcount);
        goal = view.findViewById(R.id.goal);


        return view;


    }

    public interface StepListener
    {
        void update();
    }

    public void setstepgoal(){

        Intent intent = new Intent(getActivity(), NewMealActivity.class);
        startActivity(intent);

    }




}
