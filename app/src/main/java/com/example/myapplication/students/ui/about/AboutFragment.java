package com.example.myapplication.students.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        map = view.findViewById(R.id.about_map);

        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.civil, "Civil Engineering", ""));
        list.add(new BranchModel(R.drawable.computer, "Computer Engineering", ""));
        list.add(new BranchModel(R.drawable.electricity, "Electrical Engineering", ""));
        list.add(new BranchModel(R.drawable.mechanical, "Mechanical Engineering", ""));

        adapter = new BranchAdapter(getContext(), list);

        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/qrem-system-2f8ed.appspot.com/o/2019-04-24.jpg?alt=media&token=ffbfb930-d63b-4d16-8289-32c897f0f38c&_gl=1*1l6ro8z*_ga*MjAyODQxMDQzNy4xNjc4NDU5MTU5*_ga_CW55HF8NVT*MTY4NTg2MjEzOC4xMy4xLjE2ODU4NjIzMDcuMC4wLjA.").into(imageView);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
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