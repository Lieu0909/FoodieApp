package com.example.foodie.MealPlanner;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MealPlannerMain extends AppCompatActivity {
    private FloatingActionButton add;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planner_main);
        getSupportActionBar().setTitle("MEAL PLANNER");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add=findViewById(R.id.addBtn);
        lv=findViewById(R.id.listView);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAdd=new Intent(MealPlannerMain.this,AddMeal.class);
                startActivity(intentToAdd);
            }
        });
        DatabaseHelper db=new DatabaseHelper(this);
        List<MealInfo> meals=db.getAllMeal();
        final MealAdapter mealAdapter=new MealAdapter(this,meals);
        lv.setAdapter(mealAdapter);

    }}
