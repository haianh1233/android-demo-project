package com.haianh.demoproject.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.haianh.demoproject.database.Database;
import com.haianh.demoproject.model.CartItem;

import java.util.ArrayList;
import java.util.List;


public class CartDao {
    Database databaseHelper;

    public CartDao(@NonNull Database databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public CartDao(@NonNull Context context) {
        databaseHelper = new Database(context);
    }


    public List<CartItem> getCartItems(String accountId) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        List<CartItem> list = null;

        db = databaseHelper.getReadableDatabase();
        String sql = "SELECT Id, ProductId, Quantity FROM CART_DETAIL " +
                "WHERE AccountId = ?";

        cs = db.rawQuery(sql, new String[]{accountId + ""});

        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            int productId = cs.getInt(1);
            int quantity = cs.getInt(2);

            if (list == null) {
                list = new ArrayList<>();
            }

            list.add(new CartItem(id, accountId, productId, quantity));
            cs.moveToNext();
        }

        if (cs != null) cs.close();
        if (db != null) db.close();
        return list;
    }

    public CartItem getCartItem(String accountId, int productId) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        CartItem cartItem = null;

        db = databaseHelper.getReadableDatabase();
        String sql = "SELECT Id, Quantity " +
                "FROM CART_DETAIL " +
                "WHERE AccountId=? and ProductId=?";

        cs = db.rawQuery(sql, new String[]{accountId + "", productId + ""});
        if (cs.getCount() > 0) {
            cs.moveToFirst();
            int id = cs.getInt(0);
            int quantity = cs.getInt(1);

            cartItem = new CartItem(id, accountId, productId, quantity);
        }

        if (cs != null) cs.close();
        if (db != null) db.close();
        return cartItem;
    }

    public void updateCart(int id, int quantity) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        try {
            db = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Quantity", quantity);

            db.updateWithOnConflict(
                    "CART_DETAIL",
                    contentValues,
                    "Id = " + id,
                    null,
                    SQLiteDatabase.CONFLICT_IGNORE
            );
        } catch (Exception e) {
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }
    }

    public void insertCart(String accountId, int productId, int quantity) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        try {
            db = databaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("AccountId", accountId);
            contentValues.put("ProductId", productId);
            contentValues.put("Quantity", quantity);

            db.insert("CART_DETAIL", null, contentValues);
        } catch (Exception e) {
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }
    }

    public void deleteCartItem(int id) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        try {
            db = databaseHelper.getWritableDatabase();
            db.delete("CART_DETAIL", "ID = ?", new String[]{id + ""});
        } catch (Exception e) {
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }
    }

    public void deleteAllCartItem(String accountId) {
        SQLiteDatabase db = null;
        Cursor cs = null;
        try {
            db = databaseHelper.getWritableDatabase();
            db.delete("CART_DETAIL", "AccountId = ?", new String[]{accountId + ""});
        } catch (Exception e) {
        } finally {
            if (cs != null) cs.close();
            if (db != null) db.close();
        }
    }
}
