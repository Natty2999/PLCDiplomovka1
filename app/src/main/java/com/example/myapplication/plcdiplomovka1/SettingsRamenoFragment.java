package com.example.myapplication.plcdiplomovka1;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsRamenoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsRamenoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button buttonInputs;
    private Button buttonOutputs;

    private FloatingActionButton saveButton;

    //inputs
    private TextInputEditText et_ip_adresa_rameno;
    private TextInputEditText et_snimace_db_number_rameno;
    //offset

    private TextInputEditText et_snimac_prisiaty_dboffset;
    private TextInputEditText et_snimac_dom_poloha_dboffset;
    private TextInputEditText et_snimac_vys_extruder_dboffset;
    private TextInputEditText et_snimac_prazdny_zasobnik_dboffset;
    private TextInputEditText et_snimac_rameno_pri_z_dboffset;
    private TextInputEditText et_snimac_rameno_pri_v_dboffset;
    private TextInputEditText et_snimac_suc_na_v_dboffset;
    private TextInputEditText et_snimac_rameno_vytah_dole_dboffset;

    //bit
    private TextInputEditText et_snimac_prisiaty_dbbit;
    private TextInputEditText et_snimac_dom_poloha_dbbit;
    private TextInputEditText et_snimac_vys_extruder_dbbit;
    private TextInputEditText et_snimac_prazdny_zasobnik_dbbit;
    private TextInputEditText et_snimac_rameno_pri_z_dbbit;
    private TextInputEditText et_snimac_rameno_pri_v_dbbit;
    private TextInputEditText et_snimac_suc_na_v_dbbit;
    private TextInputEditText et_snimac_r_vytah_dole_dbbit;


    //outputs
    //offset
    private TextInputEditText et_vystup_extruder_dboffset;
    private TextInputEditText et_vystup_rameno_kz_dboffset;
    private TextInputEditText et_vystup_rameno_kv_dboffset;
    private TextInputEditText et_vystup_odfuk_dboffset;
    private TextInputEditText et_vystup_prisavka_dboffset;
    private TextInputEditText et_vystup_manual_rameno_dboffset;
    private TextInputEditText et_vystup_auto_rameno_dboffset;
    private TextInputEditText et_vystup_polo_automaticky_rameno_dboffset;
    private TextInputEditText et_vystup_start_stop_rameno_dboffset;


    //bit
    private TextInputEditText et_vystup_extruder_dbbit;
    private TextInputEditText et_vystup_rameno_kz_dbbit;
    private TextInputEditText et_vystup_rameno_kv_dbbit;
    private TextInputEditText et_vystup_odfuk_dbbit;
    private TextInputEditText et_vystup_prisavka_dbbit;
    private TextInputEditText et_vystup_manual_rameno_dbbit;
    private TextInputEditText et_vystup_auto_rameno_dbbit;
    private TextInputEditText et_vystup_polo_automaticky_rameno_dbbit;
    private TextInputEditText et_vystup_start_stop_rameno_dbbit;



    private ConstraintLayout layoutInputs;
    private ConstraintLayout layoutOutputs;


    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String IP_ADRESA_RAMENO = "ipAdresaRameno";
    private static final String SNIMACE_DBNUMBER = "snimaceDBNumberRameno";
    //INPUTS
    //offset
    private static final String SNIMAC_PRISIATY_DBOFFSET = "snimacPrisiatyDBOffset";
    private static final String SNIMAC_DOM_POLOHA_DBOFFSET = "snimacDomPolohaDBOffset";
    private static final String SNIMAC_VYS_EXTRUDER_DBOFFSET = "snimacVysExtruderDBOffset";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET = "snimacPrazdnyZasobnikDBOffset";
    private static final String SNIMAC_RAMENO_PRI_Z_DBOFFSET = "snimacRamenoPriZDBOffset";
    private static final String SNIMAC_RAMENO_PRI_V_DBOFFSET = "snimacRamenoPriVDBOffset";
    private static final String SNIMAC_SUC_NA_V_DBOFFSET = "snimacSucNaVDBOffset";
    private static final String SNIMAC_RAMENO_VYTAH_DOLE_DBOFFSET = "snimacRVytahDoleDBOffset";
    //bit
    private static final String SNIMAC_PRISIATY_DBBIT = "snimacPrisiatyDBBit";
    private static final String SNIMAC_DOM_POLOHA_DBBIT = "snimacDomPolohaDBBit";
    private static final String SNIMAC_VYS_EXTRUDER_DBBIT = "snimacVysExtruderDBBit";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBBIT = "snimacPrazdnyZasobnikDBBit";
    private static final String SNIMAC_RAMENO_PRI_Z_DBBIT = "snimacRamenoPriZDBBit";
    private static final String SNIMAC_RAMENO_PRI_V_DBBIT = "snimacRamenoPriVDBBit";
    private static final String SNIMAC_SUC_NA_V_DBBIT = "snimacSucNaVDBBit";
    private static final String SNIMAC_R_VYTAH_DOLE_DBBIT = "snimacRVytahDoleDBBit";

    //OUTPUTS
    //offset
    private static final String VYSTUP_EXTRUDER_DBOFFSET = "vystupExtruderDBOffset";
    private static final String VYSTUP_RAMENO_KZ_DBOFFSET = "vystupRamenoKzDBOffset";
    private static final String VYSTUP_RAMENO_KV_DBOFFSET = "vystupRamenoKvDBOffset";
    private static final String VYSTUP_ODFUK_DBOFFSET = "vystupOdfukDBOffset";
    private static final String VYSTUP_PRISAVKA_DBOFFSET = "vystupPrisavkaDBOffset";
    private static final String VYSTUP_MANUAL_RAMENO_DBOFFSET = "vystupManualRamenoDBOffset";
    private static final String VYSTUP_AUTO_RAMENO_DBOFFSET = "vystupAutoRamenoDBOffset";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET = "vystupPoloAutomatickyRamenoDBOffset";
    private static final String VYSTUP_START_STOP_RAMENO_DBOFFSET = "vystupStartStopRamenoDBOffset";

    //bit
    private static final String VYSTUP_EXTRUDER_DBBIT = "vystupExtruderDBBit";
    private static final String VYSTUP_RAMENO_KZ_DBBIT = "vystupRamenoKzDBBit";
    private static final String VYSTUP_RAMENO_KV_DBBIT = "vystupRamenoKvDBBit";
    private static final String VYSTUP_ODFUK_DBBIT = "vystupOdfukDBBit";
    private static final String VYSTUP_PRISAVKA_DBBIT = "vystupPrisavkaDBBit";
    private static final String VYSTUP_MANUAL_RAMENO_DBBIT = "vystupManualRamenoDBBit";
    private static final String VYSTUP_AUTO_RAMENO_DBBIT = "vystupAutoRamenoDBBit";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT = "vystupPoloAutomatickyRamenoDBBit";
    private static final String VYSTUP_START_STOP_RAMENO_DBBIT = "vystupStartStopRamenoDBBit";






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsRamenoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsRamenoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsRamenoFragment newInstance(String param1, String param2) {
        SettingsRamenoFragment fragment = new SettingsRamenoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //loadData();
        // Get the MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // Get the BottomNavigationView from the MainActivity
            BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
            // Set the selected item
            if (bottomNavigationView != null && bottomNavigationView.getSelectedItemId() != R.id.nav_settings){
                bottomNavigationView.setSelectedItemId(R.id.nav_settings);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_rameno, container, false);

        buttonInputs = view.findViewById(R.id.btn_show_inputs);
        buttonOutputs = view.findViewById(R.id.btn_show_outputs);
        layoutInputs = view.findViewById(R.id.constraint_layout_inputs);
        layoutOutputs = view.findViewById(R.id.constraint_layout_outputs);
        saveButton = view.findViewById(R.id.btn_save);

        List<TextInputEditText> textInputEditTextsAll = new ArrayList<>();
        List<TextInputEditText> textInputEditTextsOffsets = new ArrayList<>();
        List<TextInputEditText> textInputEditTextsBits = new ArrayList<>();
        //INPUTS
        et_ip_adresa_rameno = view.findViewById(R.id.et_ip_adresa_rameno);
        et_snimace_db_number_rameno = view.findViewById(R.id.et_snimace_db_number_rameno);
        //offset
        et_snimac_prisiaty_dboffset = view.findViewById(R.id.et_snimac_prisiaty_dboffset);
        et_snimac_dom_poloha_dboffset = view.findViewById(R.id.et_snimac_dom_poloha_dboffset);
        et_snimac_vys_extruder_dboffset = view.findViewById(R.id.et_snimac_vys_extruder_dboffset);
        et_snimac_prazdny_zasobnik_dboffset = view.findViewById(R.id.et_snimac_prazdny_zasobnik_dboffset);
        et_snimac_rameno_pri_z_dboffset = view.findViewById(R.id.et_snimac_rameno_pri_z_dboffset);
        et_snimac_rameno_pri_v_dboffset = view.findViewById(R.id.et_snimac_rameno_pri_v_dboffset);
        et_snimac_suc_na_v_dboffset = view.findViewById(R.id.et_snimac_suc_na_v_dboffset);
        et_snimac_rameno_vytah_dole_dboffset = view.findViewById(R.id.et_snimac_rameno_vytah_dole_dboffset);

        //bit
        et_snimac_prisiaty_dbbit = view.findViewById(R.id.et_snimac_prisiaty_dbbit);
        et_snimac_dom_poloha_dbbit = view.findViewById(R.id.et_snimac_dom_poloha_dbbit);
        et_snimac_vys_extruder_dbbit = view.findViewById(R.id.et_snimac_vys_extruder_dbbit);
        et_snimac_prazdny_zasobnik_dbbit = view.findViewById(R.id.et_snimac_prazdny_zasobnik_dbbit);
        et_snimac_rameno_pri_z_dbbit = view.findViewById(R.id.et_snimac_rameno_pri_z_dbbit);
        et_snimac_rameno_pri_v_dbbit = view.findViewById(R.id.et_snimac_pri_v_dbbit);
        et_snimac_suc_na_v_dbbit = view.findViewById(R.id.et_snimac_suc_na_v_dbbit);
        et_snimac_r_vytah_dole_dbbit = view.findViewById(R.id.et_snimac_r_vytah_dole_dbbit);

        //OUTPUTS
        //offset
        et_vystup_extruder_dboffset = view.findViewById(R.id.et_vystup_extruder_dboffset);
        et_vystup_rameno_kz_dboffset = view.findViewById(R.id.et_vystup_rameno_kz_dboffset);
        et_vystup_rameno_kv_dboffset = view.findViewById(R.id.et_vystup_rameno_kv_dboffset);
        et_vystup_odfuk_dboffset = view.findViewById(R.id.et_vystup_odfuk_dboffset);
        et_vystup_prisavka_dboffset = view.findViewById(R.id.et_vystup_prisavka_dboffset);
        et_vystup_manual_rameno_dboffset = view.findViewById(R.id.et_vystup_manual_rameno_dboffset);
        et_vystup_auto_rameno_dboffset = view.findViewById(R.id.et_vystup_auto_rameno_dboffset);
        et_vystup_polo_automaticky_rameno_dboffset = view.findViewById(R.id.et_vystup_polo_automaticky_rameno_dboffset);
        et_vystup_start_stop_rameno_dboffset = view.findViewById(R.id.et_vystup_start_rameno_dboffset);


        //bit
        et_vystup_extruder_dbbit = view.findViewById(R.id.et_vystup_extruder_dbbit);
        et_vystup_rameno_kz_dbbit = view.findViewById(R.id.et_vystup_rameno_kz_dbbit);
        et_vystup_rameno_kv_dbbit = view.findViewById(R.id.et_vystup_rameno_kv_dbbit);
        et_vystup_odfuk_dbbit = view.findViewById(R.id.et_vystup_odfuk_dbbit);
        et_vystup_prisavka_dbbit = view.findViewById(R.id.et_vystup_prisavka_dbbit);
        et_vystup_manual_rameno_dbbit = view.findViewById(R.id.et_vystup_manual_rameno_dbbit);
        et_vystup_auto_rameno_dbbit = view.findViewById(R.id.et_vystup_auto_rameno_dbbit);
        et_vystup_polo_automaticky_rameno_dbbit = view.findViewById(R.id.et_vystup_polo_automaticky_dbbit);
        et_vystup_start_stop_rameno_dbbit = view.findViewById(R.id.et_vystup_start_rameno_dbbit);


        //inputs
        //offsets
        textInputEditTextsAll.add(et_snimac_prisiaty_dboffset);
        textInputEditTextsAll.add(et_snimac_dom_poloha_dboffset);
        textInputEditTextsAll.add(et_snimac_vys_extruder_dboffset);
        textInputEditTextsAll.add(et_snimac_prazdny_zasobnik_dboffset);
        textInputEditTextsAll.add(et_snimac_rameno_pri_z_dboffset);
        textInputEditTextsAll.add(et_snimac_rameno_pri_v_dboffset);
        textInputEditTextsAll.add(et_snimac_suc_na_v_dboffset);
        textInputEditTextsAll.add(et_snimac_rameno_vytah_dole_dboffset);
        //bits
        textInputEditTextsAll.add(et_snimac_prisiaty_dbbit);
        textInputEditTextsAll.add(et_snimac_dom_poloha_dbbit);
        textInputEditTextsAll.add(et_snimac_vys_extruder_dbbit);
        textInputEditTextsAll.add(et_snimac_prazdny_zasobnik_dbbit);
        textInputEditTextsAll.add(et_snimac_rameno_pri_z_dbbit);
        textInputEditTextsAll.add(et_snimac_rameno_pri_v_dbbit);
        textInputEditTextsAll.add(et_snimac_suc_na_v_dbbit);
        textInputEditTextsAll.add(et_snimac_r_vytah_dole_dbbit);

        //outputs
        //offsets
        textInputEditTextsAll.add(et_vystup_extruder_dboffset);
        textInputEditTextsAll.add(et_vystup_rameno_kz_dboffset);
        textInputEditTextsAll.add(et_vystup_rameno_kv_dboffset);
        textInputEditTextsAll.add(et_vystup_odfuk_dboffset);
        textInputEditTextsAll.add(et_vystup_prisavka_dboffset);
        textInputEditTextsAll.add(et_vystup_manual_rameno_dboffset);
        textInputEditTextsAll.add(et_vystup_auto_rameno_dboffset);
        textInputEditTextsAll.add(et_vystup_polo_automaticky_rameno_dboffset);
        textInputEditTextsAll.add(et_vystup_start_stop_rameno_dboffset);
        //bits
        textInputEditTextsAll.add(et_vystup_extruder_dbbit);
        textInputEditTextsAll.add(et_vystup_rameno_kz_dbbit);
        textInputEditTextsAll.add(et_vystup_rameno_kv_dbbit);
        textInputEditTextsAll.add(et_vystup_odfuk_dbbit);
        textInputEditTextsAll.add(et_vystup_prisavka_dbbit);
        textInputEditTextsAll.add(et_vystup_manual_rameno_dbbit);
        textInputEditTextsAll.add(et_vystup_auto_rameno_dbbit);
        textInputEditTextsAll.add(et_vystup_polo_automaticky_rameno_dbbit);
        textInputEditTextsAll.add(et_vystup_start_stop_rameno_dbbit);


        //ONLY OFFSETS
        //inputs
        textInputEditTextsOffsets.add(et_snimac_prisiaty_dboffset);
        textInputEditTextsOffsets.add(et_snimac_dom_poloha_dboffset);
        textInputEditTextsOffsets.add(et_snimac_vys_extruder_dboffset);
        textInputEditTextsOffsets.add(et_snimac_prazdny_zasobnik_dboffset);
        textInputEditTextsOffsets.add(et_snimac_rameno_pri_z_dboffset);
        textInputEditTextsOffsets.add(et_snimac_rameno_pri_v_dboffset);
        textInputEditTextsOffsets.add(et_snimac_suc_na_v_dboffset);
        textInputEditTextsOffsets.add(et_snimac_rameno_vytah_dole_dboffset);
        //outputs
        textInputEditTextsOffsets.add(et_vystup_extruder_dboffset);
        textInputEditTextsOffsets.add(et_vystup_rameno_kz_dboffset);
        textInputEditTextsOffsets.add(et_vystup_rameno_kv_dboffset);
        textInputEditTextsOffsets.add(et_vystup_odfuk_dboffset);
        textInputEditTextsOffsets.add(et_vystup_prisavka_dboffset);
        textInputEditTextsOffsets.add(et_vystup_manual_rameno_dboffset);
        textInputEditTextsOffsets.add(et_vystup_auto_rameno_dboffset);
        textInputEditTextsOffsets.add(et_vystup_polo_automaticky_rameno_dboffset);
        textInputEditTextsOffsets.add(et_vystup_start_stop_rameno_dboffset);


        //only bits
        //inputs
        textInputEditTextsBits.add(et_snimac_prisiaty_dbbit);
        textInputEditTextsBits.add(et_snimac_dom_poloha_dbbit);
        textInputEditTextsBits.add(et_snimac_vys_extruder_dbbit);
        textInputEditTextsBits.add(et_snimac_prazdny_zasobnik_dbbit);
        textInputEditTextsBits.add(et_snimac_rameno_pri_z_dbbit);
        textInputEditTextsBits.add(et_snimac_rameno_pri_v_dbbit);
        textInputEditTextsBits.add(et_snimac_suc_na_v_dbbit);
        textInputEditTextsBits.add(et_snimac_r_vytah_dole_dbbit);
        //outputs
        textInputEditTextsBits.add(et_vystup_extruder_dbbit);
        textInputEditTextsBits.add(et_vystup_rameno_kz_dbbit);
        textInputEditTextsBits.add(et_vystup_rameno_kv_dbbit);
        textInputEditTextsBits.add(et_vystup_odfuk_dbbit);
        textInputEditTextsBits.add(et_vystup_prisavka_dbbit);
        textInputEditTextsBits.add(et_vystup_manual_rameno_dbbit);
        textInputEditTextsBits.add(et_vystup_auto_rameno_dbbit);
        textInputEditTextsBits.add(et_vystup_polo_automaticky_rameno_dbbit);
        textInputEditTextsBits.add(et_vystup_start_stop_rameno_dbbit);




        saveButton.setOnClickListener(v -> {
            String ipAdresaRameno = Objects.requireNonNull(et_ip_adresa_rameno.getText()).toString();
            String snimaceDBNumberRameno = Objects.requireNonNull(et_snimace_db_number_rameno.getText()).toString();

            //regex
            boolean isIP = ipAdresaRameno.matches("(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
            String regex = "^[1-9][0-9]{0,2}$";
            boolean isDBNumber = snimaceDBNumberRameno.matches(regex);
            boolean isEmpty = false;
            boolean isWrongOffset = false;
            boolean isWrongBit = false;

            //skontroluj ci su vsetky offsety v spravnom formate
            for(TextInputEditText textInputEditText : textInputEditTextsOffsets){
                regex = "^([0-9]|[1-9][0-9]{1,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
                if(!Objects.requireNonNull(textInputEditText.getText()).toString().matches(regex)){
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
                et_ip_adresa_rameno.setError("Zadajte správny formát IP.");
                Toast.makeText(getActivity(), "Zadajte správny formát.", Toast.LENGTH_SHORT).show();
            }else if (isWrongOffset) {
                Toast.makeText(getActivity(), "Zadajte správny formát Offsetu.", Toast.LENGTH_SHORT).show();
            }else if (isWrongBit) {
                Toast.makeText(getActivity(), "Zadajte správny formát Bitu.", Toast.LENGTH_SHORT).show();
            } else if (!isDBNumber) {
                et_snimace_db_number_rameno.setError("Zadajte správny formát čisla DB. 0-999");
                Toast.makeText(getActivity(), "Zadajte správny formát.", Toast.LENGTH_SHORT).show();
            } else {
                saveData();
            }
        });

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


        loadData();
        return view;
    }

    public void saveData() {
        //uloz do sharedpreferences
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(IP_ADRESA_RAMENO, Objects.requireNonNull(et_ip_adresa_rameno.getText()).toString());
            editor.putString(SNIMACE_DBNUMBER, Objects.requireNonNull(et_snimace_db_number_rameno.getText()).toString());
            // Snimac
            // Offsety
            editor.putString(SNIMAC_PRISIATY_DBOFFSET, Objects.requireNonNull(et_snimac_prisiaty_dboffset.getText()).toString());
            editor.putString(SNIMAC_DOM_POLOHA_DBOFFSET, Objects.requireNonNull(et_snimac_dom_poloha_dboffset.getText()).toString());
            editor.putString(SNIMAC_VYS_EXTRUDER_DBOFFSET, Objects.requireNonNull(et_snimac_vys_extruder_dboffset.getText()).toString());
            editor.putString(SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET, Objects.requireNonNull(et_snimac_prazdny_zasobnik_dboffset.getText()).toString());
            editor.putString(SNIMAC_RAMENO_PRI_Z_DBOFFSET, Objects.requireNonNull(et_snimac_rameno_pri_z_dboffset.getText()).toString());
            editor.putString(SNIMAC_RAMENO_PRI_V_DBOFFSET, Objects.requireNonNull(et_snimac_rameno_pri_v_dboffset.getText()).toString());
            editor.putString(SNIMAC_SUC_NA_V_DBOFFSET, Objects.requireNonNull(et_snimac_suc_na_v_dboffset.getText()).toString());
            editor.putString(SNIMAC_RAMENO_VYTAH_DOLE_DBOFFSET, Objects.requireNonNull(et_snimac_rameno_vytah_dole_dboffset.getText()).toString());
            // Bity
            editor.putString(SNIMAC_PRISIATY_DBBIT, Objects.requireNonNull(et_snimac_prisiaty_dbbit.getText()).toString());
            editor.putString(SNIMAC_DOM_POLOHA_DBBIT, Objects.requireNonNull(et_snimac_dom_poloha_dbbit.getText()).toString());
            editor.putString(SNIMAC_VYS_EXTRUDER_DBBIT, Objects.requireNonNull(et_snimac_vys_extruder_dbbit.getText()).toString());
            editor.putString(SNIMAC_PRAZDNY_ZASOBNIK_DBBIT, Objects.requireNonNull(et_snimac_prazdny_zasobnik_dbbit.getText()).toString());
            editor.putString(SNIMAC_RAMENO_PRI_Z_DBBIT, Objects.requireNonNull(et_snimac_rameno_pri_z_dbbit.getText()).toString());
            editor.putString(SNIMAC_RAMENO_PRI_V_DBBIT, Objects.requireNonNull(et_snimac_rameno_pri_v_dbbit.getText()).toString());
            editor.putString(SNIMAC_SUC_NA_V_DBBIT, Objects.requireNonNull(et_snimac_suc_na_v_dbbit.getText()).toString());
            editor.putString(SNIMAC_R_VYTAH_DOLE_DBBIT, Objects.requireNonNull(et_snimac_r_vytah_dole_dbbit.getText()).toString());
            // Vystup
            // Offsety
            editor.putString(VYSTUP_EXTRUDER_DBOFFSET, Objects.requireNonNull(et_vystup_extruder_dboffset.getText()).toString());
            editor.putString(VYSTUP_RAMENO_KZ_DBOFFSET, Objects.requireNonNull(et_vystup_rameno_kz_dboffset.getText()).toString());
            editor.putString(VYSTUP_RAMENO_KV_DBOFFSET, Objects.requireNonNull(et_vystup_rameno_kv_dboffset.getText()).toString());
            editor.putString(VYSTUP_ODFUK_DBOFFSET, Objects.requireNonNull(et_vystup_odfuk_dboffset.getText()).toString());
            editor.putString(VYSTUP_PRISAVKA_DBOFFSET, Objects.requireNonNull(et_vystup_prisavka_dboffset.getText()).toString());
            editor.putString(VYSTUP_MANUAL_RAMENO_DBOFFSET, Objects.requireNonNull(et_vystup_manual_rameno_dboffset.getText()).toString());
            editor.putString(VYSTUP_AUTO_RAMENO_DBOFFSET, Objects.requireNonNull(et_vystup_auto_rameno_dboffset.getText()).toString());
            editor.putString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET, Objects.requireNonNull(et_vystup_polo_automaticky_rameno_dboffset.getText()).toString());
            editor.putString(VYSTUP_START_STOP_RAMENO_DBOFFSET, Objects.requireNonNull(et_vystup_start_stop_rameno_dboffset.getText()).toString());
            // Bity
            editor.putString(VYSTUP_EXTRUDER_DBBIT, Objects.requireNonNull(et_vystup_extruder_dbbit.getText()).toString());
            editor.putString(VYSTUP_RAMENO_KZ_DBBIT, Objects.requireNonNull(et_vystup_rameno_kz_dbbit.getText()).toString());
            editor.putString(VYSTUP_RAMENO_KV_DBBIT, Objects.requireNonNull(et_vystup_rameno_kv_dbbit.getText()).toString());
            editor.putString(VYSTUP_ODFUK_DBBIT, Objects.requireNonNull(et_vystup_odfuk_dbbit.getText()).toString());
            editor.putString(VYSTUP_PRISAVKA_DBBIT, Objects.requireNonNull(et_vystup_prisavka_dbbit.getText()).toString());
            editor.putString(VYSTUP_MANUAL_RAMENO_DBBIT, Objects.requireNonNull(et_vystup_manual_rameno_dbbit.getText()).toString());
            editor.putString(VYSTUP_AUTO_RAMENO_DBBIT, Objects.requireNonNull(et_vystup_auto_rameno_dbbit.getText()).toString());
            editor.putString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT, Objects.requireNonNull(et_vystup_polo_automaticky_rameno_dbbit.getText()).toString());
            editor.putString(VYSTUP_START_STOP_RAMENO_DBBIT, Objects.requireNonNull(et_vystup_start_stop_rameno_dbbit.getText()).toString());

            editor.apply();

            Toast.makeText(getActivity(), "Uložené!", Toast.LENGTH_SHORT).show();
        }
    }
    public void loadData() {
        if (getActivity() != null){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

            et_ip_adresa_rameno.setText(sharedPreferences.getString(IP_ADRESA_RAMENO, "192.168.0.138"));
            et_snimace_db_number_rameno.setText(sharedPreferences.getString(SNIMACE_DBNUMBER, "1"));

            // Snimac
            // Offsety
            et_snimac_prisiaty_dboffset.setText(sharedPreferences.getString(SNIMAC_PRISIATY_DBOFFSET, "0"));
            et_snimac_dom_poloha_dboffset.setText(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBOFFSET, "0"));
            et_snimac_vys_extruder_dboffset.setText(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBOFFSET, "0"));
            et_snimac_prazdny_zasobnik_dboffset.setText(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET, "0"));
            et_snimac_rameno_pri_z_dboffset.setText(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBOFFSET, "0"));
            et_snimac_rameno_pri_v_dboffset.setText(sharedPreferences.getString(SNIMAC_RAMENO_PRI_V_DBOFFSET, "0"));
            et_snimac_suc_na_v_dboffset.setText(sharedPreferences.getString(SNIMAC_SUC_NA_V_DBOFFSET, "0"));
            et_snimac_rameno_vytah_dole_dboffset.setText(sharedPreferences.getString(SNIMAC_RAMENO_VYTAH_DOLE_DBOFFSET, "0"));
            // Bity
            et_snimac_prisiaty_dbbit.setText(sharedPreferences.getString(SNIMAC_PRISIATY_DBBIT, "0"));
            et_snimac_dom_poloha_dbbit.setText(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBBIT, "1"));
            et_snimac_vys_extruder_dbbit.setText(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBBIT, "2"));
            et_snimac_prazdny_zasobnik_dbbit.setText(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBBIT, "3"));
            et_snimac_rameno_pri_z_dbbit.setText(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBBIT, "4"));
            et_snimac_rameno_pri_v_dbbit.setText(sharedPreferences.getString(SNIMAC_RAMENO_PRI_V_DBBIT, "5"));
            et_snimac_suc_na_v_dbbit.setText(sharedPreferences.getString(SNIMAC_SUC_NA_V_DBBIT, "6"));
            et_snimac_r_vytah_dole_dbbit.setText(sharedPreferences.getString(SNIMAC_R_VYTAH_DOLE_DBBIT, "7"));
            // Vystup
            // Offsety
            et_vystup_extruder_dboffset.setText(sharedPreferences.getString(VYSTUP_EXTRUDER_DBOFFSET, "2"));
            et_vystup_rameno_kz_dboffset.setText(sharedPreferences.getString(VYSTUP_RAMENO_KZ_DBOFFSET, "2"));
            et_vystup_rameno_kv_dboffset.setText(sharedPreferences.getString(VYSTUP_RAMENO_KV_DBOFFSET, "2"));
            et_vystup_odfuk_dboffset.setText(sharedPreferences.getString(VYSTUP_ODFUK_DBOFFSET, "2"));
            et_vystup_prisavka_dboffset.setText(sharedPreferences.getString(VYSTUP_PRISAVKA_DBOFFSET, "2"));
            et_vystup_manual_rameno_dboffset.setText(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBOFFSET, "2"));
            et_vystup_auto_rameno_dboffset.setText(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBOFFSET, "2"));
            et_vystup_polo_automaticky_rameno_dboffset.setText(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET, "2"));
            et_vystup_start_stop_rameno_dboffset.setText(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBOFFSET, "4"));
            // Bity
            et_vystup_extruder_dbbit.setText(sharedPreferences.getString(VYSTUP_EXTRUDER_DBBIT, "0"));
            et_vystup_rameno_kz_dbbit.setText(sharedPreferences.getString(VYSTUP_RAMENO_KZ_DBBIT, "3"));
            et_vystup_rameno_kv_dbbit.setText(sharedPreferences.getString(VYSTUP_RAMENO_KV_DBBIT, "4"));
            et_vystup_odfuk_dbbit.setText(sharedPreferences.getString(VYSTUP_ODFUK_DBBIT, "2"));
            et_vystup_prisavka_dbbit.setText(sharedPreferences.getString(VYSTUP_PRISAVKA_DBBIT, "1"));
            //TODO implenmentovat auto, manual, poloautomaticky v tia portaly
            et_vystup_manual_rameno_dbbit.setText(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBBIT, "7"));
            et_vystup_auto_rameno_dbbit.setText(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBBIT, "7"));
            et_vystup_polo_automaticky_rameno_dbbit.setText(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT, "7"));
            et_vystup_start_stop_rameno_dbbit.setText(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBBIT, "0"));


        }
    }
}