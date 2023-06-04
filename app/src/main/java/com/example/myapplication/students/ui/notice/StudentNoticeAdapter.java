package com.example.myapplication.students.ui.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudentNoticeAdapter extends RecyclerView.Adapter<StudentNoticeAdapter.StudentNoticeViewAdapter> {

    private Context context;
    private ArrayList<StudentNoticeData> list;

    public StudentNoticeAdapter(Context context, ArrayList<StudentNoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public StudentNoticeViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.s_news_feed_item_layout, parent, false);
        return new StudentNoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentNoticeViewAdapter holder, int position) {

        StudentNoticeData data = list.get(position);

        holder.noticeTitle.setText(data.getTitle());
        holder.date.setText(data.getDate());
        holder.time.setText(data.getTime());

        try {
            if (data.getImage() != null)
                Picasso.get().load(data.getImage()).into(holder.noticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentNoticeViewAdapter extends RecyclerView.ViewHolder {

        private TextView noticeTitle, date, time;
        private ImageView noticeImage;

        public StudentNoticeViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);


            noticeTitle = itemView.findViewById(R.id.s_notice_title);
            date = itemView.findViewById(R.id.s_date);
            time = itemView.findViewById(R.id.s_time);
            noticeImage = itemView.findViewById(R.id.s_notice_image);
        }
    }
}
