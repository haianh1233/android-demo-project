package com.haianh.demoproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.haianh.demoproject.database.Database;
import com.haianh.demoproject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    Database databaseHelper;

    public ProductDao(@NonNull Database databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public ProductDao(@NonNull Context context) {
        databaseHelper = new Database(context);
    }


    public List<Product> getAllProduct() {
        SQLiteDatabase db = null;
        Cursor cs = null;
        List<Product> list = null;
        try {
            db = databaseHelper.getReadableDatabase();
            String sql = "SELECT Id, Name, Price, Quantity " +
                    "FROM PRODUCT ";
            cs = db.rawQuery(sql, null);

            cs.moveToFirst();
            while (!cs.isAfterLast()) {
                int id = cs.getInt(0);
                String name = cs.getString(1);
                double price = cs.getDouble(2);
                int quantity = cs.getInt(3);

                if (list == null) {
                    list = new ArrayList<>();
                }

                if (quantity > 0) {
                    list.add(new Product(id, name, price, quantity));
                }
                cs.moveToNext();
            }
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }

        return list;
    }

    public boolean updateProduct(Product product) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        try {
            db = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", product.getName());
            contentValues.put("Price", product.getPrice());
            contentValues.put("Quantity", product.getQuantity());

            long index = db.updateWithOnConflict(
                    "PRODUCT",
                    contentValues,
                    "Id = ?",
                    new String[]{product.getId() + ""},
                    SQLiteDatabase.CONFLICT_IGNORE
            );

            return index > -1;
        } catch (Exception e) {
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }

        return false;
    }

}
