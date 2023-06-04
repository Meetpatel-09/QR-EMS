package com.example.myapplication.students.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewAdapter> {

    private Context context;
    private List<String> list;

    public GalleryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public GalleryViewAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image, parent,false);
        return new GalleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GalleryViewAdapter holder, int position) {

        try {
            Picasso.get().load(list.get(position)).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GalleryViewAdapter extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GalleryViewAdapter(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }
}
