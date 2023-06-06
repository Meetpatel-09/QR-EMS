package com.example.myapplication.students.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;

public class ProfileFragment extends Fragment {

    private RecyclerView recyclerViewEvents;
    private Button btnEditProfile, btnVolunteer;
    private ImageView imgProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgProfile = view.findViewById(R.id.my_profile_img);
        btnEditProfile = view.findViewById(R.id.btn_edit_profile);
        btnVolunteer = view.findViewById(R.id.btn_volunteer);




        return view;
    }
}