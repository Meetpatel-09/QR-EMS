package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.admin.attendance.ViewAttedanceActivity;
import com.example.myapplication.admin.ebook.UploadPdf;
import com.example.myapplication.admin.event.AddEventActivity;
import com.example.myapplication.admin.volunteer.ViewVolunteer;
import com.example.myapplication.admin.notice.DeleteNoticeActivity;
import com.example.myapplication.admin.notice.UploadNotice;
import com.example.myapplication.authentication.AdminLoginActivity;
import com.google.android.material.card.MaterialCardView;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialCardView uploadNotice, addGalleryImage, addFaculty, deleteNotice, viewAttendance, addEvent, addPdf;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLogin", "false").equals("false")) {
            startActivity(new Intent(AdminHomeActivity.this, AdminLoginActivity.class));
            finish();
        }

        uploadNotice = findViewById(R.id.add_notification);
        addGalleryImage = findViewById(R.id.add_image);
        addEvent = findViewById(R.id.add_event);
        addPdf = findViewById(R.id.add_pdf);
        addFaculty = findViewById(R.id.add_faculty);
        deleteNotice = findViewById(R.id.delete_notice);
//        viewAttendance = findViewById(R.id.view_attendance);
        logOut = findViewById(R.id.btn_logout);

        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addEvent.setOnClickListener(this);
        addPdf.setOnClickListener(this);
        addFaculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
//        viewAttendance.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_notification:
                intent = new Intent(AdminHomeActivity.this, UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.add_image:
                intent = new Intent(AdminHomeActivity.this, UploadImage.class);
                startActivity(intent);
                break;
            case R.id.add_event:
                intent = new Intent(AdminHomeActivity.this, AddEventActivity.class);
                startActivity(intent);
                break;
            case R.id.add_pdf:
                intent = new Intent(AdminHomeActivity.this, UploadPdf.class);
                startActivity(intent);
                break;
            case R.id.add_faculty:
                intent = new Intent(AdminHomeActivity.this, ViewVolunteer.class);
                startActivity(intent);
                break;
            case R.id.delete_notice:
                intent = new Intent(AdminHomeActivity.this, DeleteNoticeActivity.class);
                startActivity(intent);
                break;
//            case R.id.view_attendance:
//                intent = new Intent(AdminHomeActivity.this, ViewAttedanceActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btn_logout:
                editor.putString("isLogin", "false");
                editor.commit();
                intent = new Intent(AdminHomeActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}