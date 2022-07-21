package com.haianh.demoproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static String US = "";
    public static String PW = "";
    public static final String FILE_NAME = "prm_assignment.sql";
    public Database(Context context) {
        super(context, FILE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ADMIN(username Text primary key not null, password Text)";
        db.execSQL(sql);
        db.execSQL("INSERT INTO ADMIN VALUES('admin', 'admin')");

        sql = "Create table Item(Id integer primary key autoincrement," +
                "Name text, Price REAL, Description text)";
        db.execSQL(sql);
        sql = "Insert Into Item Values(null, 'CocaCola', '50000', 'Thuc uong co ga')";
        db.execSQL(sql);
        sql = "Insert Into Item Values(null, 'Pepsi', '55000', 'Thuc uong co ga')";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS PRODUCT(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Name VARCHAR(100) NOT NULL," +
                "Price REAL NOT NULL," +
                "Quantity INTEGER NOT NULL)";

        db.execSQL(sql);
        db.execSQL("INSERT INTO PRODUCT(Name, Price, Quantity) " +
                "VALUES " +
                "('Coca', 10000.0, 100)," +
                "('Pepsi', 10000.0, 100)," +
                "('7up', 10000.0, 100)," +
                "('Coffee Can', 10000.0, 100)," +
                "('Mountain Dew', 10000.0, 100);" +
                "");

        sql = "CREATE TABLE IF NOT EXISTS CART_DETAIL(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "AccountId TEXT NOT NULL," +
                "ProductId INTEFER NOT NULL," +
                "Quantity INTEGER NOT NULL)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
