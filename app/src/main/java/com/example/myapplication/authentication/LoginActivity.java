package com.example.myapplication.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.students.MainActivity;
import com.example.myapplication.students.attendance.StudentData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pwd;
    private TextView openReg;
    private Button btnLogin;

    private String sEmail, sPwd, Lemail;

    private FirebaseAuth auth;

    private ProgressDialog pd;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pd = new ProgressDialog(this);

        email = findViewById(R.id.login_email);
        pwd = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btn_login);
        openReg = findViewById(R.id.tv_reg);

        reference = FirebaseDatabase.getInstance().getReference().child("students");
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        openReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void validateData() {

        sEmail = email.getText().toString();
        sPwd = pwd.getText().toString();

        if (sEmail.isEmpty()) {
            email.setError("Required");
            email.requestFocus();
        } else if (sPwd.isEmpty()) {
            pwd.setError("Required");
            pwd.requestFocus();
        } else {
            loginUSer();
        }

    }

    private void loginUSer() {
        pd.setMessage("Loading...");
        pd.show();

        reference.orderByChild("enroll").equalTo(sEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(LoginActivity.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                } else {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentData data = dataSnapshot.getValue(StudentData.class);
                        Lemail = data.getEmail();
                        login();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {
        auth.signInWithEmailAndPassword(Lemail, sPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}