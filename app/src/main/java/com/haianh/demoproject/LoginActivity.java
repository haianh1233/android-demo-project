package com.haianh.demoproject;

import androidx.annotation.Nullable;
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
    public static final int EXIT_CODE = 100;

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
                switchActivities(user, pass);
            }
            else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == EXIT_CODE) {
            String username = "";
            String password = "";
            if(data != null) {
                username = data.getStringExtra("username");
                password = data.getStringExtra("password");
            }

            if(editText_User != null)
                editText_User.setText(username);

            if(editText_Pass != null)
                editText_Pass.setText(password);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void switchActivities(String username, String password) {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        switchActivityIntent.putExtra("username", username);
        switchActivityIntent.putExtra("password", password);
        startActivityForResult(switchActivityIntent, LoginActivity.EXIT_CODE);

    }
}