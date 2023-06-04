package com.example.myapplication.admin.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StudentAttAdapter extends RecyclerView.Adapter<StudentAttAdapter.StudentAttViewAdapter> {

    private Context context;
    private List<AttendanceData> list;

    public StudentAttAdapter(Context context, List<AttendanceData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public StudentAttViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_att, parent, false);
        return new StudentAttViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentAttViewAdapter holder, int position) {

        AttendanceData data = list.get(position);
        holder.enroll.setText(data.getEnroll());
        holder.studentName.setText(data.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentAttViewAdapter extends RecyclerView.ViewHolder {

        private TextView enroll,studentName;

        public StudentAttViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);

            enroll = itemView.findViewById(R.id.student_enroll);
            studentName = itemView.findViewById(R.id.student_name);
        }
    }
}
