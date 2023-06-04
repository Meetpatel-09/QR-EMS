package com.example.myapplication.admin.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewAdapter> {

    private List<FacultyData> list;
    private Context context;
    private String category;

    public FacultyAdapter(List<FacultyData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    @NonNull
    @NotNull
    @Override
    public FacultyViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent, false);
        return new FacultyViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FacultyViewAdapter holder, int position) {
        FacultyData data = list.get(position);
        holder.name.setText(data.getName());
        holder.email.setText(data.getEmail());
        holder.post.setText(data.getPost());
        try {
            Picasso.get().load(data.getImage()).into(holder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateFaculty.class);
                intent.putExtra("name", data.getName());
                intent.putExtra("email", data.getEmail());
                intent.putExtra("post", data.getPost());
                intent.putExtra("image", data.getImage());
                intent.putExtra("key", data.getKey());
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FacultyViewAdapter extends RecyclerView.ViewHolder {

        private TextView name, email, post;
        private Button update;
        private ImageView image;

        public FacultyViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.faculty_name);
            post = itemView.findViewById(R.id.faculty_post);
            email = itemView.findViewById(R.id.faculty_email);
            update = itemView.findViewById(R.id.faculty_update);
            image = itemView.findViewById(R.id.faculty_image);
        }
    }
}
