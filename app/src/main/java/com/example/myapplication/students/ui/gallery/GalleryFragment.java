package com.example.myapplication.students.ui.gallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GalleryFragment extends Fragment {

    RecyclerView annualFunctionRecyclerview, independenceDayRecyclerview, projectFairRecyclerview, otherEventsRecyclerview;

    GalleryAdapter adapter;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        annualFunctionRecyclerview = view.findViewById(R.id.annual_function_recyclerview);
        independenceDayRecyclerview = view.findViewById(R.id.independence_day_recyclerview);
        projectFairRecyclerview = view.findViewById(R.id.project_fair_recyclerview);
        otherEventsRecyclerview = view.findViewById(R.id.other_events_recyclerview);

        reference = FirebaseDatabase.getInstance().getReference().child("Gallery");

        getAnnualFunctionImages();
        getIndependentDayImages();
        getProjectFairImages();
        getOtherEventImages();

        return view;
    }

    private void getAnnualFunctionImages() {
        reference.child("Annual Function").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);
                annualFunctionRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                annualFunctionRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIndependentDayImages() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);
                independenceDayRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                independenceDayRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProjectFairImages() {
        reference.child("Project Fair").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);
                projectFairRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                projectFairRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOtherEventImages() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {

            List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String data = (String) dataSnapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);
                otherEventsRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                otherEventsRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}