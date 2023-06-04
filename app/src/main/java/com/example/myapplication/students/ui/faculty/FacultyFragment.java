package com.example.myapplication.students.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FacultyFragment extends Fragment {

    private RecyclerView csDepartment, eDepartment, cDepartment, mDepartment;
    private LinearLayout cNoData, csNoData, mNoData;
    private List<StudentFacultyData> list1, list2, list3;
    private FacultyAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        csDepartment = view.findViewById(R.id.s_cs_department);
        eDepartment = view.findViewById(R.id.s_e_department);
        cDepartment = view.findViewById(R.id.s_c_department);
        mDepartment = view.findViewById(R.id.s_m_department);
        cNoData = view.findViewById(R.id.s_c_no_data);
        csNoData = view.findViewById(R.id.s_cs_no_data);
        mNoData = view.findViewById(R.id.s_m_no_data);

        reference = FirebaseDatabase.getInstance().getReference().child("Faculty");

        cDepartment();
        csDepartment();
        eDepartment();
        mDepartment();

        return view;
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
                        StudentFacultyData data = dataSnapshot.getValue(StudentFacultyData.class);
                        list2.add(data);
                    }
                    cDepartment.setHasFixedSize(true);
                    cDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list2, getContext(), "Civil");
                    cDepartment.setAdapter(adapter);
                    cNoData.setVisibility(View.GONE);
                    cDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        StudentFacultyData data = dataSnapshot.getValue(StudentFacultyData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list1, getContext(), "Computer");
                    csDepartment.setAdapter(adapter);
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eDepartment() {
        dbRef = reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                } else {
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        StudentFacultyData data = dataSnapshot.getValue(StudentFacultyData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list1, getContext(), "Computer");
                    csDepartment.setAdapter(adapter);
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                        StudentFacultyData data = dataSnapshot.getValue(StudentFacultyData.class);
                        list3.add(data);
                    }
                    mDepartment.setHasFixedSize(true);
                    mDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new FacultyAdapter(list3, getContext(), "Mechanical");
                    mDepartment.setAdapter(adapter);
                    mNoData.setVisibility(View.GONE);
                    mDepartment.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}