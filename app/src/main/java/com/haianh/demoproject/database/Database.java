package com.haianh.demoproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static String US = "";
    public static String PW = "";
    public Database(Context context) {
        super(context, "Demo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ADMIN(username Text primary key not null, password Text)";
        db.execSQL(sql);
        db.execSQL("INSERT INTO ADMIN VALUES('admin', 'admin')");

        // User
        sql = "CREATE TABLE USER(Username Text primary key not null, password Text)";
        db.execSQL(sql);
        db.execSQL("INSERT INTO USER VALUES('user', 'user')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
