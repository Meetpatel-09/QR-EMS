package com.example.myapplication.admin.volunteer;

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

import com.example.myapplication.students.attendance.StudentData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewVolunteer extends AppCompatActivity {

//    FloatingActionButton fab;
    private RecyclerView csDepartment, cDepartment, mDepartment, eDepartment;
    private LinearLayout cNoData, csNoData, eNoData, mNoData;
    private List<StudentData> list1, list2, list3, list4;
    private VolunteerAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_volunteer);

//        fab = findViewById(R.id.fab);
        csDepartment = findViewById(R.id.cs_department);
        cDepartment = findViewById(R.id.c_department);
        mDepartment = findViewById(R.id.m_department);
        eDepartment = findViewById(R.id.e_department);
        cNoData = findViewById(R.id.c_no_data);
        csNoData = findViewById(R.id.cs_no_data);
        eNoData = findViewById(R.id.e_no_data);
        mNoData = findViewById(R.id.m_no_data);

//        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");
        reference = FirebaseDatabase.getInstance().getReference();

        cDepartment();

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ViewVolunteer.this, AddVolunteer.class));
//            }
//        });
    }

    private void cDepartment() {
        dbRef = reference.child("students");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                list2 = new ArrayList<>();
                list3 = new ArrayList<>();
                list4 = new ArrayList<>();
                if (!snapshot.exists()){
                    cNoData.setVisibility(View.VISIBLE);
                    cDepartment.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        StudentData data = dataSnapshot.getValue(StudentData.class);
                        assert data != null;
                        if (!Objects.equals(data.getHasApplied(), "No")) {

                            switch (data.getDepartment()) {
                                case "Civil":
                                    list2.add(data);
                                    break;
                                case "Computer":
                                    list1.add(data);
                                    break;
                                case "Electrical":
                                    list4.add(data);
                                    break;
                                case "Mechanical":
                                    list3.add(data);
                                    break;

                            }
                        }
                    }

                    cDepartment.setHasFixedSize(true);
                    cDepartment.setLayoutManager(new LinearLayoutManager(ViewVolunteer.this));
                    adapter = new VolunteerAdapter(list2, ViewVolunteer.this, "Civil");
                    cDepartment.setAdapter(adapter);
                    cNoData.setVisibility(View.GONE);
                    cDepartment.setVisibility(View.VISIBLE);

                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(ViewVolunteer.this));
                    adapter = new VolunteerAdapter(list1, ViewVolunteer.this, "Computer");
                    csDepartment.setAdapter(adapter);
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);

                    eDepartment.setHasFixedSize(true);
                    eDepartment.setLayoutManager(new LinearLayoutManager(ViewVolunteer.this));
                    adapter = new VolunteerAdapter(list4, ViewVolunteer.this, "Electric");
                    eDepartment.setAdapter(adapter);
                    eNoData.setVisibility(View.GONE);
                    eDepartment.setVisibility(View.VISIBLE);

                    mDepartment.setHasFixedSize(true);
                    mDepartment.setLayoutManager(new LinearLayoutManager(ViewVolunteer.this));
                    adapter = new VolunteerAdapter(list3, ViewVolunteer.this, "Mechanical");
                    mDepartment.setAdapter(adapter);
                    mNoData.setVisibility(View.GONE);
                    mDepartment.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}