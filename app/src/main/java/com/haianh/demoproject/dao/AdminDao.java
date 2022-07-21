package com.haianh.demoproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haianh.demoproject.database.Database;
import com.haianh.demoproject.model.Admin;

import java.util.ArrayList;

public class AdminDao {
    Database mydata;

    public AdminDao(Context context) {
        mydata = new Database(context);
    }
    public ArrayList<Admin> readAll(){
        ArrayList<Admin> data = new ArrayList<>();
        SQLiteDatabase db = mydata.getReadableDatabase();
        Cursor cs =db.rawQuery("Select * from ADMIN", null);
        cs.moveToFirst();
        while(!cs.isAfterLast()){
            String username = cs.getString(0);
            String pass = cs.getString(1);
            data.add(new Admin(username, pass));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return data;
    }
    public boolean Authentication(String user, String pass){
        SQLiteDatabase db = mydata.getReadableDatabase();
        String sql = "Select * from ADMIN where username =? and password=?";
        Cursor cs = db.rawQuery(sql, new String[]{user, pass});
        if(cs.getCount() <= 0){
            cs.close();
            db.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }

    public boolean isExist(String user){
        SQLiteDatabase db = mydata.getReadableDatabase();
        String sql = "Select * from ADMIN where username =?";
        Cursor cs = db.rawQuery(sql, new String[]{user});
        return cs.getCount() > 1 ? true : false;
    }

    public boolean createAccount(String username, String password) {
        SQLiteDatabase db = mydata.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long row = db.insert("ADMIN", null,values);

        return row > 0 ? true : false;
    }
}
