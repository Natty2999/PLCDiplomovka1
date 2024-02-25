package com.example.myapplication.plcdiplomovka1;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

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
    private Button buttonLeft;
    private Button buttonRight;

    private ImageView Piest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testing, container, false);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        buttonRight = view.findViewById(R.id.buttonRight);
        Piest = view.findViewById(R.id.imagePiesttest);
        //when button is clicked
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current layout parameters
                final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) Piest.getLayoutParams();

                // Create a ValueAnimator that animates from the current end margin to 0
                ValueAnimator animator = ValueAnimator.ofInt(params.getMarginEnd(), 320);
                animator.addUpdateListener(animation -> {
                    // Update the end margin in the layout parameters
                    params.setMarginEnd((Integer) animation.getAnimatedValue());
                    Piest.setLayoutParams(params);
                    Piest.getParent().requestLayout();
                });

                // Set the duration of the animation
                animator.setDuration(300);

                // Start the animation
                animator.start();
            }
        });
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current layout parameters
                final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) Piest.getLayoutParams();

                // Create a ValueAnimator that animates from the current end margin to 0
                ValueAnimator animator = ValueAnimator.ofInt(params.getMarginEnd(), 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        // Update the end margin in the layout parameters
                        params.setMarginEnd((Integer) animation.getAnimatedValue());
                        Piest.setLayoutParams(params);
                        Piest.getParent().requestLayout();
                    }
                });

                // Set the duration of the animation
                animator.setDuration(300);

                // Start the animation
                animator.start();
            }
        });
        return view;

    }
}