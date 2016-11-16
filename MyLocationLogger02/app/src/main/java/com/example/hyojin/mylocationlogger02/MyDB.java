package com.example.hyojin.mylocationlogger02;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/** 데이터베이스를 다루는 클래스 **/
public class MyDB extends SQLiteOpenHelper {

    /** 클래스 선언 시 생성자 **/
    public MyDB(Context context){
        super(context, "MyLocation", null, 1);
    }

    /** 데이터베이스 테이블 만들기 **/
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE database (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude REAL , longitude REAL , category INTEGER ,  whatdo TEXT);");
    }

    /** 데이터베이스를 새로 생성 **/
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS database");
        onCreate(db);
    }

    /** 데이터베이스에 정보 추가 **/
    public void insert (Double latitude, Double longitude, int category, String whatdo) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO database VALUES(NULL, " + latitude + ", " + longitude + ", " + category + ", '" + whatdo + "');");
        db.close();
    }

    /** 데이터베이스에 저장된 정보를 ArrayList로 반환 **/
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
        cursor.close();
        db.close();
    }
}