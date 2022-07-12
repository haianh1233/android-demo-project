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

        sql = "Create table Item(Id integer primary key autoincrement," +
                "Name text, Price REAL, Description text)";
        db.execSQL(sql);
        sql = "Insert Into Item Values(null, 'CocaCola', '50000', 'Thuc uong co ga')";
        db.execSQL(sql);
        sql = "Insert Into Item Values(null, 'Pepsi', '55000', 'Thuc uong co ga')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists Item");
        db.execSQL("Drop table if exists User");
        db.execSQL("Drop table if exists Admin");

        onCreate(db);
    }
}
