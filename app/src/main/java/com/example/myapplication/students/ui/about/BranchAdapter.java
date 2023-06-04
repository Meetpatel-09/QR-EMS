package com.example.myapplication.students.ui.about;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BranchAdapter extends PagerAdapter {

    private Context context;
    private List<BranchModel> list;

    public BranchAdapter(Context context, List<BranchModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.branch_item_layout, container, false);

        ImageView branchIcon;
        TextView branchName, branchDesc;

        branchIcon = view.findViewById(R.id.branch_icon);
        branchName = view.findViewById(R.id.branch_title);
        branchDesc = view.findViewById(R.id.branch_desc);

        branchIcon.setImageResource(list.get(position).getImg());
        branchName.setText(list.get(position).getTitle());
        branchDesc.setText(list.get(position).getDescription());

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
}
