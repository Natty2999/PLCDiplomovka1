package com.example.myapplication.plcdiplomovka1;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

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
    private Button buttonInputs;
    private Button buttonOutputs;
    private Button buttonTest;
    private TextView textViewSnimacL;
    private TextView textViewSnimacP;
    private TextInputLayout layoutSnimacLOffset;
    private TextInputLayout layoutSnimacPOffset;
    private TextInputLayout layoutSnimacLBit;
    private TextInputLayout layoutSnimacPBit;
    private ConstraintLayout layoutInputs;
    private ConstraintLayout layoutOutputs;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testing, container, false);
        //buttonLeft = view.findViewById(R.id.buttonLeft);
        //buttonRight = view.findViewById(R.id.buttonRight);
        //Piest = view.findViewById(R.id.imagePiesttest);
        buttonInputs = view.findViewById(R.id.btn_show_inputs);
        buttonOutputs = view.findViewById(R.id.btn_show_outputs);
        layoutInputs = view.findViewById(R.id.constraint_layout_inputs);
        layoutOutputs = view.findViewById(R.id.constraint_layout_outputs);





        layoutInputs.setVisibility(View.GONE);
        buttonInputs.setText(R.string.action_show);
        buttonInputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);

        layoutOutputs.setVisibility(View.GONE);
        buttonOutputs.setText(R.string.action_show);
        buttonOutputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);

        //when button is clicked

        /*buttonInputs.setOnClickListener(v -> {

        if (listTextViewInputs.get(0).getVisibility()==View.GONE){
            buttonInputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_24, 0);
            buttonInputs.setText(R.string.schovat);
            for (int i = 0; i < 2; i++) {
                listTextViewInputs.get(i).setVisibility(View.VISIBLE);
                listTextInputLayoutOffsetsInputs.get(i).setVisibility(View.VISIBLE);
                listTextInputLayoutBitsInputs.get(i).setVisibility(View.VISIBLE);
            }
        }
        else {
            buttonInputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);
            buttonInputs.setText(R.string.zobrazit);
            for (int i = 0; i < 2; i++) {
                listTextViewInputs.get(i).setVisibility(View.GONE);
                listTextInputLayoutOffsetsInputs.get(i).setVisibility(View.GONE);
                listTextInputLayoutBitsInputs.get(i).setVisibility(View.GONE);
            }
        }
        });*/
        buttonInputs.setOnClickListener(v -> {
            if (layoutInputs.getVisibility() == View.VISIBLE) {
                buttonInputs.setText(R.string.action_show);
                buttonInputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);
                layoutInputs.setVisibility(View.GONE);
            } else {
                layoutInputs.setVisibility(View.VISIBLE);
                buttonInputs.setText(R.string.action_hide);
                buttonInputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_24, 0);
            }
        });

        buttonOutputs.setOnClickListener(v -> {

            if (layoutOutputs.getVisibility() == View.VISIBLE) {
                buttonOutputs.setText(R.string.action_show);
                buttonOutputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);
                layoutOutputs.setVisibility(View.GONE);
            } else {
                layoutOutputs.setVisibility(View.VISIBLE);
                buttonOutputs.setText(R.string.action_hide);
                buttonOutputs.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_24, 0);
            }
        });



        //buttonOutputs same as buttonInputs

        return view;

    }

}