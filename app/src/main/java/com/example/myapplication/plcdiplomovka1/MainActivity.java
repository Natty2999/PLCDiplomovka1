package com.example.myapplication.plcdiplomovka1;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.content.res.Configuration;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

//TODO pridat Toasty na upozornenie prebehnutia deju
//TODO pridat kontrolu ci je IP adresa spravne zadaná (spravny format) - DONE
//TODO pridat kontrolu ci je DBNumber, DBOffset a DBBit spravne zadané (čísla), bit moze byt 0-7- DONE
//TODO pridat kontrolu ci je WriteValue spravne zadaný (true, false, 0, 1) - DONE
//TODO pozriet ako sa pridavaju dalsie View do aplikacie - DONE
//TODO pridat uchovanie fungujucich IP adries s moznostou pomenovania a vyberu z listu

public class MainActivity extends AppCompatActivity {

    Button buttonTest;
    BottomNavigationView bottomNavigationView;
    TabLayout tabLayoutSettings;
    static FloatingActionButton buttonMenu;
    FrameLayout frameLayoutDetail;
    DefaultBitEditing defaultBitEditing = new DefaultBitEditing();
    Testing testingFragment = new Testing();
    VytahFragment vytahFragment = new VytahFragment();
    RamenoFragment ramenoFragment = new RamenoFragment();

    RamenoDetZasobnikFragment ramenoDetZasobnikFragment = new RamenoDetZasobnikFragment();
    RamenoDetRamenoFragment ramenoDetRamenoFragment = new RamenoDetRamenoFragment();

    SettingsVytahFragment settingsVytahFragment = new SettingsVytahFragment();
    SettingsRamenoFragment settingsRamenoFragment = new SettingsRamenoFragment();
    VytahDetVytahFragment vytahDetVytahFragment = new VytahDetVytahFragment();
    VytahDetPiestFragment vytahDetPiestFragment = new VytahDetPiestFragment();
    public Spinner spinner;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DynamicColors.applyToActivityIfAvailable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        buttonMenu = findViewById(R.id.floatingActionButton);
        tabLayoutSettings = findViewById(R.id.tabLayoutSettings);
        frameLayoutDetail = findViewById(R.id.frameLayoutDetail);
        spinner = findViewById(R.id.spinnerRamenoDetail);
        spinner.setVisibility(View.GONE);
        int color = getColor(R.color.material_dark);
        // if dark mode is enabled, set the color of the spinner to white
        if (isDarkModeOn()) {
            //set background drawable of frameLayoutDetail to layout_bg_dark.xml
            frameLayoutDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.layout_bg_dark));
        }else {
            //set background drawable of frameLayoutDetail to layout_bg.xml
            frameLayoutDetail.setBackground(ContextCompat.getDrawable(this, R.drawable.layout_bg));
        }



        if (buttonTest != null) {
            buttonTest.setOnClickListener(v -> {
                if (ramenoDetZasobnikFragment == null) {
                    ramenoDetZasobnikFragment = new RamenoDetZasobnikFragment();
                } else {
                    tabLayoutSettings.setVisibility(TabLayout.GONE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ramenoDetZasobnikFragment).addToBackStack(null).commit();

                }
            });
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).addToBackStack(null).commit();
            tabLayoutSettings.setVisibility(TabLayout.GONE);
        }
        if (buttonMenu != null && bottomNavigationView != null) {
            buttonMenu.setOnClickListener(v -> {
                toggleMenuVisibility();
            });
        }

        if (bottomNavigationView != null) {
            bottomNavigationView.setOnItemSelectedListener(item -> {
                //if spinner is opened, close it

                changeFragment(item);
                return true;
            });
        }

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

        //set default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, defaultBitEditing).addToBackStack(null).commit();
        setButtonDetailContent();


    }
    public boolean isDarkModeOn() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_YES:
                return true;
            case Configuration.UI_MODE_NIGHT_NO:
                return false;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return false;
            default:
                return false;
        }
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
                //defaultBitEditing = new Testing();
            }
            selectedFragment = defaultBitEditing;
            spinner.setVisibility(View.GONE);
        } else if (itemId == R.id.nav_settings) {
            if (tabLayoutSettings.getSelectedTabPosition() == 0) {
                if (settingsVytahFragment == null) {
                    settingsVytahFragment = new SettingsVytahFragment();
                }
                selectedFragment = settingsVytahFragment;
                spinner.setVisibility(View.GONE);
            } else if (tabLayoutSettings.getSelectedTabPosition() == 1) {
                if (settingsRamenoFragment == null) {
                    settingsRamenoFragment = new SettingsRamenoFragment();
                }
                selectedFragment = settingsRamenoFragment;
                spinner.setVisibility(View.GONE);
            }
        } else if (itemId == R.id.nav_vytah) {
            if (vytahFragment == null) {
                vytahFragment = new VytahFragment();

            }
            selectedFragment = vytahFragment;
            spinner.setVisibility(View.VISIBLE);
        }
        else if (itemId == R.id.nav_rameno) {
            if ( ramenoFragment == null) {
                ramenoFragment = new RamenoFragment();
            }
            selectedFragment = ramenoFragment;
            spinner.setVisibility(View.VISIBLE);
        }
        if (selectedFragment != null) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
            if (selectedFragment != currentFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, selectedFragment).addToBackStack(null).commit();

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = null;
                // get current active fragment

                if (selectedFragment == vytahFragment) {
                    adapter = ArrayAdapter.createFromResource(this, R.array.elevator_detail, android.R.layout.simple_spinner_item);
                } else if (selectedFragment == ramenoFragment) {
                    adapter = ArrayAdapter.createFromResource(this, R.array.rameno_detail, android.R.layout.simple_spinner_item);
                }
                //Toast.makeText(this, "Current fragment: " + currentFragment, Toast.LENGTH_SHORT).show();
                if (adapter != null) {
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);
                }
                // Specify the layout to use when the list of choices appears
            }

        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DynamicColors.applyToActivitiesIfAvailable(this.getApplication());
        changeTablayoutVisibility();
    }
    // on back button pressed

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                    changeTablayoutVisibility();

                } else {
                    setEnabled(false);
                    MainActivity.super.onBackPressed();
                    finish();
                }
            }
        });
    }
    public void setButtonDetailContent(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (currentFragment == null) {
                    return;
                }else if (currentFragment == vytahFragment|| currentFragment == vytahDetVytahFragment || currentFragment == vytahDetPiestFragment) {
                    if(id == 1){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, vytahDetVytahFragment).addToBackStack(null).commit();
                    }else {
                        if(id == 2){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, vytahDetPiestFragment).addToBackStack(null).commit();
                        }
                    }

                }else if (currentFragment == ramenoFragment || currentFragment == ramenoDetRamenoFragment || currentFragment == ramenoDetZasobnikFragment) {
                    //rameno
                    if(id == 1){
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ramenoDetRamenoFragment).addToBackStack(null).commit();
                    }else {
                        if(id == 2){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ramenoDetZasobnikFragment).addToBackStack(null).commit();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Interface callback
            }
        });
    }
    public void toggleMenuVisibility(){
        if (bottomNavigationView.getVisibility() == View.VISIBLE) {
            bottomNavigationView.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            buttonMenu.setImageResource(R.drawable.baseline_arrow_forward_ios_24);
        } else {
            bottomNavigationView.setVisibility(View.VISIBLE);
            if (bottomNavigationView.getSelectedItemId() != R.id.nav_home && bottomNavigationView.getSelectedItemId() != R.id.nav_settings) {
                spinner.setVisibility(View.VISIBLE);
            } else {
                spinner.setVisibility(View.GONE);
            }
            buttonMenu.setImageResource(R.drawable.baseline_arrow_back_ios_24);
        }
    }
    public void hideMenu(){
        bottomNavigationView.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        buttonMenu.setImageResource(R.drawable.baseline_arrow_forward_ios_24);
    }

}