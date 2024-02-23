package com.example.myapplication.plcdiplomovka1;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsVytahFragment extends Fragment {
    //text inputy
    private TextInputEditText vytahIPAdresa;
    private TextInputEditText snimace_DBNumber;
    //TODO pridat rameno cez Q137.... do programu Tiaportalu aby vedel vytah ci moze ist hore
    //input Ofsets
    private TextInputEditText snimac_L_DBOffset;
    private TextInputEditText snimac_P_DBOffset;
    private TextInputEditText snimac_H_DBOffset;
    private TextInputEditText snimac_Vytah_H_DBOffset;
    private TextInputEditText snimac_Vytah_D_DBOffset;
    private TextInputEditText snimac_Rameno_DBoffset;
    private TextInputEditText snimac_Piest_DBOffset;
    //input bits
    private TextInputEditText snimac_L_DBBit;
    private TextInputEditText snimac_P_DBBit;
    private TextInputEditText snimac_H_DBBit;
    private TextInputEditText snimac_Vytah_H_DBBit;
    private TextInputEditText snimac_Vytah_D_DBBit;
    private TextInputEditText snimac_Rameno_DBBit;
    private TextInputEditText snimac_Piest_DBBit;

    //text outputy

    //output bits
    private TextInputEditText vystup_Draha_DBBit;
    private TextInputEditText vystup_Piest_DBBit;
    private TextInputEditText vystup_Vytah_H_DBBit;
    private TextInputEditText vystup_Vytah_D_DBBit;
    private TextInputEditText vystup_Manual_DBBit;
    //output offsets
    private TextInputEditText vystup_Draha_DBOffset;
    private TextInputEditText vystup_Piest_DBOffset;
    private TextInputEditText vystup_Vytah_H_DBOffset;
    private TextInputEditText vystup_Vytah_D_DBOffset;
    private TextInputEditText vystup_Manual_DBOffset;

    //shared preferences
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String IP_ADRESA_VYTAH = "ipAdresaVytah";
    private static final String SNIMACE_DBNUMBER = "snimaceDBNumber";
    //input Ofsets
    private static final String SNIMAC_L_DBOFFSET = "snimac_L_DBOffset";
    private static final String SNIMAC_P_DBOFFSET = "snimac_P_DBOffset";
    private static final String SNIMAC_H_DBOFFSET = "snimac_H_DBOffset";
    private static final String SNIMAC_VYTAH_H_DBOFFSET = "snimac_Vytah_H_DBOffset";
    private static final String SNIMAC_VYTAH_D_DBOFFSET = "snimac_Vytah_D_DBOffset";
    private static final String SNIMAC_RAMENO_DBOFFSET = "snimac_Rameno_DBOffset";
    private static final String SNIMAC_PIEST_DBOFFSET = "snimac_Piest_DBOffset";
    //input Bits
    private static final String SNIMAC_L_DBBIT = "snimac_L_DBBit";
    private static final String SNIMAC_P_DBBIT = "snimac_P_DBBit";
    private static final String SNIMAC_H_DBBIT = "snimac_H_DBBit";
    private static final String SNIMAC_VYTAH_H_DBBIT = "snimac_Vytah_H_DBBit";
    private static final String SNIMAC_VYTAH_D_DBBIT = "snimac_Vytah_D_DBBit";
    private static final String SNIMAC_RAMENO_DBBIT = "snimac_Rameno_DBBit";
    private static final String SNIMAC_PIEST_DBBIT = "snimac_Piest_DBBit";

    //output Ofsets
    private static final String VYSTUP_DRAHA_DBOFFSET = "vystup_Draha_DBOffset";
    private static final String VYSTUP_PIEST_DBOFFSET = "vystup_Piest_DBOffset";
    private static final String VYSTUP_VYTAH_H_DBOFFSET = "vystup_Vytah_H_DBOffset";
    private static final String VYSTUP_VYTAH_D_DBOFFSET = "vystup_Vytah_D_DBOffset";
    private static final String VYSTUP_MANUAL_DBOFFSET = "vystup_Manual_DBOffset";

    //output Bits
    private static final String VYSTUP_DRAHA_DBBIT = "vystup_Draha_DBBit";
    private static final String VYSTUP_PIEST_DBBIT = "vystup_Piest_DBBit";
    private static final String VYSTUP_VYTAH_H_DBBIT = "vystup_Vytah_H_DBBit";
    private static final String VYSTUP_VYTAH_D_DBBIT = "vystup_Vytah_D_DBBit";
    private static final String VYSTUP_MANUAL_DBBIT = "vystup_Manual_DBBit";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings_vytah, container, false);

        //TODO pridat Toasty na upozornenie prebehnutia deju
        //TODO pridat ukladanie do pamate telefou pomocou SharedPreferences
        List<TextInputEditText> textInputEditTextsAll = new ArrayList<>();
        List<TextInputEditText> textInputEditTextsOffsets = new ArrayList<>();
        List<TextInputEditText> textInputEditTextsBits = new ArrayList<>();

        //TODO pridat vsetky TextInputy do listu textInputEditTextsAll
        //TODO pridat vsetky offsety do listu textInputEditTextsOffsets
        //TODO pridat vsetky bity do listu textInputEditTextsBits
        vytahIPAdresa = view.findViewById(R.id.VytahIPAdresa);
        snimace_DBNumber = view.findViewById(R.id.Snimace_DBNumber);
        // Snimac
        // Offsety
        snimac_L_DBOffset = view.findViewById(R.id.Snimac_L_DBOffset);
        snimac_P_DBOffset = view.findViewById(R.id.Snimac_P_DBOffset);
        snimac_H_DBOffset = view.findViewById(R.id.Snimac_H_DBOffset);
        snimac_Vytah_H_DBOffset = view.findViewById(R.id.Snimac_Vytah_H_DBOffset);
        snimac_Vytah_D_DBOffset = view.findViewById(R.id.Snimac_Vytah_D_DBOffset);
        snimac_Rameno_DBoffset = view.findViewById(R.id.Snimac_Rameno_DBOffset);
        snimac_Piest_DBOffset = view.findViewById(R.id.Snimac_Piest_DBOffset);
        // Bity
        snimac_L_DBBit = view.findViewById(R.id.Snimac_L_DBBit);
        snimac_P_DBBit = view.findViewById(R.id.Snimac_P_DBBit);
        snimac_H_DBBit = view.findViewById(R.id.Snimac_H_DBBit);
        snimac_Vytah_H_DBBit = view.findViewById(R.id.Snimac_Vytah_H_DBBit);
        snimac_Vytah_D_DBBit = view.findViewById(R.id.Snimac_Vytah_D_DBBit);
        snimac_Rameno_DBBit = view.findViewById(R.id.Snimac_Rameno_DBBit);
        snimac_Piest_DBBit = view.findViewById(R.id.Snimac_Piest_DBBit);
        // Vystup
        // Offsety
        vystup_Draha_DBOffset = view.findViewById(R.id.Vystup_Draha_DBOffset);
        vystup_Piest_DBOffset = view.findViewById(R.id.Vystup_Piest_DBOffset);
        vystup_Vytah_H_DBOffset = view.findViewById(R.id.Vystup_Vytah_H_DBOffset);
        vystup_Vytah_D_DBOffset = view.findViewById(R.id.Vystup_Vytah_D_DBOffset);
        vystup_Manual_DBOffset = view.findViewById(R.id.Vystup_Manual_DBOffset);
        // Bity
        vystup_Draha_DBBit = view.findViewById(R.id.Vystup_Draha_DBBit);
        vystup_Piest_DBBit = view.findViewById(R.id.Vystup_Piest_DBBit);
        vystup_Vytah_H_DBBit = view.findViewById(R.id.Vystup_Vytah_H_DBBit);
        vystup_Vytah_D_DBBit = view.findViewById(R.id.Vystup_Vytah_D_DBBit);
        vystup_Manual_DBBit = view.findViewById(R.id.Vystup_Manual_DBBit);

        //pridaj do listu textInputEditTextsAll
        // Snimac
        // Offsety
        textInputEditTextsAll.add(snimac_L_DBOffset);
        textInputEditTextsAll.add(snimac_P_DBOffset);
        textInputEditTextsAll.add(snimac_H_DBOffset);
        textInputEditTextsAll.add(snimac_Vytah_H_DBOffset);
        textInputEditTextsAll.add(snimac_Vytah_D_DBOffset);
        textInputEditTextsAll.add(snimac_Rameno_DBoffset);
        textInputEditTextsAll.add(snimac_Piest_DBOffset);
        // Bity
        textInputEditTextsAll.add(snimac_L_DBBit);
        textInputEditTextsAll.add(snimac_P_DBBit);
        textInputEditTextsAll.add(snimac_H_DBBit);
        textInputEditTextsAll.add(snimac_Vytah_H_DBBit);
        textInputEditTextsAll.add(snimac_Vytah_D_DBBit);
        textInputEditTextsAll.add(snimac_Rameno_DBBit);
        textInputEditTextsAll.add(snimac_Piest_DBBit);
        // Vystup
        // Offsety
        textInputEditTextsAll.add(vystup_Draha_DBOffset);
        textInputEditTextsAll.add(vystup_Piest_DBOffset);
        textInputEditTextsAll.add(vystup_Vytah_H_DBOffset);
        textInputEditTextsAll.add(vystup_Vytah_D_DBOffset);
        textInputEditTextsAll.add(vystup_Manual_DBOffset);
        // Bity
        textInputEditTextsAll.add(vystup_Draha_DBBit);
        textInputEditTextsAll.add(vystup_Piest_DBBit);
        textInputEditTextsAll.add(vystup_Vytah_H_DBBit);
        textInputEditTextsAll.add(vystup_Vytah_D_DBBit);
        textInputEditTextsAll.add(vystup_Manual_DBBit);

        //pridaj do listu textInputEditTextsOffsets
        // Snimac
        textInputEditTextsOffsets.add(snimac_L_DBOffset);
        textInputEditTextsOffsets.add(snimac_P_DBOffset);
        textInputEditTextsOffsets.add(snimac_H_DBOffset);
        textInputEditTextsOffsets.add(snimac_Vytah_H_DBOffset);
        textInputEditTextsOffsets.add(snimac_Vytah_D_DBOffset);
        textInputEditTextsOffsets.add(snimac_Rameno_DBoffset);
        textInputEditTextsOffsets.add(snimac_Piest_DBOffset);
        // Vystup
        textInputEditTextsOffsets.add(vystup_Draha_DBOffset);
        textInputEditTextsOffsets.add(vystup_Piest_DBOffset);
        textInputEditTextsOffsets.add(vystup_Vytah_H_DBOffset);
        textInputEditTextsOffsets.add(vystup_Vytah_D_DBOffset);
        textInputEditTextsOffsets.add(vystup_Manual_DBOffset);

        //pridaj do listu textInputEditTextsBits
        // Snimac
        textInputEditTextsBits.add(snimac_L_DBBit);
        textInputEditTextsBits.add(snimac_P_DBBit);
        textInputEditTextsBits.add(snimac_H_DBBit);
        textInputEditTextsBits.add(snimac_Vytah_H_DBBit);
        textInputEditTextsBits.add(snimac_Vytah_D_DBBit);
        textInputEditTextsBits.add(snimac_Rameno_DBBit);
        textInputEditTextsBits.add(snimac_Piest_DBBit);
        // Vystup
        textInputEditTextsBits.add(vystup_Draha_DBBit);
        textInputEditTextsBits.add(vystup_Piest_DBBit);
        textInputEditTextsBits.add(vystup_Vytah_H_DBBit);
        textInputEditTextsBits.add(vystup_Vytah_D_DBBit);
        textInputEditTextsBits.add(vystup_Manual_DBBit);



        FloatingActionButton saveButton = view.findViewById(R.id.ActionButtonSave);

        saveButton.setOnClickListener(v -> {
            //uloz do sharedpreferences
            String ipAdresaVytah = Objects.requireNonNull(vytahIPAdresa.getText()).toString();
            String snimace_DBNumberStr = Objects.requireNonNull(snimace_DBNumber.getText()).toString();
            //skontroluj ci je v tvare IP adresy
            boolean isIP = ipAdresaVytah.matches("(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
            boolean isDBNumber = snimace_DBNumberStr.matches("\\d{1,3}");
            boolean isEmpty = false;
            boolean isWrongOffset = false;
            boolean isWrongBit = false;
            //skontroluj ci su vsetky polia vyplnene

            //skontroluj ci su vsetky offsety v spravnom formate
            for(TextInputEditText textInputEditText : textInputEditTextsOffsets){
                if(!Objects.requireNonNull(textInputEditText.getText()).toString().matches("\\d{1,3}")){
                    textInputEditText.setError("Zlý formát Offsetu. 0-255");
                    isWrongOffset = true;
                }
            }
            //skontroluj ci su vsetky bity v spravnom formate
            for(TextInputEditText textInputEditText : textInputEditTextsBits){
                if(!Objects.requireNonNull(textInputEditText.getText()).toString().matches("^[0-7]$")){
                    textInputEditText.setError("Zlý formát Bitu. 0-7");
                    isWrongBit = true;
                }
            }
            for(TextInputEditText textInputEditText : textInputEditTextsAll){
                if(Objects.requireNonNull(textInputEditText.getText()).toString().trim().isEmpty()){
                    textInputEditText.setError("Pole je prázdne.");
                    isEmpty = true;
                }
            }

            if (isEmpty){
                Toast.makeText(getActivity(), "Niektoré z polí je prázdne.", Toast.LENGTH_SHORT).show();
            } else if (!isIP) {
                vytahIPAdresa.setError("Zadajte správny formát IP.");
                Toast.makeText(getActivity(), "Zadajte správny formát.", Toast.LENGTH_SHORT).show();
            }else if (isWrongOffset) {
                Toast.makeText(getActivity(), "Zadajte správny formát Offsetu.", Toast.LENGTH_SHORT).show();
            }else if (isWrongBit) {
                Toast.makeText(getActivity(), "Zadajte správny formát Bitu.", Toast.LENGTH_SHORT).show();
            } else if (!isDBNumber) {
                snimace_DBNumber.setError("Zadajte správny formát čisla DB. 0-255");
                Toast.makeText(getActivity(), "Zadajte správny formát.", Toast.LENGTH_SHORT).show();
            } else {
                saveData();
            }
        });

        loadData();
        //updateViews();

        return view;
    }
    public void saveData(){
        //uloz do sharedpreferences
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(IP_ADRESA_VYTAH, Objects.requireNonNull(vytahIPAdresa.getText()).toString());
            editor.putString(SNIMACE_DBNUMBER, Objects.requireNonNull(snimace_DBNumber.getText()).toString());
            // Snimac
            // Offsety
            editor.putString(SNIMAC_L_DBOFFSET, Objects.requireNonNull(snimac_L_DBOffset.getText()).toString());
            editor.putString(SNIMAC_P_DBOFFSET, Objects.requireNonNull(snimac_P_DBOffset.getText()).toString());
            editor.putString(SNIMAC_H_DBOFFSET, Objects.requireNonNull(snimac_H_DBOffset.getText()).toString());
            editor.putString(SNIMAC_VYTAH_H_DBOFFSET, Objects.requireNonNull(snimac_Vytah_H_DBOffset.getText()).toString());
            editor.putString(SNIMAC_VYTAH_D_DBOFFSET, Objects.requireNonNull(snimac_Vytah_D_DBOffset.getText()).toString());
            editor.putString(SNIMAC_RAMENO_DBOFFSET, Objects.requireNonNull(snimac_Rameno_DBoffset.getText()).toString());
            editor.putString(SNIMAC_PIEST_DBOFFSET, Objects.requireNonNull(snimac_Piest_DBOffset.getText()).toString());
            // Bity
            editor.putString(SNIMAC_L_DBBIT, Objects.requireNonNull(snimac_L_DBBit.getText()).toString());
            editor.putString(SNIMAC_P_DBBIT, Objects.requireNonNull(snimac_P_DBBit.getText()).toString());
            editor.putString(SNIMAC_H_DBBIT, Objects.requireNonNull(snimac_H_DBBit.getText()).toString());
            editor.putString(SNIMAC_VYTAH_H_DBBIT, Objects.requireNonNull(snimac_Vytah_H_DBBit.getText()).toString());
            editor.putString(SNIMAC_VYTAH_D_DBBIT, Objects.requireNonNull(snimac_Vytah_D_DBBit.getText()).toString());
            editor.putString(SNIMAC_RAMENO_DBBIT, Objects.requireNonNull(snimac_Rameno_DBBit.getText()).toString());
            editor.putString(SNIMAC_PIEST_DBBIT, Objects.requireNonNull(snimac_Piest_DBBit.getText()).toString());

            // Vystup
            // Offsety
            editor.putString(VYSTUP_DRAHA_DBOFFSET, Objects.requireNonNull(vystup_Draha_DBOffset.getText()).toString());
            editor.putString(VYSTUP_PIEST_DBOFFSET, Objects.requireNonNull(vystup_Piest_DBOffset.getText()).toString());
            editor.putString(VYSTUP_VYTAH_H_DBOFFSET, Objects.requireNonNull(vystup_Vytah_H_DBOffset.getText()).toString());
            editor.putString(VYSTUP_VYTAH_D_DBOFFSET, Objects.requireNonNull(vystup_Vytah_D_DBOffset.getText()).toString());
            editor.putString(VYSTUP_MANUAL_DBOFFSET, Objects.requireNonNull(vystup_Manual_DBOffset.getText()).toString());
            // Bity
            editor.putString(VYSTUP_DRAHA_DBBIT, Objects.requireNonNull(vystup_Draha_DBBit.getText()).toString());
            editor.putString(VYSTUP_PIEST_DBBIT, Objects.requireNonNull(vystup_Piest_DBBit.getText()).toString());
            editor.putString(VYSTUP_VYTAH_H_DBBIT, Objects.requireNonNull(vystup_Vytah_H_DBBit.getText()).toString());
            editor.putString(VYSTUP_VYTAH_D_DBBIT, Objects.requireNonNull(vystup_Vytah_D_DBBit.getText()).toString());
            editor.putString(VYSTUP_MANUAL_DBBIT, Objects.requireNonNull(vystup_Manual_DBBit.getText()).toString());


            editor.apply();

            Toast.makeText(getActivity(), "Uložené!", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadData(){
        if (getActivity() != null){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

            vytahIPAdresa.setText(sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138"));
            snimace_DBNumber.setText(sharedPreferences.getString(SNIMACE_DBNUMBER, "1"));
            // Snimace
            // Offsety
            snimac_L_DBOffset.setText(sharedPreferences.getString(SNIMAC_L_DBOFFSET, "0"));
            snimac_P_DBOffset.setText(sharedPreferences.getString(SNIMAC_P_DBOFFSET, "0"));
            snimac_H_DBOffset.setText(sharedPreferences.getString(SNIMAC_H_DBOFFSET, "0"));
            snimac_Vytah_H_DBOffset.setText(sharedPreferences.getString(SNIMAC_VYTAH_H_DBOFFSET, "0"));
            snimac_Vytah_D_DBOffset.setText(sharedPreferences.getString(SNIMAC_VYTAH_D_DBOFFSET, "0"));
            snimac_Rameno_DBoffset.setText(sharedPreferences.getString(SNIMAC_RAMENO_DBOFFSET, "0"));
            snimac_Piest_DBOffset.setText(sharedPreferences.getString(SNIMAC_PIEST_DBOFFSET, "0"));

            // Bity
            snimac_L_DBBit.setText(sharedPreferences.getString(SNIMAC_L_DBBIT, "0"));
            snimac_P_DBBit.setText(sharedPreferences.getString(SNIMAC_P_DBBIT, "1"));
            snimac_H_DBBit.setText(sharedPreferences.getString(SNIMAC_H_DBBIT, "3"));
            snimac_Vytah_H_DBBit.setText(sharedPreferences.getString(SNIMAC_VYTAH_H_DBBIT, "4"));
            snimac_Vytah_D_DBBit.setText(sharedPreferences.getString(SNIMAC_VYTAH_D_DBBIT, "5"));
            snimac_Rameno_DBBit.setText(sharedPreferences.getString(SNIMAC_RAMENO_DBBIT, "2"));
            snimac_Piest_DBBit.setText(sharedPreferences.getString(SNIMAC_PIEST_DBBIT, "6"));

            // Vystupy
            // Offsety
            vystup_Draha_DBOffset.setText(sharedPreferences.getString(VYSTUP_DRAHA_DBOFFSET, "2"));
            vystup_Piest_DBOffset.setText(sharedPreferences.getString(VYSTUP_PIEST_DBOFFSET, "2"));
            vystup_Vytah_H_DBOffset.setText(sharedPreferences.getString(VYSTUP_VYTAH_H_DBOFFSET, "2"));
            vystup_Vytah_D_DBOffset.setText(sharedPreferences.getString(VYSTUP_VYTAH_D_DBOFFSET, "2"));
            vystup_Manual_DBOffset.setText(sharedPreferences.getString(VYSTUP_MANUAL_DBOFFSET, "2"));
            // Bity
            vystup_Draha_DBBit.setText(sharedPreferences.getString(VYSTUP_DRAHA_DBBIT, "3"));
            vystup_Piest_DBBit.setText(sharedPreferences.getString(VYSTUP_PIEST_DBBIT, "2"));
            vystup_Vytah_H_DBBit.setText(sharedPreferences.getString(VYSTUP_VYTAH_H_DBBIT, "1"));
            vystup_Vytah_D_DBBit.setText(sharedPreferences.getString(VYSTUP_VYTAH_D_DBBIT, "0"));
            vystup_Manual_DBBit.setText(sharedPreferences.getString(VYSTUP_MANUAL_DBBIT, "4"));
        }
    }
}