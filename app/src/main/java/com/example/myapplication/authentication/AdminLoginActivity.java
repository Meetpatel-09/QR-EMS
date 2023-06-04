package com.example.myapplication.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.AdminHomeActivity;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPwd;
    private TextView show;
    private RelativeLayout login;

    private String email, pwd;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("yes")) {
            openDash();
        }

        etEmail = findViewById(R.id.email);
        etPwd = findViewById(R.id.password);

        show = findViewById(R.id.tv_show);

        login = findViewById(R.id.login_btn);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPwd.getInputType() == 144) {
                    etPwd.setInputType(129);
                    show.setText("Hide");
                } else {
                    etPwd.setInputType(144);
                    show.setText("Show");
                }
                etPwd.setSelection(etPwd.getText().length());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private void validateData() {
        email = etEmail.getText().toString();
        pwd = etPwd.getText().toString();

        if (email.isEmpty()) {
            etEmail.setError("Please enter email");
            etEmail.requestFocus();
        } else if (pwd.isEmpty()) {
            etPwd.setError("Please enter email");
            etPwd.requestFocus();
        } else if (email.equals("admin@gmail.com") && pwd.equals("123456")) {
            editor.putString("isLogin", "yes");
            editor.commit();
            openDash();
        } else {
            Toast.makeText(this, "Please enter correct email and password.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openDash() {
        startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
        finish();
    }
}