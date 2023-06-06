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
import com.example.myapplication.authentication.VolunteerLoginActivity;
import com.example.myapplication.students.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStudentSign, btnVolunteerSign, btnAdminSign;
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

        btnStudentSign = findViewById(R.id.btn_student_sign);
        btnVolunteerSign = findViewById(R.id.btn_volunteer_sign);
        btnAdminSign = findViewById(R.id.btn_admin_sign);

        btnStudentSign.setOnClickListener(this);
        btnVolunteerSign.setOnClickListener(this);
        btnAdminSign.setOnClickListener(this);
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
            case R.id.btn_student_sign:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.btn_volunteer_sign:
                startActivity(new Intent(this, VolunteerLoginActivity.class));
                break;

            case R.id.btn_admin_sign:
                startActivity(new Intent(this, AdminLoginActivity.class));
                break;

        }
    }
}