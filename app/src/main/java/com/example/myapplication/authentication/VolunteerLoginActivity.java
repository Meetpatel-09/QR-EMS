package com.example.myapplication.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.students.MainActivity;
import com.example.myapplication.students.attendance.StudentData;
import com.example.myapplication.volunteer.VolunteerHomeActivity;
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

import java.util.Objects;

public class VolunteerLoginActivity extends AppCompatActivity {

    private EditText email, pwd;
    private Button btnLogin;

    private String sEmail, sPwd, Lemail, isVolunteer;

    private FirebaseAuth auth;

    private ProgressDialog pd;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_login);

        pd = new ProgressDialog(this);

        email = findViewById(R.id.v_login_email);
        pwd = findViewById(R.id.v_login_password);
        btnLogin = findViewById(R.id.v_btn_login);

        reference = FirebaseDatabase.getInstance().getReference().child("students");
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
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
                    Toast.makeText(VolunteerLoginActivity.this, "Account Not Found", Toast.LENGTH_SHORT).show();
                } else {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentData data = dataSnapshot.getValue(StudentData.class);
                        assert data != null;
                        Lemail = data.getEmail();
                        isVolunteer = data.getIsVolunteer();
                        login();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(VolunteerLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void login() {
        auth.signInWithEmailAndPassword(Lemail, sPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    if (!Objects.equals(isVolunteer, "No")) {
                        startActivity(new Intent(VolunteerLoginActivity.this, VolunteerHomeActivity.class));
                    } else {
                        Toast.makeText(VolunteerLoginActivity.this, "Your not a volunteer", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    Toast.makeText(VolunteerLoginActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                pd.dismiss();
                Toast.makeText(VolunteerLoginActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}