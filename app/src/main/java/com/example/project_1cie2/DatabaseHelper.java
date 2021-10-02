package com.example.project_1cie2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "college";
    public static final String TABLE = "student";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";

    private static final String KEY_ID = "id";
    private static final String TABLE_FUEL = "fuel";
    private static final String KEY_DATE = "date";
    private static final String KEY_VOLUME = "volume";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ODOMETER = "odometer";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE, null, 1);
        SQLiteDatabase db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT)";
        Log.i("sql", sql);
        sqLiteDatabase.execSQL(sql);

        /* sqLiteDatabase.execSQL("create table " + TABLE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, password);

        // insert into table_name (name,city,branch) values ('abc','rjt','ce')
        long result = db.insert(TABLE, null, values);
        return (result == -1) ? false : true;
    }

    public Cursor selectData() {
        SQLiteDatabase db = getWritableDatabase();

        //Select * form Table
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
        return cursor;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public boolean addFuel(Fuel fuel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE, fuel.getDate());
        values.put(KEY_VOLUME, fuel.getVolume());
        values.put(KEY_PRICE, fuel.getPrice());
        values.put(KEY_ODOMETER, fuel.getOdoMeter());

        // Inserting Row
        db.insert(TABLE_FUEL, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return true;
    }

    // code to get the single contact
    Fuel getFuel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Fuel fuel = null;
        Cursor cursor = db.query(TABLE_FUEL, new String[]{KEY_ID,
                        KEY_DATE, KEY_VOLUME, KEY_PRICE, KEY_ODOMETER}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            fuel = new Fuel(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Float.parseFloat(cursor.getString(2)),
                    Float.parseFloat(cursor.getString(3)),
                    Float.parseFloat(cursor.getString(4)));
            cursor.close();
        }
        return fuel;
    }

    public Fuel getLastFuelRecord() {
        String selectQuery = "SELECT * FROM " + TABLE_FUEL + " ORDER BY " + KEY_ID + " DESC LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Fuel fuel = null;
        if (cursor != null) {
            cursor.moveToFirst();
            fuel = new Fuel(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    Float.parseFloat(cursor.getString(2)),
                    Float.parseFloat(cursor.getString(3)),
                    Float.parseFloat(cursor.getString(4)));
            cursor.close();
        }
        return fuel;
    }

    public List<Fuel> getFuelList() {
        List<Fuel> fuelList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FUEL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Fuel fuel = new Fuel(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),
                        Float.parseFloat(cursor.getString(4)));
                fuelList.add(fuel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return fuelList;
    }
}


