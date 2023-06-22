package com.example.myapplication.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.students.attendance.StudentData;
import com.example.myapplication.students.ui.profile.MyEventsAdapter;
import com.example.myapplication.students.ui.profile.MyEventsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ParticipantEventsActivity extends AppCompatActivity {

    private TextView tvName, tvNumber, tvEnrollment;
    private ImageView imgProfile;
    private RecyclerView participantsEvents;
    private ProgressDialog pd;
    private ArrayList<MyEventsModel> list;
    private MyEventsAdapter adapter;

    private String userID;

    private DatabaseReference reference, databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_events);

        tvName = findViewById(R.id.participant_name);
        tvNumber = findViewById(R.id.participant_number);
        tvEnrollment = findViewById(R.id.participant_enroll);

        imgProfile = findViewById(R.id.participant_image);

        participantsEvents = findViewById(R.id.re_participant_events2);

        userID = getIntent().getStringExtra("sId");

        System.out.println("userID");
        System.out.println(userID);
        System.out.println("userID");

        reference = FirebaseDatabase.getInstance().getReference().child("Participated").child(userID);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("students");

        pd = new ProgressDialog(ParticipantEventsActivity.this);

        getProfile();

        getEvents();

    }

    private void getProfile() {

        databaseReference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentData data = snapshot.getValue(StudentData.class);

                tvName.setText(String.format("Name: %s", data.getName()));
                tvNumber.setText(String.format("Number: %s", data.getPhone()));
                tvEnrollment.setText(String.format("Enrollment: %s", data.getEnroll()));

                if (data.getIdPhoto().equals("No")) {
                    imgProfile.setImageResource(R.drawable.profile);
                } else {
                    Picasso.get().load(data.getProfilePhoto()).into(imgProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getEvents() {

        pd.setMessage("Loading");
        pd.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list = new ArrayList<>();
//                System.out.println(snapshot);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot);
                    MyEventsModel data = dataSnapshot.getValue(MyEventsModel.class);
                    list.add(0, data);
                }
//                System.out.println(list);
                adapter = new MyEventsAdapter(ParticipantEventsActivity.this, list);
                adapter.notifyDataSetChanged();
                participantsEvents.setAdapter(adapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                pd.dismiss();
                Toast.makeText(ParticipantEventsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    }