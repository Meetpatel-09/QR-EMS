package com.example.myapplication.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.FirstActivity;
import com.example.myapplication.R;
import com.example.myapplication.students.attendance.ScanActivity;
import com.google.android.material.card.MaterialCardView;

public class VolunteerHomeActivity extends AppCompatActivity implements View.OnClickListener  {

    private MaterialCardView scanQRCode, manageEvent, manageParticipants;
    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_home);

        scanQRCode = findViewById(R.id.scan_qr_code);
        manageEvent = findViewById(R.id.manage_event);
        manageParticipants = findViewById(R.id.manage_participants);

        logOut = findViewById(R.id.v_btn_logout);

        scanQRCode.setOnClickListener(this);
        manageEvent.setOnClickListener(this);
        manageParticipants.setOnClickListener(this);
        logOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.scan_qr_code:
                intent = new Intent(VolunteerHomeActivity.this, ScanActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_event:
                intent = new Intent(VolunteerHomeActivity.this, ManageEventsActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_participants:
                intent = new Intent(VolunteerHomeActivity.this, ManageParticipantsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_logout:
                intent = new Intent(VolunteerHomeActivity.this, FirstActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}