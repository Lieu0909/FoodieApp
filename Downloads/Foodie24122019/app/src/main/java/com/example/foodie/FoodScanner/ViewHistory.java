package com.example.foodie.FoodScanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;

import java.util.ArrayList;

public class ViewHistory extends AppCompatActivity {

    DatabaseHelper mydb;
    private ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("SCANNER HISTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mydb = new DatabaseHelper(this);

        lv = findViewById(R.id.listviewscanhistory);
        ArrayList<String> array = new ArrayList<>();
        final Cursor data1 = mydb.viewAll();
        while (data1.moveToNext()) {
            array.add(data1.getString(0));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, array);
            lv.setAdapter(arrayAdapter);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String link =adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(),"link " + link, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http:// "  + link));
                startActivity(intent);

            }

        });

    }
}
