package com.example.gb_notes.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gb_notes.R;


public class SettingsFragment extends Fragment {
    private final String TOAST = "Orientation lock";

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        initSwitchDarkMode(view);
        initSwitchOrientationLock(view);
    }

    private void initSwitchDarkMode(View view) {
        SwitchCompat switchDarkMode = view.findViewById(R.id.switchNightMode);
        switchDarkMode.setOnClickListener(v -> {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }


    private void initSwitchOrientationLock(View view) {
        SwitchCompat switchOrientationLock = view.findViewById(R.id.switchOrientationLock);
        switchOrientationLock.setOnClickListener(v -> Toast.makeText(getActivity(), TOAST, Toast.LENGTH_SHORT).show());
    }


}
