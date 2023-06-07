package com.example.myapplication.students.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView noticeRecyclerview;
    private ProgressBar progressBar;
    private ArrayList<StudentNoticeData> list;
    private StudentNoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        noticeRecyclerview = view.findViewById(R.id.delete_notice_recyclerview);
        progressBar = view.findViewById(R.id.progress_bar);

        reference = FirebaseDatabase.getInstance().getReference().child("Notice");

        noticeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeRecyclerview.setHasFixedSize(true);

        getNotice();

        return view;
    }
    private void getNotice() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    StudentNoticeData data = dataSnapshot.getValue(StudentNoticeData.class);
                    list.add(0,data);
                }

                adapter = new StudentNoticeAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                noticeRecyclerview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}