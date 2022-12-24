package com.smd.health;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smd.health.data.Meal;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder>{

    private ArrayList<Meal> list;

    public MealAdapter  (ArrayList<Meal> ds)  {
        list = ds;
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView calories;


        public MealViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.Meal_item_Name);
            calories = itemView.findViewById(R.id.Meal_item_calories);


        }
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_item, parent, false);
        MealViewHolder vh = new MealViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {

        String name = list.get(position).getName();
        String calories= list.get(position).getCalories();

        int eol = name.indexOf("\n");
        holder.name.setText(name.substring(0, eol > 0 ? eol : name.length()));

        int eol2 = calories.indexOf("\n");
        holder.calories.setText(calories.substring(0, eol2 > 0 ? eol2 : calories.length()));

        holder.itemView.setTag(position);

    }





    @Override
    public int getItemCount() {
        return list.size();
    }
}
