package com.example.myapplication.students.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.admin.event.EventData;
import com.example.myapplication.students.ui.profile.MyEventsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {

    private Context context;
    private ArrayList<EventData> list;
    private String source;

    public EventsAdapter(Context context, ArrayList<EventData> list, String source) {
        this.context = context;
        this.list = list;
        this.source = source;
    }

    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_event_item, parent, false);
        return new EventsAdapter.EventsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        EventData data = list.get(position);

        if (Objects.equals(source, "Approve")) {
            holder.btnParticipate.setText("Approve");
        } else if (Objects.equals(source, "Participate")) {
            holder.btnParticipate.setText("Participate");
        } else {
            holder.btnParticipate.setText("Update");
        }

        holder.tvEvent.setText(String.format("Event/Game: %s", data.getEvent()));
        holder.tvDate.setText(String.format("Date: %s", data.getDate()));
        holder.tvFees.setText(String.format("Entry Fees: %s", data.getFees()));
        holder.tvPlayers.setText(String.format("Number of Players: %s", data.getPlayers()));
        holder.btnParticipate.setOnClickListener(v -> {
            if (Objects.equals(source, "Approve")) {
//                String id =  FirebaseAuth.getInstance().getCurrentUser().getUid();
//                FirebaseDatabase.getInstance().getReference().child("Participants").child(data.getEvent()).child(id).setValue(true);
//
//                MyEventsModel model = new MyEventsModel(data.getEvent(), "Pending", data.getDate(), data.getPlayers(), data.getKey(), "-");
//
//                FirebaseDatabase.getInstance().getReference().child("Participated").child().child(data.getKey()).setValue(model);

            } else if (Objects.equals(source, "Participate")) {
                String id =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference().child("Participants").child(data.getEvent()).child(id).setValue(true);

                MyEventsModel model = new MyEventsModel(data.getEvent(), "Pending", data.getDate(), data.getPlayers(), data.getKey(), "-");

                FirebaseDatabase.getInstance().getReference().child("Participated").child(id).child(data.getKey()).setValue(model);

            } else {
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEvent, tvDate, tvFees,tvPlayers;
        private Button btnParticipate;

        public EventsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEvent = itemView.findViewById(R.id.tv_event_name);
            tvDate = itemView.findViewById(R.id.tv_event_date);
            tvFees = itemView.findViewById(R.id.tv_event_fees);
            tvPlayers = itemView.findViewById(R.id.tv_event_players);
            btnParticipate = itemView.findViewById(R.id.btn_participate);

        }
    }
}
