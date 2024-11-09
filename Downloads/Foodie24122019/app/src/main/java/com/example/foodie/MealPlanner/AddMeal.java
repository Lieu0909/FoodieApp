package com.example.foodie.MealPlanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMeal extends AppCompatActivity {

    DatabaseHelper myDB;
    private CalendarView calender;
    private Button save;
    private TextView tvDay,tvDate;
    private EditText et1,et2,et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADD MEAL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calender=findViewById(R.id.calendarView);
        save=findViewById(R.id.update);
        tvDay=findViewById(R.id.Day);
        tvDate=findViewById(R.id.Date);
        et1=findViewById(R.id.breakfast);
        et2=findViewById(R.id.lunch);
        et3=findViewById(R.id.dinner);
        myDB=new DatabaseHelper(this);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //get Date
                String date=dayOfMonth +"/"+(month + 1) + "/" +year;
                tvDate.setText(date);

                //get Day
                String day;
                SimpleDateFormat dayFormat=new SimpleDateFormat("EEEE");
                Date d=new Date(year,month,dayOfMonth-1);
                day=dayFormat.format(d);
                tvDay.setText(day);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date =tvDate.getText().toString();
                String day=tvDay.getText().toString();
                String breakfast=et1.getText().toString();
                String lunch=et2.getText().toString();
                String dinner=et3.getText().toString();

                if(date.length()!=0 && day.length() !=0 && breakfast.length() !=0 && lunch.length()!=0 && dinner.length()!=0){
                    Add(date,day,breakfast,lunch,dinner);
                }else {
                    Toast.makeText(getApplicationContext(),"Please fill the empty!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void Add(String date,String day,String breakfast,String lunch,String dinner){
        boolean insertMeal=myDB.addMeal(date, day, breakfast, lunch, dinner);
        if(insertMeal==true){
            Toast.makeText(getApplicationContext(),"Save Succesfully!",Toast.LENGTH_LONG).show();
            Intent returnToPlanner=new Intent(AddMeal.this,MealPlannerMain.class);
            startActivity(returnToPlanner);
        }else {
            Toast.makeText(getApplicationContext(),"Failed Inserted!",Toast.LENGTH_LONG).show();
        }

    }







}

