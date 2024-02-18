package com.example.myapplication.plcdiplomovka1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.CountDownTimer;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.navigation.NavigationBarView;

import Moka7.*;
//TODO pridat Toasty na upozornenie prebehnutia deju
//TODO pridat kontrolu ci je IP adresa spravne zadaná (spravny format)
//TODO pridat kontrolu ci je DBNumber, DBOffset a DBBit spravne zadané (čísla), bit moze byt 0-7
//TODO pridat kontrolu ci je WriteValue spravne zadaný (true, false, 0, 1) !done
//TODO pozriet ako sa pridavaju dalsie View do aplikacie
//TODO pridat uchovanie fungujucich IP adries s moznostou pomenovania a vyberu z listu

public class MainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    DefaultBitEditing defaultBitEditing = new DefaultBitEditing();
    Testing testing = new Testing();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //how to set default fragment
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected( MenuItem item){
           int itemId = item.getItemId();
            if(itemId == R.id.nav_home){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).commit();
            }else if(itemId == R.id.nav_settings){
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, testing).commit();
            }
            return true;
        }
        });

    }
}