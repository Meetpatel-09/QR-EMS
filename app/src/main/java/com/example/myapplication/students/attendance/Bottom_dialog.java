package com.example.myapplication.students.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.admin.event.EventData;
import com.example.myapplication.students.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bottom_dialog extends BottomSheetDialogFragment {

    private TextView title, link, btn_visit;
    private ImageView close;
    private String fetchurl;

    private FirebaseUser fUser;
    private String profileId;

    private String sEnroll, department, semester, fname, event;

    private DatabaseReference reference, dbRef;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_dialog, container, false);

        title = view.findViewById(R.id.txt_title);
        link = view.findViewById(R.id.txt_link);
        btn_visit = view.findViewById(R.id.visit);
        close = view.findViewById(R.id.close);

        reference = FirebaseDatabase.getInstance().getReference().child("Attendance");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Event");
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (fUser.getUid() == null) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
        profileId = fUser.getUid();

        userInfo();
//        title.setText(fetchurl);
        title.setText(profileId);
        title.setText("Scan Successful");

        btn_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateQRCode();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void validateQRCode() {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String date = currentDate.format(calForDate.getTime());

        dbRef.child(date).orderByChild("url").equalTo(fetchurl).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {

                    Toast.makeText(getContext(), "Invalid QR code", Toast.LENGTH_SHORT).show();
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        EventData data = dataSnapshot.getValue(EventData.class);
                        assert data != null;
//                        event = data.getEventname();
                    }
                    insertData();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertData() {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", profileId);
        map.put("enroll", sEnroll);
        map.put("name", fname);
        map.put("department", department);
        map.put("semester", semester);

        String dep = department;
        String sem = semester;

        reference.child(event).child(dep).child(sem).child(profileId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(getContext(), "Attendance filled successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fetchurl(String url) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                fetchurl = url;
            }
        });
    }

    private void userInfo() {

        FirebaseDatabase.getInstance().getReference().child("students").child(profileId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                StudentData studentData = snapshot.getValue(StudentData.class);

                sEnroll = studentData.getEnroll();
                department = studentData.getDepartment();
                semester = studentData.getSemester();
                fname = studentData.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
