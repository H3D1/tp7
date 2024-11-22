package com.example.tp7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyDataBase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Teacher (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Course (id INTEGER PRIMARY KEY AUTOINCREMENT, courseName TEXT, hours FLOAT, type TEXT, idTeacher INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
        db.execSQL("DROP TABLE IF EXISTS Teacher");
        db.execSQL("DROP TABLE IF EXISTS Course");
        onCreate(db);
    }
}
