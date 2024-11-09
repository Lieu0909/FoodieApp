package com.example.foodie.Recipes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class UpdateRecipesActivity extends AppCompatActivity {
    EditText nameEt,ingredientEt,instructionEt;
    Button cancelBt,updateBt,shareBtOnUpdate;
    DatabaseHelper dbUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipes);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("RECIPES");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //passing Update Activity's context to database alass
        dbUpdate = new DatabaseHelper(this);
        SQLiteDatabase sqliteDatabase = dbUpdate.getWritableDatabase();

        nameEt = findViewById(R.id.nameEditTextIdUpdate);
        ingredientEt = findViewById(R.id.ingredientEditTextIdUpdate);
        instructionEt = findViewById(R.id.instructionsEditTextIdUpdate);

        cancelBt = findViewById(R.id.cacelButtonIdUpdate);
        updateBt = findViewById(R.id.btnAdd);
        shareBtOnUpdate = findViewById(R.id.shareButtonIdUpdate);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String sub = intent.getStringExtra("name");
        String ing = intent.getStringExtra("ingredient");
        String ins = intent.getStringExtra("instruction");
        final String id = intent.getStringExtra("listId");

        nameEt.setText(sub);
        ingredientEt.setText(ing);
        instructionEt.setText(ins);

        //for sharing data to social media
        shareBtOnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String sub = nameEt.getText().toString();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,sub);
                String des = (ingredientEt.getText().toString()+instructionEt.getText().toString());
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,des);
                //String ins = instructionEt.getText().toString();
                //shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,ins);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //for updating database data
        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                String d = (String) android.text.format.DateFormat.format("dd/MM/yyyy  hh:mm:ss",date);

                if(dbUpdate.update(nameEt.getText().toString(),ingredientEt.getText().toString(),instructionEt.getText().toString(),d,id)==true){
                    Toast.makeText(getApplicationContext(),"Data updated",Toast.LENGTH_SHORT).show();
                    backToMain();
                }
            }
        });
    }

    //this method to clearing top activity and starting new activity
    public void backToMain()
    {
        Intent intent = new Intent(UpdateRecipesActivity.this, RecipesMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }
}
