package com.example.myapplication.plcdiplomovka1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class VytahActivity extends AppCompatActivity {
    ImageView imageViewDrahaVzduch;
    ImageView imageViewVytahHore;
    ImageView imageViewVytahDole;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vytah);
        Button buttonDraha = findViewById(R.id.buttonDraha);
        imageViewDrahaVzduch = findViewById(R.id.imageViewDrahaVzduch);
        imageViewVytahHore = findViewById(R.id.imageViewVytahHore);
        imageViewVytahDole = findViewById(R.id.imageViewVytahDole);
        int falseRed = getResources().getColor(R.color.falseRed, getTheme());
        int trueGreen = getResources().getColor(R.color.trueGreen, getTheme());
        imageViewDrahaVzduch.setColorFilter(falseRed);
        //while button is held down change color of imageview2 to green, when let go change back to red
        buttonDraha.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                // use trueGreen from colors.xml
                imageViewDrahaVzduch.setColorFilter(trueGreen);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                // use falseRed from colors.xml
                imageViewDrahaVzduch.setColorFilter(falseRed);
            }
            return true;
        });

    }
}