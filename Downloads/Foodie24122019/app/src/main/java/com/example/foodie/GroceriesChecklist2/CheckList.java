package com.example.foodie.GroceriesChecklist2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CheckList extends AppCompatActivity {
    DatabaseHelper db;
    ArrayAdapter<String> adapter;
    ArrayList<String> grocerieslist;
    private ListView lv;
    private Button del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        getSupportActionBar().setTitle("GROCERIES CHECKLIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        db = new DatabaseHelper(this);

        lv = (ListView) findViewById(R.id.list);
        //del = findViewById(R.id.btnDelete);

        //List<ItemList> grocerylist=db.getGroceriesList();

        //loadTaskList();

        //grocerieslist = new ArrayList<>();
        grocerieslist = getArralVal(getApplicationContext());
        Collections.sort(grocerieslist);
        //adapter = new ArrayAdapter<>(this,R.layout.row,R.id.task_title,grocerieslist);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,grocerieslist);
        lv=findViewById(R.id.list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View view, final int position, long id) {
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItem.trim().equals(grocerieslist.get(position).trim())){
                    removeElement(selectedItem,position);
                }else {
                    Toast.makeText(getApplicationContext(),"Error deleting item!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

/*     private void loadTaskList() {
        ArrayList<String> taskList = db.getList();
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.task_title, taskList);
            lv.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(taskList);
            adapter.notifyDataSetChanged();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listmenu, menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Item");
            final EditText input = new EditText(this);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    grocerieslist.add(input.getText().toString());
                    storeArray(grocerieslist,getApplicationContext());
                    lv.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(),"Save Successfully!",Toast.LENGTH_LONG).show();

                }

            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void storeArray (ArrayList inArrayList, Context context){
        Set input = new HashSet(inArrayList);
        SharedPreferences wordput= context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = wordput.edit();
        editor.putStringSet("myArray", input);
        editor.commit();
    }

    public static ArrayList getArralVal(Context dan){
        SharedPreferences wordget = dan.getSharedPreferences("dbArrayValues",Activity.MODE_PRIVATE);
        Set tempSet = new HashSet();
        tempSet = wordget.getStringSet("myArray", tempSet);
        return new ArrayList<>(tempSet);
    }
    public void removeElement(String selectedItem,final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete "+selectedItem+ "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                grocerieslist.remove(position);
                Collections.sort(grocerieslist);
                storeArray(grocerieslist, getApplicationContext());
                lv.setAdapter(adapter);
                Toast.makeText(getApplicationContext(),"Deleted Successfully!",Toast.LENGTH_LONG).show();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Item")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String item = String.valueOf(taskEditText.getText());
                                db.insertList(item);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        Log.e("String", (String) taskTextView.getText());
        String task = String.valueOf(taskTextView.getText());
        db.deleteTask(task);
        loadTaskList();
    }

     */


}
