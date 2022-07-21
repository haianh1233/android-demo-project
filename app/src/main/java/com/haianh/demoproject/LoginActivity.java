package com.haianh.demoproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.haianh.demoproject.dao.AdminDao;
import com.haianh.demoproject.database.Database;

public class LoginActivity extends AppCompatActivity {
    private EditText editText_User, editText_Pass;
    private Button button_Login, button_SignUp;
    private CheckBox rememberMe;
    public static final int EXIT_CODE = 100;

    AdminDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dao = new AdminDao(this);
        editText_User = findViewById(R.id.username);
        editText_Pass = findViewById(R.id.password);
        button_Login = findViewById(R.id.button_login);
        button_SignUp = findViewById(R.id.button_signup);
        rememberMe = findViewById(R.id.rememberMe);

        SharedPreferences pref = getSharedPreferences("information.dat", MODE_PRIVATE);
        String un = pref.getString("username", "");
        String pwd = pref.getString("password", "");
        boolean check = pref.getBoolean("rememberMe", Boolean.FALSE);

        editText_User.setText(un);
        editText_Pass.setText(pwd);
        rememberMe.setChecked(check);

        if(check)
            checkLogin();

        button_Login.setOnClickListener(v -> checkLogin());

        button_SignUp.setOnClickListener(v -> {
            getSharedPreferences("information.dat", 0).edit().clear().commit();
            editText_User.setText("");
            editText_Pass.setText("");

            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
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

    public void checkLogin(){
        String strUser = editText_User.getText().toString();
        String pass = editText_Pass.getText().toString();
        boolean check = rememberMe.isChecked();
        if(strUser.isEmpty() || pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Username or password wrong", Toast.LENGTH_SHORT).show();
        }else{
            if(dao.Authentication(strUser, pass)){
                Database.US = strUser;
                Database.PW = pass;
                Remember(strUser, pass, check);
                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("username", strUser);
                i.putExtra("password", pass);
                startActivityForResult(i, LoginActivity.EXIT_CODE);
            }else{
                Toast.makeText(getApplicationContext(), "Username or password wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void Remember(String user_name, String pass_word, boolean check){
        SharedPreferences pref = getSharedPreferences("information.dat", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if(check){
            editor.putString("username",user_name);
            editor.putString("password",pass_word);
            editor.putBoolean("rememberMe", check);

        }else{
            editor.clear();
        }
        editor.commit();
    }
}