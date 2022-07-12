package com.haianh.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haianh.demoproject.dao.ItemDao;
import com.haianh.demoproject.model.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String welcomeText;
    private Button exitButton;
    private Button addItemButton;
    List<Item> items;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemButton = findViewById(R.id.addNewItem);

        Intent intent = getIntent();

        if(intent != null) {
            String name = intent.getStringExtra("username");

            String welcomeStatement = "Welcome " + name + " to Home page.";

            TextView welcomeTextView = findViewById(R.id.welcome_text);
            welcomeTextView.setText(welcomeStatement);

            Toast.makeText(this, welcomeStatement, Toast.LENGTH_LONG).show();
        }

//        exitButton = findViewById(R.id.exit_button);
//        exitButton.setOnClickListener(v -> {
//            setResult(LoginActivity.EXIT_CODE, intent);
//            finish();
//        });

        ListView recyclerView = findViewById(R.id.main_view);

        items = ItemDao.getAll(MainActivity.this);
        adapter = new ArrayAdapter(MainActivity.this,  android.R.layout.simple_list_item_1 , items);

        recyclerView.setAdapter(adapter);

        addItemButton.setOnClickListener(v -> showAddDialog());

        recyclerView.setOnItemClickListener(((parent, view, position, id) -> showUpdateDialog(position)));
    }

    private void showUpdateDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText name = view.findViewById(R.id.updateItemName);
        EditText price = view.findViewById(R.id.updateItemPrice);
        EditText description = view.findViewById(R.id.updateItemDescription);
        Button btnUpdate = view.findViewById(R.id.btnUpdateItem);
        Button btnDelete = view.findViewById(R.id.btnDeleteItem);
        Item item = items.get(position);
        name.setText(item.getName());
        price.setText(String.valueOf(item.getPrice()));
        description.setText(item.getDescription());

        btnUpdate.setOnClickListener(v -> {
            item.setName(name.getText().toString());
            item.setPrice(Float.parseFloat(price.getText().toString()));
            item.setDescription(description.getText().toString());

            if(ItemDao.Update(MainActivity.this, item)){
                Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                items.clear();
                items.addAll(ItemDao.getAll(MainActivity.this));
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }else{
                Toast.makeText(MainActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();

            }
        });


        btnDelete.setOnClickListener(v -> {

            if(ItemDao.delete(MainActivity.this, item.getId())){
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                items.clear();
                items.addAll(ItemDao.getAll(MainActivity.this));
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }else{
                Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText name = view.findViewById(R.id.addItemName);
        EditText price = view.findViewById(R.id.addItemPrice);
        EditText description = view.findViewById(R.id.addItemDescription);
        Button save = view.findViewById(R.id.btnAddItem);
        save.setOnClickListener(v -> {
            String itemName = name.getText().toString();
            Float itemPrice = Float.parseFloat(price.getText().toString());
            String itemDescription = description.getText().toString();
            if(ItemDao.insert(MainActivity.this, itemName, itemPrice,itemDescription)){
                Toast.makeText(MainActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                items.clear();
                items.addAll(ItemDao.getAll(MainActivity.this));
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }else{
                Toast.makeText(MainActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_scrolling, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout: {
                Intent intent = getIntent();
                setResult(LoginActivity.EXIT_CODE, intent);
                finish();
                return true;
            }
            case R.id.help: {
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                return true;
            }
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}