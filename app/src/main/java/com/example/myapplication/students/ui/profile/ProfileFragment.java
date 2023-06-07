package com.example.myapplication.students.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.students.attendance.StudentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerViewEvents;
    private Button btnEditProfile, btnVolunteer;
    private ImageView imgProfile;
    private TextView tvName, tvEnrollment;

    private RecyclerView eventRecyclerview;
    private ProgressDialog pd;
    private ArrayList<MyEventsModel> list;
    private MyEventsAdapter adapter;

    private DatabaseReference reference, databaseReference;

    private FirebaseUser fUser;
    private String profileId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = view.findViewById(R.id.my_profile_img);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnVolunteer = view.findViewById(R.id.btn_volunteer);
        tvName = view.findViewById(R.id.tv_name);
        tvEnrollment = view.findViewById(R.id.tv_enroll);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        profileId = fUser.getUid();

        eventRecyclerview = view.findViewById(R.id.my_events);
        reference = FirebaseDatabase.getInstance().getReference().child("Participated").child(profileId);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("students");

        pd = new ProgressDialog(getContext());

        eventRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerview.setHasFixedSize(true);

        getProfile();

        getEvents();


        return view;
    }

    private void getProfile() {

        databaseReference.child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentData data = snapshot.getValue(StudentData.class);

                tvName.setText(String.format("Name: %s", data.getName()));
                tvEnrollment.setText(String.format("Enrollment: %s", data.getEnroll()));
                if (data.getIdPhoto().equals("No")){
                    imgProfile.setImageResource(R.drawable.profile);
                }else {
                    Picasso.get().load(data.getIdPhoto()).into(imgProfile);
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
                System.out.println(snapshot);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MyEventsModel data = dataSnapshot.getValue(MyEventsModel.class);
                    list.add(0, data);
                }

                adapter = new MyEventsAdapter(getContext(), list);
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