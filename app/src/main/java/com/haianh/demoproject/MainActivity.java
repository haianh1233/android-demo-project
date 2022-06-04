package com.haianh.demoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String welcomeText;
    private Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        RecyclerView recyclerView = findViewById(R.id.main_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<MyData> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(new MyData("Item" + i));
        }

        Adapter adapter = new Adapter(list, getApplicationContext());

        recyclerView.setAdapter(adapter);
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
            default:
        }

        return super.onOptionsItemSelected(item);
    }
}