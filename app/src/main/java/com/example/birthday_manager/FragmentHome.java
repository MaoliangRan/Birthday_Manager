package com.example.birthday_manager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentHome extends Fragment implements OnClickListener {

    private LinearLayout img1;
    private LinearLayout img2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img1 = (LinearLayout) view.findViewById(R.id.essay1);
        img2 = (LinearLayout) view.findViewById(R.id.essay2);
        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.essay1:
                mainActivity.creatwebview("essay1");
                break;
            case R.id.essay2:
                mainActivity.creatwebview("essay2");
                break;
        }
    }
}

