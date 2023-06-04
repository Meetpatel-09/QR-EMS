package com.example.myapplication.admin.attendance;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DisplayAttendanceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AttendanceData> list1;
    private StudentAttAdapter adapter;

    private DatabaseReference reference, dbRef;

    private String event, department, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attendance);

        recyclerView = findViewById(R.id.recycler_attendance);

        reference = FirebaseDatabase.getInstance().getReference().child("Attendance");

        event = getIntent().getStringExtra("event");
        department = getIntent().getStringExtra("department");
        semester = getIntent().getStringExtra("semester");

        viewAtt();

    }

    private void viewAtt() {

        dbRef = reference.child(event).child(department).child(semester);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    Toast.makeText(DisplayAttendanceActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        AttendanceData data = dataSnapshot.getValue(AttendanceData.class);
                        list1.add(data);
                    }
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(DisplayAttendanceActivity.this));
                adapter = new StudentAttAdapter(DisplayAttendanceActivity.this, list1);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(DisplayAttendanceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}