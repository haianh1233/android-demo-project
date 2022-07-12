package com.haianh.demoproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.haianh.demoproject.database.Database;
import com.haianh.demoproject.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    public static List<Item> getAll(Context context){
        Database helper = null;
        SQLiteDatabase db = null;
        Cursor cs = null;
        List<Item> items = new ArrayList<>();
        try{
            helper = new Database(context);
            db = helper.getReadableDatabase();
            cs = db.rawQuery("Select * from Item", null);
            while (cs.moveToNext()) {
                Item item = new Item();
                item.setId(cs.getInt(0));
                item.setName(cs.getString(1));
                item.setPrice(cs.getFloat(2));
                item.setDescription(cs.getString(3));
                items.add(item);

            }
        }finally {
            if(cs != null)
                cs.close();

            if(db != null)
                db.close();
        }

        return items;
    }

    public static boolean insert(Context context, String name, Float price, String description){
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Price", price);
        values.put("Description", description);
        long row = db.insert("Item", null,values);
        return (row > 0);
    }
    public static boolean Update(Context context, Item item){
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", item.getName());
        values.put("Price", item.getPrice());
        values.put("Description", item.getDescription());
        int row = db.update("Item", values, "Id=?", new String[]{item.getId() + ""});
        return (row > 0);
    }
    public static boolean delete(Context context, int id){
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        int row = db.delete("Item","Id=?", new String[]{id + ""});
        return (row > 0);
    }
}
