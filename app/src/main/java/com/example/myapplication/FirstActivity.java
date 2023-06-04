package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.authentication.AdminLoginActivity;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.students.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStudetnReg, btnStudentLogin, btnAdminLogin;
    private FirebaseAuth auth;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("true")) {
            startActivity(new Intent(FirstActivity.this, AdminLoginActivity.class));
            finish();
        }

        auth = FirebaseAuth.getInstance();

        btnStudetnReg = findViewById(R.id.btn_student_reg);
        btnStudentLogin = findViewById(R.id.btn_student_login);
        btnAdminLogin = findViewById(R.id.btn_admin_login);

        btnStudetnReg.setOnClickListener(this);
        btnStudentLogin.setOnClickListener(this);
        btnAdminLogin.setOnClickListener(this);
    }
    private void openHome() {
        startActivity(new Intent(FirstActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() != null) {
            openHome();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_student_reg:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btn_student_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btn_admin_login:
                startActivity(new Intent(this, AdminLoginActivity.class));
                break;

        }
    }
}