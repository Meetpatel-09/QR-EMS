package com.example.myapplication.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.admin.event.EventData;
import com.example.myapplication.students.ui.event.EventsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ManageEventsActivity extends AppCompatActivity {

    private RecyclerView eventRecyclerview;
    private ProgressDialog pd;
    private ArrayList<EventData> list;
    private EventsAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        eventRecyclerview = findViewById(R.id.manage_events_1);
        reference = FirebaseDatabase.getInstance().getReference().child("Events");

        pd = new ProgressDialog(this);

        eventRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        eventRecyclerview.setHasFixedSize(true);

        getEvents();

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
                adapter = new EventsAdapter(ManageEventsActivity.this, list, "Update");
                adapter.notifyDataSetChanged();
                eventRecyclerview.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(ManageEventsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}