package com.example.foodie.Recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;

import java.util.Date;

public class AddRecipes extends AppCompatActivity {
    EditText nameEt,ingredientEt,instructionEt;
    Button cancelBt,saveBt,shareBt;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("RECIPES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);

        nameEt = findViewById(R.id.nameEditTextIdUpdate);
        ingredientEt = findViewById(R.id.ingredientEditTextIdUpdate);
        instructionEt = findViewById(R.id.instructionsEditTextIdUpdate);

        cancelBt = findViewById(R.id.cacelButtonIdUpdate);
        saveBt = findViewById(R.id.btnAdd);
        shareBt = findViewById(R.id.shareButtonIdUpdate);

        shareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //passing data via intent
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String sub = nameEt.getText().toString();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String des = (ingredientEt.getText().toString()+instructionEt.getText().toString());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,des);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                backToMain();
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();
            }
        });
    }

    //for inserting new data
    public void insertData(){
        long l = -1;

        Date date = new Date();
        String d = (String) android.text.format.DateFormat.format("dd/MM/yyyy  hh:mm:ss",date);

        if(nameEt.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"You didn't add any subject",Toast.LENGTH_SHORT).show();
        }
        else{
            l = mydb.insertData(nameEt.getText().toString(), ingredientEt.getText().toString(), instructionEt.getText().toString(), d);
        }

        if(l>=0){
            Toast.makeText(getApplicationContext(),"Data added",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Data not added", Toast.LENGTH_SHORT).show();
        }
    }
    public void backToMain()
    {
        Intent intent = new Intent(AddRecipes.this, RecipesMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
