package com.example.myapplication.students.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.MyEventsViewHolder>{

    private Context context;
    private ArrayList<MyEventsModel> list;

    public MyEventsAdapter(Context context, ArrayList<MyEventsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyEventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_event_item2, parent, false);
        return new MyEventsAdapter.MyEventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventsViewHolder holder, int position) {

        MyEventsModel data = list.get(position);
        holder.tvEvent.setText(String.format("Event/Game: %s", data.getEvent()));
        holder.tvDate.setText(String.format("Date: %s", data.getDate()));
        holder.tvFees.setText(String.format("Entry Fees: %s", data.getFees()));
        holder.tvPlayers.setText(String.format("Number of Players: %s", data.getPlayers()));
        holder.tvStatus.setText(String.format("Status: %s", data.getStatus()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyEventsViewHolder extends RecyclerView.ViewHolder {

        private TextView tvEvent, tvDate, tvFees,tvPlayers, tvStatus;

        public MyEventsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEvent = itemView.findViewById(R.id.tv_p_event_name);
            tvDate = itemView.findViewById(R.id.tv_p_event_date);
            tvFees = itemView.findViewById(R.id.tv_p_event_fees);
            tvPlayers = itemView.findViewById(R.id.tv_p_event_players);
            tvStatus = itemView.findViewById(R.id.tv_p_status);
        }
    }
}
