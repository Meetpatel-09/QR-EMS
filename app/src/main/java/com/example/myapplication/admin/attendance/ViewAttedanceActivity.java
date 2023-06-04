package com.example.myapplication.admin.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class ViewAttedanceActivity extends AppCompatActivity {

    private Button btnNxt;
    private String event, department, semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attedance);

        btnNxt = findViewById(R.id.btn_nxt);

        btnNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectMethod();
            }
        });
    }

    private void redirectMethod() {

        Spinner sEvent = findViewById(R.id.s_event);
        Spinner sDepartment = findViewById(R.id.s_department);
        Spinner sSemester = findViewById(R.id.s_semester);

        event = sEvent.getSelectedItem().toString();
        department = sDepartment.getSelectedItem().toString();
        semester = sSemester.getSelectedItem().toString();

        if (event.equals("Select Event")) {
            Toast.makeText(this, "Please select event.", Toast.LENGTH_SHORT).show();
        } else if (department.equals("Select Department")) {
            Toast.makeText(this, "Please select department.", Toast.LENGTH_SHORT).show();
        } else if (department.equals("Select Semester")) {
            Toast.makeText(this, "Please select semester.", Toast.LENGTH_SHORT).show();
        } else {
            if (event.equals("Annual Function")) {
                event = "AnnualFunction";
            }

            Intent intent = new Intent(ViewAttedanceActivity.this, DisplayAttendanceActivity.class);
            intent.putExtra("event", event);
            intent.putExtra("department", department);
            intent.putExtra("semester", semester);
            startActivity(intent);
        }
    }
}