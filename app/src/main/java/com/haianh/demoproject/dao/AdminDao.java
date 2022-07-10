package com.haianh.demoproject.dao;

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
        return data;
    }
    public boolean Authentication(String user, String pass){
        SQLiteDatabase db = mydata.getReadableDatabase();
        String sql = "Select * from ADMIN where username =? and password=?";
        Cursor cs = db.rawQuery(sql, new String[]{user, pass});
        if(cs.getCount() <= 0){
            return false;
        }
        return true;
    }
}
