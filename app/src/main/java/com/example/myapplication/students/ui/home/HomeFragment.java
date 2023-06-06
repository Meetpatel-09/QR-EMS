package com.example.myapplication.students.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);
        map = view.findViewById(R.id.map);

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.c, null));
        images.add(new SlideModel(R.drawable.campus, null));
        images.add(new SlideModel(R.drawable.classroom, null));
        images.add(new SlideModel(R.drawable.computer_lab, null));
        images.add(new SlideModel(R.drawable.lab, null));
        images.add(new SlideModel(R.drawable.event, null));
        images.add(new SlideModel(R.drawable.sports, null));

        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);

        map.setOnClickListener(v -> {
                openMap();
        });
        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=vallabh budhi polytechnic navsari");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent, "Launch Maps");
        startActivity(chooser);
    }
}