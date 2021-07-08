package com.example.gb_notes.ui;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.gb_notes.MainActivity;
import com.example.gb_notes.R;
import com.example.gb_notes.observer.ActionPublisher;

public class DialogNoteFragment extends DialogFragment {

    private ActionPublisher actPublisher;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, null);

        initButtons(view);

        return view;
    }

    private void initButtons(View view) {
        Button buttonYes = view.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(v -> {
            actPublisher.notifySingle(true);
            dismiss();
        });

        Button buttonNo = view.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(v -> {
            actPublisher.notifySingle(false);
            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        MainActivity activity = (MainActivity) context;
        actPublisher = activity.getActPublisher();
        super.onAttach(context);
    }

    @Override
    public void dismiss() {
        actPublisher = null;
        super.dismiss();
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }
}
