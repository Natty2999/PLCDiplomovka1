package com.example.myapplication.plcdiplomovka1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.google.android.material.tabs.TabLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
//TODO pridat Toasty na upozornenie prebehnutia deju
//TODO pridat kontrolu ci je IP adresa spravne zadaná (spravny format)
//TODO pridat kontrolu ci je DBNumber, DBOffset a DBBit spravne zadané (čísla), bit moze byt 0-7
//TODO pridat kontrolu ci je WriteValue spravne zadaný (true, false, 0, 1) !done
//TODO pozriet ako sa pridavaju dalsie View do aplikacie
//TODO pridat uchovanie fungujucich IP adries s moznostou pomenovania a vyberu z listu

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayoutSettings;

    DefaultBitEditing defaultBitEditing = new DefaultBitEditing();
    VytahFragment vytahFragment = new VytahFragment();

    SettingsVytahFragment settingsVytahFragment = new SettingsVytahFragment();
    SettingsRamenoFragment settingsRamenoFragment = new SettingsRamenoFragment();
    Testing testing = new Testing();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        tabLayoutSettings = findViewById(R.id.tabLayoutSettings);
        //how to set default fragment
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected( MenuItem item){
           int itemId = item.getItemId();
            if(itemId == R.id.nav_home){
                tabLayoutSettings.setVisibility(TabLayout.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).commit();
            }else if(itemId == R.id.nav_settings){

                if(tabLayoutSettings.getSelectedTabPosition() == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, settingsVytahFragment).commit();
                }else if(tabLayoutSettings.getSelectedTabPosition() == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, settingsRamenoFragment).commit();
                }
                tabLayoutSettings.setVisibility(TabLayout.VISIBLE);

            }else if(itemId == R.id.nav_vytah){
                tabLayoutSettings.setVisibility(TabLayout.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, vytahFragment).commit();
            }
            return true;
        }
        });
        tabLayoutSettings.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // This method is called when a tab is selected
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, settingsVytahFragment).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, settingsRamenoFragment).commit();
                        break;
                    // Add more cases if you have more tabs
                }
            }

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
}