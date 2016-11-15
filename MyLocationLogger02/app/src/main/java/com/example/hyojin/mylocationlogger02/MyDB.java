package com.example.hyojin.mylocationlogger02;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public MyDB(Context context){
        super(context, "MyLocation", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE database (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL , longitude REAL , category INTEGER ,  whatdo TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 만약 member라는 테이블이 존재한다면 날려버려라.
        String sql = "DROP TABLE IF EXISTS member";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert (Double latitude, Double longitude, int category, String whatdo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO database VALUES(NULL, " + latitude + ", " + longitude + ", " + category + ", '" + whatdo + "');");
        Log.d ("SQL", "select : " + "(latitude:" + latitude + ")(longitude:" + longitude + ")(category:" + category + ")(whatdo:" + whatdo + ")");
        db.close();
    }

    public void getResult (ArrayList<LocationSet> LocationSet) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM database", null);

        while (cursor.moveToNext()) {
            Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            int category = cursor.getInt(cursor.getColumnIndex("category"));
            String whatdo = cursor.getString(cursor.getColumnIndex("whatdo"));

            LocationSet locationSet = new LocationSet();

            locationSet.latitude = latitude ;
            locationSet.longitude = longitude ;
            locationSet.category = category ;
            locationSet.whatdo = whatdo ;

            LocationSet.add(locationSet) ;
        }
    }
}