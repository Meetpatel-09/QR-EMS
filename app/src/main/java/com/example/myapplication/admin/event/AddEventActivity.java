package com.example.myapplication.admin.event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import com.example.myapplication.admin.AdminHomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddEventActivity extends AppCompatActivity {

    private EditText etName, etDate, etFees, etPlayers;
    private String sName, sDate, sFees, sPlayers;
    private Button btn1;
    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    String downloadUrl = "";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        reference = FirebaseDatabase.getInstance().getReference().child("Events");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Event");
        storageReference = FirebaseStorage.getInstance().getReference();

        etName = findViewById(R.id.et_event);
        etDate = findViewById(R.id.et_e_date);
        etFees = findViewById(R.id.et_e_fees);
        etPlayers = findViewById(R.id.et_e_playes);

        btn1 = findViewById(R.id.btn1);

        btn1.setOnClickListener(v -> validateData());

    }

    private void validateData() {

        sName = etName.getText().toString();
        sDate = etDate.getText().toString();
        sFees = etFees.getText().toString();
        sPlayers = etPlayers.getText().toString();

        if (sName.isEmpty()) {
            etName.setError("Required");
            etName.requestFocus();
        } else if (sDate.isEmpty()) {
            etDate.setError("Required");
            etDate.requestFocus();
        } else if (sFees.isEmpty()) {
            etFees.setError("Required");
            etFees.requestFocus();
        } else if (sPlayers.isEmpty()) {
            etPlayers.setError("Required");
            etPlayers.requestFocus();
        } else {
            uploadData();
        }

    }

    private void uploadData() {
        final String uniqueKey = dbRef.push().getKey();

        EventData eventData = new EventData(sName, sFees, sDate, sPlayers, uniqueKey);
        dbRef.child(sDate).child(uniqueKey).setValue(eventData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                reference.child(uniqueKey).setValue(eventData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddEventActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddEventActivity.this, AdminHomeActivity.class));
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(AddEventActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}