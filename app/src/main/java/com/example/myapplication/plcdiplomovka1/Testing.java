package com.example.myapplication.plcdiplomovka1;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import Moka7.*;
import com.google.android.material.materialswitch.MaterialSwitch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Testing extends Fragment {
    public Testing() {
        // Required empty public constructor
    }
    public static Testing newInstance(String param1, String param2) {
        Testing fragment = new Testing();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_testing, container, false);
        return view;
    }
}