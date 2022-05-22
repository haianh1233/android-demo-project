package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_User, editText_Pass;
    private Button button_Login, button_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_User = findViewById(R.id.username);
        editText_Pass = findViewById(R.id.password);
        button_Login = findViewById(R.id.button_login);
        button_SignUp = findViewById(R.id.button_signup);

        button_Login.setOnClickListener(v -> {
            String user = editText_User.getText().toString();
            String pass = editText_Pass.getText().toString();
            if(user.equals("admin") && pass.equals("admin")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
                switchActivities();
            }
            else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            }

        });
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }
}