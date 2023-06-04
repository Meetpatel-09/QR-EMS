package com.example.myapplication.admin.faculty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewFaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csDepartment, cDepartment, mDepartment;
    private LinearLayout cNoData, csNoData, mNoData;
    private List<FacultyData> list1, list2, list3;
    private FacultyAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        fab = findViewById(R.id.fab);
        csDepartment = findViewById(R.id.cs_department);
        cDepartment = findViewById(R.id.c_department);
        mDepartment = findViewById(R.id.m_department);
        cNoData = findViewById(R.id.c_no_data);
        csNoData = findViewById(R.id.cs_no_data);
        mNoData = findViewById(R.id.m_no_data);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        cDepartment();
        csDepartment();
        mDepartment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewFaculty.this, AddFaculty.class));
            }
        });
    }

    private void cDepartment() {
        dbRef = reference.child("Civil");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list2 = new ArrayList<>();
                if (!snapshot.exists()){
                    cNoData.setVisibility(View.VISIBLE);
                    cDepartment.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        FacultyData data = dataSnapshot.getValue(FacultyData.class);
                        list2.add(data);
                    }
                    cDepartment.setHasFixedSize(true);
                    cDepartment.setLayoutManager(new LinearLayoutManager(ViewFaculty.this));
                    adapter = new FacultyAdapter(list2, ViewFaculty.this, "Civil");
                    cDepartment.setAdapter(adapter);
                    cNoData.setVisibility(View.GONE);
                    cDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void csDepartment() {
        dbRef = reference.child("Computer");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        FacultyData data = dataSnapshot.getValue(FacultyData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(ViewFaculty.this));
                    adapter = new FacultyAdapter(list1, ViewFaculty.this, "Computer");
                    csDepartment.setAdapter(adapter);
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mDepartment() {
        dbRef = reference.child("Mechanical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list3 = new ArrayList<>();
                if (!snapshot.exists()){
                    mNoData.setVisibility(View.VISIBLE);
                    mDepartment.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        FacultyData data = dataSnapshot.getValue(FacultyData.class);
                        list3.add(data);
                    }
                    mDepartment.setHasFixedSize(true);
                    mDepartment.setLayoutManager(new LinearLayoutManager(ViewFaculty.this));
                    adapter = new FacultyAdapter(list3, ViewFaculty.this, "Mechanical");
                    mDepartment.setAdapter(adapter);
                    mNoData.setVisibility(View.GONE);
                    mDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}