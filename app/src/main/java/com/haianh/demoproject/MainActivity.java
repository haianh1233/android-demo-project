package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(v -> {
            setResult(LoginActivity.EXIT_CODE, intent);
            finish();
        });
    }
}