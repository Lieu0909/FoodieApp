package com.example.foodie.MealPlanner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class EditMeal extends AppCompatActivity {
    DatabaseHelper myDB;
    private TextView etvDate,etvDay;
    private EditText eet1,eet2,eet3;
    private Button updateBtn;
    private CalendarView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EDIT MEAL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String day = intent.getStringExtra("day");
        String breakfast = intent.getStringExtra("b");
        String lunch = intent.getStringExtra("l");
        String dinner = intent.getStringExtra("d");
        final int id=intent.getIntExtra("id",-1);
        calendar=findViewById(R.id.calendarView);
        updateBtn=findViewById(R.id.update);
        etvDay=findViewById(R.id.Day);
        etvDate=findViewById(R.id.Date);
        eet1=findViewById(R.id.breakfast);
        eet2=findViewById(R.id.lunch);
        eet3=findViewById(R.id.dinner);
        myDB=new DatabaseHelper(this);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date1=dayOfMonth+"/"+(month+1)+"/"+year;
                etvDate.setText(date1);
                SimpleDateFormat dayFormat=new SimpleDateFormat("EEEE");
                Date d=new Date(year,month,dayOfMonth-1);
                String weekday=dayFormat.format(d);
                etvDay.setText(weekday);
            }
        });
        etvDate.setText(date);
        etvDay.setText(day);
        eet1.setText(breakfast);
        eet2.setText(lunch);
        eet3.setText(dinner);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etvDate.setText(etvDate.getText().toString());
                etvDay.setText(etvDay.getText().toString());
                eet1.setText(eet1.getText().toString());
                eet2.setText(eet2.getText().toString());
                eet3.setText(eet3.getText().toString());

                if(myDB.updateMeal(etvDate.getText().toString(),etvDay.getText().toString(),eet1.getText().toString(),eet2.getText().toString(),eet3.getText().toString(),id)==true){
                    Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditMeal.this, MealPlannerMain.class);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Fail");
                    builder.setCancelable(false);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        } });
                    builder.create().show();
                } }

        });

    }
}
