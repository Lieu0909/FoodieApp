package com.example.foodie;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodie.FoodScanner.FoodScannerMain;
import com.example.foodie.GroceriesCheckList.CheckListMain;
import com.example.foodie.GroceriesChecklist2.CheckList;
import com.example.foodie.GroceriesNearby.MapsActivity;
import com.example.foodie.MealPlanner.MealPlannerMain;
import com.example.foodie.Recipes.RecipesMain;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity{
    private Button btnCheckList,btnMealPlanner,btnScanner,btnRecipes,btnGroceries;
    private SeekBar sk;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("HOME");

        btnCheckList = findViewById(R.id.btnCheckList);
        btnMealPlanner = findViewById(R.id.btnMealPlanner);
        btnScanner = findViewById(R.id.btnScanner);
        btnRecipes = findViewById(R.id.btnRecipes);
        btnGroceries = findViewById(R.id.btnGroceries);
        sk = findViewById(R.id.seekBar);
        tv = findViewById(R.id.tv);

        btnCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goChecklist = new Intent(HomeActivity.this, CheckList.class);
                startActivity(goChecklist);
            }
        });

        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMealPlanner = new Intent(HomeActivity.this, MealPlannerMain.class);
                startActivity(goMealPlanner);
            }
        });

        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goScanner = new Intent(HomeActivity.this, FoodScannerMain.class);
                startActivity(goScanner);
            }
        });

        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goRecipes = new Intent(HomeActivity.this, RecipesMain.class);
                startActivity(goRecipes);
            }
        });

        btnGroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goGroceries = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(goGroceries);
            }
        });

        //Getting Current screen brightness.
        int Brightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,0);
        //Setting up current screen brightness to seekbar;
        sk.setProgress(Brightness);
        //Setting up current screen brightness to TextView;
        tv.setText("Screen Brightness : " + Brightness);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Context context = getApplicationContext();
                boolean canWrite = Settings.System.canWrite(context);

                if (canWrite) {
                    // Because max screen brightness value is 255
                    // But max seekbar value is 100, so need to convert.
                    int screenBrightnessValue = progress * 255 / 255;

                    // Set seekbar adjust screen brightness value in the text view.
                    tv.setText("Screen Brightness = " + screenBrightnessValue);

                    // Change the screen brightness change mode to manual.
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    // Apply the screen brightness value to the system, this will change the value in Settings ---> Display ---> Brightness level.
                    // It will also change the screen brightness for the device.
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue);
                } else {
                    // Show Can modify system settings panel to let user add WRITE_SETTINGS permission for this app.
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);

         if((item.getItemId())== R.id.logout_button){
             user_logout();
         }
         return true;
    }

    private void user_logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}
