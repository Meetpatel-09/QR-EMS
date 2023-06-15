package com.example.myapplication.students.ui.event;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.event.EventData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventFragment extends Fragment {

    private RecyclerView eventRecyclerview;
    private ProgressDialog pd;
    private ArrayList<EventData> list;
    private EventsAdapter adapter;

    private DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event, container, false);

        eventRecyclerview = view.findViewById(R.id.events);
        reference = FirebaseDatabase.getInstance().getReference().child("Events");

        pd = new ProgressDialog(getContext());

        eventRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerview.setHasFixedSize(true);

        getEvents();

        return view;
    }

    private void getEvents() {

        pd.setMessage("Loading");
        pd.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    EventData data = dataSnapshot.getValue(EventData.class);
                    list.add(0, data);
                }
                adapter = new EventsAdapter(getContext(), list);
                adapter.notifyDataSetChanged();
                eventRecyclerview.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}