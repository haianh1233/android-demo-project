package com.haianh.demoproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haianh.demoproject.dao.AdminDao;

public class SignupActivity extends AppCompatActivity {

    private Button btnSignup;
    private EditText editText_User, editText_Pass, editText_PassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = findViewById(R.id.button_signup1);
        editText_User = findViewById(R.id.username1);
        editText_Pass = findViewById(R.id.password1);
        editText_PassConfirm = findViewById(R.id.password_confirm);

        btnSignup.setOnClickListener(t -> {
            String username = editText_User.getText().toString();
            String password = editText_Pass.getText().toString();
            String confirm = editText_PassConfirm.getText().toString();

            if(!password.equals(confirm)) {
                Toast.makeText(this, "Confirm does not match password", Toast.LENGTH_SHORT).show();
                return;
            }

            AdminDao dao = new AdminDao(this);
            if(dao.isExist(username)) {
                Toast.makeText(this, "Username already exist", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean createResult = dao.createAccount(username, password);
            if(!createResult) {
                Toast.makeText(this, "Signup failed with unknown error", Toast.LENGTH_SHORT).show();
                return;
            }

            dao.createAccount(username, password);

            Toast.makeText(this, "Welcome to Foody", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
        });
    }
}