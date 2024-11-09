package com.example.foodie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodie.GroceriesChecklist2.ItemList;
import com.example.foodie.MealPlanner.MealInfo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "foodie.db";

    //for Recipes
    public static final String tableName = "recipesTable";
    public static final String col_1 = "id";
    public static final String col_2 = "name";
    public static final String col_3 = "ingredient";
    public static final String col_4 = "instruction";
    public static final String col_5 = "dateTime";

    public static final String TABLE_NAME = "MealInfo";
    public static final String COL1 = "ID";
    public static final String COL2 = "DATE";
    public static final String COL3 = "DAY";
    public static final String COL4 = "BREAKFAST";
    public static final String COL5 = "LUNCH";
    public static final String COL6 = "DINNER";

    public static final String TABLE_NAME3 = "scanner";
    public static final String TABLE3_COL1 = "LINK";

    public static final String DB_TABLE = "groceryTable";
    public static final String DB_COLUMN = "groceryItem";


    //this constructor for creating the database
    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + tableName +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT,ingredient TEXT,instruction TEXT,dateTime Date)");
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "DATE TEXT," + "DAY TEXT," + "BREAKFAST TEXT," + "LUNCH TEXT," + "DINNER TEXT)";
        String createTable2 = "CREATE TABLE " + TABLE_NAME3 + " (LINK VARCHAR PRIMARY KEY )";//scanner
        String createTable3 = "CREATE TABLE " + DB_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "groceryItem TEXT)";
        sqLiteDatabase.execSQL(createTable);
        sqLiteDatabase.execSQL(createTable2);//scanner
        sqLiteDatabase.execSQL(createTable3);//checklist

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + databaseName);//drop table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);//scanner
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);//scanner
        onCreate(sqLiteDatabase); //and create new table
    }

    //for recipes
    //function for inserting on sqlite database
    public long insertData(String name, String ingredient, String instruction, String dateTime) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();//for accessing database data
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, name);
        contentValues.put(col_3, ingredient);
        contentValues.put(col_4, instruction);
        contentValues.put(col_5, dateTime);
        long id = sqLiteDatabase.insert(tableName, null, contentValues);
        return id;
    }

    public Cursor display() {
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();//for accessing database data
        Cursor cursor = sqliteDatabase.rawQuery("select * from " + tableName, null);
        return cursor;
    }

    //for recipes
    //for updating database data
    public boolean update(String name, String ingredient, String instruction, String dateTime, String id) {
        try {
            SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_1, id);
            contentValues.put(col_2, name);
            contentValues.put(col_3, ingredient);
            contentValues.put(col_4, instruction);
            contentValues.put(col_5, dateTime);
            sqliteDatabase.update(tableName, contentValues, col_1 + " =?", new String[]{id});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //for recipes
    //for deleting database data
    public boolean delete(String id) {
        SQLiteDatabase sqliteDatabase = this.getWritableDatabase();
        sqliteDatabase.delete(tableName, col_1 + " = ?", new String[]{id});
        return true;
    }

    //Meal Planner
    public boolean addMeal(String date, String day, String breakfast, String lunch, String dinner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, date);
        contentValues.put(COL3, day);
        contentValues.put(COL4, breakfast);
        contentValues.put(COL5, lunch);
        contentValues.put(COL6, dinner);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if result is -1 =false
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<MealInfo> getAllMeal() {

        try {
            List<MealInfo> mealInfoList = new ArrayList<MealInfo>();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                do {
                    MealInfo mealInfo = new MealInfo();
                    mealInfo.setId(cursor.getInt(0));
                    mealInfo.setDate(cursor.getString(1));
                    mealInfo.setDay(cursor.getString(2));
                    mealInfo.setBreakfast(cursor.getString(3));
                    mealInfo.setLunch(cursor.getString(4));
                    mealInfo.setDinner(cursor.getString(5));
                    mealInfoList.add(mealInfo);
                } while (cursor.moveToNext());
            }
            db.close();
            return mealInfoList;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteMeal(int id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int rows = db.delete(TABLE_NAME, COL1 + "=?", new String[]{String.valueOf(id)});
            db.close();
            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor getID(String meal) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + meal + "'", null);
        return data;
    }

    public boolean updateMeal(String date, String day, String breakfast, String lunch, String dinner, Integer id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, date);
            contentValues.put(COL3, day);
            contentValues.put(COL4, breakfast);
            contentValues.put(COL5, lunch);
            contentValues.put(COL6, dinner);
            db.update(TABLE_NAME, contentValues, COL1 + "=?", new String[]{String.valueOf(id)});
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //scanner
    public Cursor viewAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data1 = db.rawQuery("SELECT * FROM " + TABLE_NAME3, null);
        return data1;
    }

    //scanner
    public boolean save(String link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues a = new ContentValues();
        a.put(TABLE3_COL1, link);

        long result = db.insert(TABLE_NAME3, null, a);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public void insertList(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN, item);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN + " = ?", new String[]{item});
        db.close();
    }

    public ArrayList<String> getList() {
        ArrayList<String> grocerieslist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COLUMN}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DB_COLUMN);
            grocerieslist.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return grocerieslist;
    }


    /*
    public List<ItemList> getGroceriesList() {

        try {
            List<ItemList> itemList = new ArrayList<ItemList>();
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
            if (cursor.moveToFirst()) {
                do {
                    ItemList item = new ItemList();
                    item.setgroceryItem(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            db.close();
            return itemList;
        } catch (Exception e) {
            return null;
        }
    }*/
}
