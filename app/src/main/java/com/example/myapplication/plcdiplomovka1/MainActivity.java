package com.example.myapplication.plcdiplomovka1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.tabs.TabLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//TODO pridat Toasty na upozornenie prebehnutia deju
//TODO pridat kontrolu ci je IP adresa spravne zadaná (spravny format)
//TODO pridat kontrolu ci je DBNumber, DBOffset a DBBit spravne zadané (čísla), bit moze byt 0-7
//TODO pridat kontrolu ci je WriteValue spravne zadaný (true, false, 0, 1) !done
//TODO pozriet ako sa pridavaju dalsie View do aplikacie
//TODO pridat uchovanie fungujucich IP adries s moznostou pomenovania a vyberu z listu

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TabLayout tabLayoutSettings;

    DefaultBitEditing defaultBitEditing = new DefaultBitEditing();
    VytahFragment vytahFragment = new VytahFragment();

    SettingsVytahFragment settingsVytahFragment = new SettingsVytahFragment();
    SettingsRamenoFragment settingsRamenoFragment = new SettingsRamenoFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        tabLayoutSettings = findViewById(R.id.tabLayoutSettings);
        //how to set default fragment
        // Set the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).addToBackStack(null).commit();
            tabLayoutSettings.setVisibility(TabLayout.GONE);
        }


        bottomNavigationView.setOnItemSelectedListener(item -> {
            changeFragment(item);
            return true;
        });

        // PRIDANE kvôli zobrazeniu tabLayoutu pri zobrazení fragmentu, inak by sa zobrazoval tablaout skor ako sa zobrazil fragment
        // dava to potom zly vzhlad, taky sekany
        getSupportFragmentManager().addOnBackStackChangedListener(this::changeTablayoutVisibility);
        tabLayoutSettings.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // This method is called when a tab is selected
                Fragment selectedFragment = null;

                if (tab.getPosition() == 0) {
                    if (settingsVytahFragment == null) {
                        settingsVytahFragment = new SettingsVytahFragment();
                    }
                    selectedFragment = settingsVytahFragment;
                } else if (tab.getPosition() == 1) {
                    if (settingsRamenoFragment == null) {
                        settingsRamenoFragment = new SettingsRamenoFragment();
                    }
                    selectedFragment = settingsRamenoFragment;
                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).addToBackStack(null).commit();
                }
                    // Add more cases if you have more tabs
                }

            //onlayout change to lanscape and back to portrait


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // This method is called when a tab is unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // This method is called when a tab is reselected
            }
        });


    }
    public void changeTablayoutVisibility() {
        if (settingsVytahFragment.isAdded() && settingsVytahFragment.isVisible()) {
            tabLayoutSettings.setVisibility(TabLayout.VISIBLE);
        } else if (settingsRamenoFragment.isAdded() && settingsRamenoFragment.isVisible()) {
            tabLayoutSettings.setVisibility(TabLayout.VISIBLE);
        } else {
            tabLayoutSettings.setVisibility(TabLayout.GONE);
        }
    }
    public void changeFragment(MenuItem item){
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            if (defaultBitEditing == null) {
                defaultBitEditing = new DefaultBitEditing();
            }
            selectedFragment = defaultBitEditing;
        } else if (itemId == R.id.nav_settings) {
            if (tabLayoutSettings.getSelectedTabPosition() == 0) {
                if (settingsVytahFragment == null) {
                    settingsVytahFragment = new SettingsVytahFragment();
                }
                selectedFragment = settingsVytahFragment;
            } else if (tabLayoutSettings.getSelectedTabPosition() == 1) {
                if (settingsRamenoFragment == null) {
                    settingsRamenoFragment = new SettingsRamenoFragment();
                }
                selectedFragment = settingsRamenoFragment;
            }
        } else if (itemId == R.id.nav_vytah) {
            if (vytahFragment == null) {
                vytahFragment = new VytahFragment();
            }
            selectedFragment = vytahFragment;
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).addToBackStack(null).commit();
        }
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeTablayoutVisibility();
    }
}