package com.example.foodie.MealPlanner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.foodie.DatabaseHelper;
import com.example.foodie.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MealAdapter extends ArrayAdapter<MealInfo> {
    private Context context;
    private List<MealInfo>meals;
    DatabaseHelper myDB;

    public MealAdapter(Context context, List<MealInfo> meals) {
        super(context, R.layout.listview_component, meals);
        this.context=context;
        this.meals=meals;

    }

    public View getView(final int position, View convertView, final ViewGroup parent){
        final LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.listview_component, parent, false);

        TextView date = view.findViewById(R.id.date);
        TextView day = view.findViewById(R.id.day);
        TextView breakfast = view.findViewById(R.id.breakfast);
        TextView lunch = view.findViewById(R.id.lunch);
        TextView dinner = view.findViewById(R.id.dinner);
        date.setText(meals.get(position).getDate());
        day.setText(meals.get(position).getDay());
        breakfast.setText(meals.get(position).getBreakfast());
        lunch.setText(meals.get(position).getLunch());
        dinner.setText(meals.get(position).getDinner());

        final FloatingActionButton editButton=view.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,EditMeal.class);
                intent.putExtra("date",meals.get(position).getDate());
                intent.putExtra("day",meals.get(position).getDay());
                intent.putExtra("b",meals.get(position).getBreakfast());
                intent.putExtra("l",meals.get(position).getLunch());
                intent.putExtra("d",meals.get(position).getDinner());
                intent.putExtra("id",meals.get(position).getId());
                context.startActivity(intent);
            }
        });

        final FloatingActionButton Button=view.findViewById(R.id.delete);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper myDB = new DatabaseHelper(getContext());
                        if (myDB.deleteMeal(meals.get(position).getId())){
                            remove(meals.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(view.getContext(),"Deleted Successfully",Toast.LENGTH_LONG).show();
                        }

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Toast.makeText(view.getContext(),"Back to Meal Planner",Toast.LENGTH_LONG).show();
                    }

                });

                builder.create().show();

            }
        });

        return view;
    }






}

