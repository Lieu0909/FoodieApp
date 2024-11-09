package com.example.foodie.FoodScanner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;

public class FoodScannerMain extends AppCompatActivity {
    DatabaseHelper mydb;

    public static TextView resultTextView;
    private ListView lv;
    private Button save_btn;
    private String[] array = {"Scan QR or Barcode Here","View History"};

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_food_scanner_main);
            ActionBar actionBar =getSupportActionBar();
            actionBar.setTitle("FOOD SCANNER");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mydb = new DatabaseHelper(this);

            lv = findViewById(R.id.listviewscanner);
            resultTextView = findViewById(R.id.result_text);
            save_btn = findViewById(R.id.btn_save);

            ArrayAdapter<String> arrayList = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,array);
            lv.setAdapter(arrayList);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i == 0){
                        Intent intent = new Intent(FoodScannerMain.this,ScanCode.class);
                        startActivity(intent);
                    }else if (i == 1){
                        Intent intent = new Intent(FoodScannerMain.this,ViewHistory.class);
                        startActivity(intent);
                    }
                }
            });


            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean check = resultTextView.getText().toString().matches("") ;

                    if(!check)
                    {
                        String link = resultTextView.getText().toString();

                        boolean isOk = mydb.save(link);

                        if (isOk)
                            Toast.makeText(getApplicationContext(),"Save Successfully!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(),"Something went wrong!",Toast.LENGTH_LONG).show();




                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Please scan the QR or barcode!",Toast.LENGTH_LONG).show();
                    }

                }
            });


        }

}
