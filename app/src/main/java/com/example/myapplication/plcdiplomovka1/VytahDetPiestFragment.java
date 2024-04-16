package com.example.myapplication.plcdiplomovka1;


import static java.lang.Math.floor;
import static java.lang.Math.max;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import Moka7.S7;
import Moka7.S7Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultBitEditing#newInstance} factory method to
 * create an instance of this fragment.
 */

public class VytahDetPiestFragment extends Fragment {
    //region Addreses
    private String vytahIPAdresa;
    private String snimace_DBNumber;
    // Snimace
    // Offsety
    private int maxOffset = 0;
    private int snimac_L_DBOffset;
    private int snimac_P_DBOffset;
    private int snimac_H_DBOffset;
    private int snimac_Rameno_DBoffset;
    private int snimac_Piest_DBOffset;
    //Bity
    private int snimac_P_DBBit;
    private int snimac_H_DBBit;
    private int snimac_L_DBBit;
    private int snimac_Rameno_DBBit;
    private int snimac_Piest_DBBit;
    //Vystupy
    // Offsety
    private int vystup_Piest_DBOffset;
    private int vystup_Vytah_H_DBOffset;
    private int vystup_Vytah_D_DBOffset;
    private int vystup_Manual_DBOffset;
    private int vystup_Auto_Polo_DBOffset;
    private int vystup_Start_Vytah_DBOffset;
    private int vystup_Stop_Vytah_DBOffset;
    //Bity

    private int vystup_Piest_DBBit;
    private int vystup_Manual_DBBit;
    private int vystup_Auto_Polo_DBBit;
    private int vystup_Start_Vytah_DBBit;
    private int vystup_Stop_Vytah_DBBit;
    //endregion

    //region Buttons


    Button buttonPiest ;
    Button buttonStartVytah;
    Button buttonStopVytah;
    Button buttonAutoVytah;
    Button buttonManualVytah;
    //endregion

    //region ImageView
    private ImageView imageViewSnimacL;
    private ImageView imageViewSnimacP;

    private ImageView imageSuciastka;
    private ImageView imagePiest;
    private ImageView imagePiestVysuvanie;
    //endregion

    private CountDownTimer countDownTimer;
    private MaterialSwitch switchRead;
    //region TextView
    private TextView vytahNadpis;
    private TextView errorText;
    private TextView i_snimac_l;
    private TextView i_snimac_p;
    private TextView i_rameno_pritomne;
    private TextView i_piest_vysunuty;
    private TextView q_piest;
    private TextView q_manual;

    //endregion

    //region Variables
    private int counter = 10;
    private int casovac_interval = 100;
    private int casovac_time = 50000;
    private boolean isPiestVysunuty = false;
    private boolean isSnimacHore = false;

    //endregion

    //region SharedPrefs
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
    private static final String SNIMAC_RAMENO_DBBIT = "snimac_Rameno_DBBit";
    private static final String SNIMAC_PIEST_DBBIT = "snimac_Piest_DBBit";

    //output Ofsets

    private static final String VYSTUP_PIEST_DBOFFSET = "vystup_Piest_DBOffset";
    private static final String VYSTUP_VYTAH_H_DBOFFSET = "vystup_Vytah_H_DBOffset";
    private static final String VYSTUP_VYTAH_D_DBOFFSET = "vystup_Vytah_D_DBOffset";
    private static final String VYSTUP_MANUAL_DBOFFSET = "vystup_Vytah_Manual_DBOffset";
    private static final String VYSTUP_AUTO_POLO_DBOFFSET = "vystup_Vytah_Auto_Polo_DBOffset";
    private static final String VYSTUP_START_VYTAH_DBOFFSET = "vystup_start_vytah_dboffset";
    private static final String VYSTUP_STOP_VYTAH_DBOFFSET = "vystup_stop_vytah_dboffset";
    //output Bits

    private static final String VYSTUP_PIEST_DBBIT = "vystup_Piest_DBBit";

    private static final String VYSTUP_MANUAL_DBBIT = "vystup_Vytah_Manual_DBBit";
    private static final String VYSTUP_AUTO_POLO_DBBIT = "vystup_Vytah_Auto_Polo_DBBit";

    private static final String VYSTUP_START_VYTAH_DBBIT = "vystup_start_vytah_dbbit";
    private static final String VYSTUP_STOP_VYTAH_DBBIT = "vystup_stop_vytah_dbbit";
    //endregion


    public VytahDetPiestFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VytahFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VytahDetPiestFragment newInstance(String param1, String param2) {
        VytahDetPiestFragment fragment = new VytahDetPiestFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
            if (bottomNavigationView != null && bottomNavigationView.getSelectedItemId() != R.id.nav_vytah){
                bottomNavigationView.setSelectedItemId(R.id.nav_vytah);
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    S7Client client = new S7Client();
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onPause() {
        super.onPause();
        if (switchRead.isChecked()) {
            switchRead.setChecked(false);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vytah_det_piest, container, false);
        //asdae

        errorText = view.findViewById(R.id.errorTextVytah);
        //tlacidla
        buttonPiest = view.findViewById(R.id.buttonPiest);
        buttonAutoVytah = view.findViewById(R.id.buttonAutoVytah);
        buttonManualVytah = view.findViewById(R.id.buttonManualVytah);
        buttonStartVytah = view.findViewById(R.id.btn_start_vytah);
        buttonStopVytah = view.findViewById(R.id.btn_stop_vytah);
        //switch
        switchRead = view.findViewById(R.id.switchReadVytah);
        //vstupy
        i_snimac_l = view.findViewById(R.id.i_snimac_l);
        i_snimac_p = view.findViewById(R.id.i_snimac_p);
        i_rameno_pritomne = view.findViewById(R.id.i_rameno_pritomne);
        i_piest_vysunuty = view.findViewById(R.id.i_piest_vysunuty);
        //vystupy
        q_piest = view.findViewById(R.id.q_piest);
        q_manual = view.findViewById(R.id.q_manual);
        //ikony


        imageViewSnimacL = view.findViewById(R.id.imageViewSnimacL);
        imageViewSnimacP = view.findViewById(R.id.imageViewSnimacP);
        imageSuciastka = view.findViewById(R.id.imageSuciastka);
        imagePiest = view.findViewById(R.id.imagePiest);
        imagePiestVysuvanie = view.findViewById(R.id.imagePiestVysuvanie);
        //text
        vytahNadpis = view.findViewById(R.id.vytahNadpis);
        loadData();
        int falseRed = getResources().getColor(R.color.falseRed,  requireActivity().getTheme());
        setAllToColor(falseRed);

        switchRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ReadPlc();
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideMenu();
                    }
                }
            }
            if (isChecked) {
                startCountdown();
                counter = 100;
            } else {
                cancelCountdown();
            }
        });
        //while button is held down change color of imageview2 to green, when let go change back to red
        //TODO implement this
        buttonPiest.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //write bit to 1
                if (getActivity() instanceof MainActivity) {
                    if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).hideMenu();
                        }
                    }
                }
                IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Piest_DBOffset, vystup_Piest_DBBit, true);
                ReadPlc();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //write bit to 0
                IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Piest_DBOffset, vystup_Piest_DBBit, false);
                ReadPlc();
            }
            return false;
        });
        //the code

        buttonStartVytah.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideMenu();
                    }
                }
            }

            if(IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Stop_Vytah_DBOffset, vystup_Stop_Vytah_DBBit, false) == 0){
                IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Start_Vytah_DBOffset, vystup_Start_Vytah_DBBit, true);
            }
            if (!switchRead.isChecked()){
                ReadPlc();
            }
        });
        buttonStopVytah.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideMenu();
                    }
                }
            }
            if(IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Start_Vytah_DBOffset, vystup_Start_Vytah_DBBit, false) == 0){
                IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Stop_Vytah_DBOffset, vystup_Stop_Vytah_DBBit, true);
            }
            if (!switchRead.isChecked()){
                ReadPlc();
            }
        });

        buttonAutoVytah.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideMenu();
                    }
                }
            }
            if(IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Manual_DBOffset, vystup_Manual_DBBit, false) == 0){
                IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Auto_Polo_DBOffset, vystup_Auto_Polo_DBBit, true);
            }
            if (!switchRead.isChecked()){
                ReadPlc();
            }
        });
        buttonManualVytah.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideMenu();
                    }
                }
            }
            if(IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Auto_Polo_DBOffset, vystup_Auto_Polo_DBBit, false) == 0){
                if(IOMethods.WritePlc(errorText, client, handler, executorService, vytahIPAdresa, Integer.parseInt(snimace_DBNumber), vystup_Manual_DBOffset, vystup_Manual_DBBit, true) == 0) {
                    if (!switchRead.isChecked()) {
                        Snackbar.make(view, "Monitoroanie vypnuté!", Snackbar.LENGTH_LONG).show();
                        // Assuming 'view' is your view that you want to highlight
                        switchRead.setBackgroundColor(Color.RED); // Change to your highlight color

                        //Create a new Handler to reset the color after 1 second
                        new Handler().postDelayed(() -> {
                            switchRead.setBackgroundColor(Color.TRANSPARENT); // Change to your default color
                        }, 1000); // Delay of 1 second
                    }
                }
            }
        });
        maxOffset =  getMaxOffset(new Integer[]{snimac_L_DBOffset, snimac_P_DBOffset, snimac_H_DBOffset, snimac_Rameno_DBoffset, snimac_Piest_DBOffset, vystup_Piest_DBOffset, vystup_Vytah_H_DBOffset, vystup_Vytah_D_DBOffset, vystup_Manual_DBOffset, vystup_Auto_Polo_DBOffset, vystup_Start_Vytah_DBOffset, vystup_Stop_Vytah_DBOffset});
        //Toast.makeText(getContext(), "Max offset: " + maxOffset, Toast.LENGTH_SHORT).show();
        if (!switchRead.isChecked()){
            ReadPlc();
        }
        return view;
    }
    public void loadData(){
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            vytahNadpis.setText(String.format("%s - %s", getResources().getString(R.string.menu_elevator), sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138")));
            vytahIPAdresa = sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138");
            snimace_DBNumber = sharedPreferences.getString(SNIMACE_DBNUMBER, "1");
            // Snimace
            // Offsety

            snimac_L_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_L_DBOFFSET, "0"));
            snimac_P_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_P_DBOFFSET, "0"));
            snimac_H_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_H_DBOFFSET, "0"));

            snimac_Rameno_DBoffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_DBOFFSET, "0"));
            snimac_Piest_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_PIEST_DBOFFSET, "0"));

            snimac_L_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_L_DBBIT, "0"));
            snimac_P_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_P_DBBIT, "1"));
            snimac_H_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_H_DBBIT, "3"));

            snimac_Rameno_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_DBBIT, "2"));
            snimac_Piest_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_PIEST_DBBIT, "6"));


            vystup_Piest_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_PIEST_DBOFFSET, "2"));
            vystup_Vytah_H_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_H_DBOFFSET, "2"));
            vystup_Vytah_D_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_D_DBOFFSET, "2"));
            vystup_Manual_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_DBOFFSET, "2"));
            vystup_Auto_Polo_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_POLO_DBOFFSET, "2"));
            vystup_Start_Vytah_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_VYTAH_DBOFFSET, "3"));
            vystup_Stop_Vytah_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_STOP_VYTAH_DBOFFSET, "3"));


            vystup_Piest_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_PIEST_DBBIT, "2"));
            vystup_Manual_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_DBBIT, "4"));
            vystup_Auto_Polo_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_POLO_DBBIT, "7"));
            vystup_Start_Vytah_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_VYTAH_DBBIT, "0"));
            vystup_Stop_Vytah_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_STOP_VYTAH_DBBIT, "1"));
        }
    }
    /**
     * Metóda startCountdown spustí odpočítavanie.
     * Táto metóda najprv zruší existujúce odpočítavanie (ak existuje) a potom vytvorí nový CountDownTimer.
     * CountDownTimer je nastavený na odpočítavanie od hodnoty casovac_time s intervalom casovac_interval.
     * Počas každého intervalu sa volá metóda ReadPlc, ktorá číta dáta z PLC.
     * Keď odpočítavanie skončí, prepne sa prepínač switchRead na vypnutý stav.
     */
    private void startCountdown() {
        cancelCountdown(); // Cancel any existing countdown
        countDownTimer = new CountDownTimer(casovac_time, casovac_interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI with current count
                ReadPlc();
                //System.out.println("Countdown: " + counter);
                counter--;
            }

            @Override
            public void onFinish() {
                //System.out.println("Countdown finished");
                counter = (int) floor(casovac_time/casovac_interval); // Reset counter
                switchRead.setChecked(false); // Turn off the switch after countdown finishes
            }
        };
        countDownTimer.start();
    }
    /**
     * Metóda cancelCountdown zruší existujúce odpočítavanie.
     * Táto metóda kontroluje, či je countDownTimer inicializovaný a ak áno, zruší ho.
     */
    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (client.Connected){
            client.Disconnect();
        }
    }

    /**
     * Metóda ReadPlc je zodpovedná za čítanie dát z PLC.
     * Táto metóda vytvára nový vlákno, ktoré sa pripojí na PLC, prečíta dáta a aktualizuje UI.
     * Ak nastane chyba pri pripojení alebo čítaní dát, chybová správa sa zobrazí v TextView errorText.
     * Po úspešnom prečítaní dát sa aktualizujú farby TextView a ImageView na základe prečítaných hodnôt.
     * Táto metóda tiež kontroluje zmeny stavu výťahu a piestu a spúšťa animácie týchto komponentov.
     */
    private int getMaxOffset(Integer[] arrayOfOffsets){
        int maxOffset = 0;
        for (int offset : arrayOfOffsets){
            maxOffset = max(maxOffset, offset);
        }
        return maxOffset;
    }

    private void ReadPlc(){
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[maxOffset+1];
        boolean[][] dataBools = new boolean[data.length][8];

        executorService.execute(() ->{
            //code to do in background
            try{
                client.SetConnectionType(S7.S7_BASIC);
                //inputs
                int dbOffsetInputs = 0;

                int res = client.ConnectTo(vytahIPAdresa,0,2);// ak je S7-300 tak je vždy 0,2

                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    res = client.ReadArea(S7.S7AreaDB, Integer.parseInt(snimace_DBNumber),dbOffsetInputs,maxOffset+1,data);
                    //log res in console
                    if (res == 0){
                        for(int i = 0; i < data.length; i++){
                            boolean[] dataBool = new boolean[8];
                            for (int j = 0; j < dataBool.length; j++) {
                                dataBool[j] = S7.GetBitAt(data,i,j);
                            }
                            dataBools[i] = dataBool;
                        }
                    }else{
                        System.out.println("Error: " + S7Client.ErrorText(res));
                        ret.set("Read Error: " + S7Client.ErrorText(res));
                    }
                }else{
                    System.out.println("Error: " + S7Client.ErrorText(res));
                    ret.set("Connection Error: " + S7Client.ErrorText(res));
                }
                client.Disconnect();
            }catch (Exception e){
                ret.set(e.getMessage());
                Thread.interrupted();
            }

            handler.post(() -> {
                //update UI

                if(ret.get().toLowerCase().contains("error")){
                    errorText.setText(ret.get());
                }else {

                    //inputs
                    //log dataBools[snimac_L_DBOffset][snimac_L_DBBit] in console

                    i_snimac_l.setTextColor(dataBools[snimac_L_DBOffset][snimac_L_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    i_snimac_p.setTextColor(dataBools[snimac_P_DBOffset][snimac_P_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    i_rameno_pritomne.setTextColor(dataBools[snimac_Rameno_DBoffset][snimac_Rameno_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    i_piest_vysunuty.setTextColor(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit] ? getResources().getColor(R.color.falseRed, requireActivity().getTheme()) : getResources().getColor(R.color.trueGreen, requireActivity().getTheme()));
                    //outputs
                    q_piest.setTextColor(dataBools[vystup_Piest_DBOffset][vystup_Piest_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    q_manual.setTextColor(dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));



                    buttonAutoVytah.setBackgroundColor(!dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    buttonManualVytah.setBackgroundColor(dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    buttonStartVytah.setBackgroundColor(dataBools[vystup_Start_Vytah_DBOffset][vystup_Start_Vytah_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    buttonStopVytah.setBackgroundColor(dataBools[vystup_Stop_Vytah_DBOffset][vystup_Stop_Vytah_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    // ak je manualny mod tak sa tlacidla zablokuju
                    boolean jeManual = dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit];
                    buttonPiest.setEnabled(jeManual);

                    if (!jeManual){
                        buttonPiest.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));
                    }else {
                        buttonPiest.setBackgroundColor(dataBools[vystup_Piest_DBOffset][vystup_Piest_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    }

                    //ikony
                    imageViewSnimacL.setColorFilter(dataBools[snimac_L_DBOffset][snimac_L_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    imageViewSnimacP.setColorFilter(dataBools[snimac_P_DBOffset][snimac_P_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //imageSuciastka , if isnimacl or isnimacp or isnimach is true then set image to visible
                    //isnimach has opposite logic




                    if(dataBools[snimac_L_DBOffset][snimac_L_DBBit] || dataBools[snimac_P_DBOffset][snimac_P_DBBit] || !dataBools[snimac_H_DBOffset][snimac_H_DBBit]){
                        imageSuciastka.setVisibility(View.VISIBLE);
                    }else{
                        imageSuciastka.setVisibility(View.INVISIBLE);
                    }


                    if (isPiestVysunuty != dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit]){
                        if(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit]){
                            animatePiest(14,300);//14
                        }else {
                            animatePiest(96,300);
                        }
                    }
                    isPiestVysunuty = dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit];




                }


            });
        } );

    }
    /**
     * Metóda isVytahD kontroluje, či je výťah dole.
     * Táto metóda porovnáva hodnoty snímača výťahu dole a výstupu výťahu dole.
     * Ak je aspoň jedna z týchto hodnôt pravdivá, metóda vráti true, inak vráti false.
     *
     * @param dataBools Dvojrozmerné pole booleanov, ktoré obsahuje hodnoty všetkých snímačov a výstupov.
     * @return boolean Hodnota true, ak je výťah dole, inak false.
     */

    /**
     * Metóda isVytahH kontroluje, či je výťah hore.
     * Táto metóda porovnáva hodnoty snímača výťahu hore a výstupu výťahu hore.
     * Ak je aspoň jedna z týchto hodnôt pravdivá, metóda vráti true, inak vráti false.
     *
     * @param dataBools Dvojrozmerné pole booleanov, ktoré obsahuje hodnoty všetkých snímačov a výstupov.
     * @return boolean Hodnota true, ak je výťah hore, inak false.
     */

    /**
     * Metóda setAllToColor nastaví všetky vizuálne prvky na zadanú farbu.
     * Táto metóda je používaná na zresetovanie vizuálnych prvkov na pôvodnú červenú farbu.
     * Farba je definovaná ako parameter metódy.
     *
     * @param color Farba, na ktorú sa majú nastaviť všetky vizuálne prvky.
     *                 Táto farba je zvyčajne červená, ale môže byť akákoľvek farba definovaná ako integer.
     */
    private void setAllToColor(int color){
        //ikony
        imageViewSnimacL.setColorFilter(color);
        imageViewSnimacP.setColorFilter(color);
        //vstupy
        i_snimac_l.setTextColor(color);
        i_snimac_p.setTextColor(color);
        i_piest_vysunuty.setTextColor(color);
        i_rameno_pritomne.setTextColor(color);
        //vystupy
        q_piest.setTextColor(color);
        q_manual.setTextColor(color);
        //tlacidla
        buttonPiest.setBackgroundColor(color);
        buttonAutoVytah.setBackgroundColor(color);
        buttonManualVytah.setBackgroundColor(color);
        buttonStartVytah.setBackgroundColor(color);
        buttonStopVytah.setBackgroundColor(color);
    }
    /**
     * Metóda animatePiest animuje pohyb piestu.
     * Táto metóda vytvára animáciu, ktorá mení pozíciu piestu z aktuálnej pozície na cieľovú pozíciu.
     * Cieľová pozícia a trvanie animácie sú definované ako parametre metódy.
     * Počas animácie sa aktualizuje dolný okraj (bottom margin) layoutu piestu.
     *
     * @param positionToDp Cieľová pozícia piestu v dp (density-independent pixels).
     * @param duration Trvanie animácie v milisekundách.
     */
    private void animatePiest(int positionToDp,int duration){
        float density = getResources().getDisplayMetrics().density;
        int positionTo = Math.round(positionToDp * density);
        // Get the current layout parameters
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imagePiestVysuvanie.getLayoutParams();
        // if positionTo is already set then return
        if(params.getMarginEnd() == positionTo){
            System.out.println("animatePiest: "+params.getMarginEnd()+" == "+positionTo);
            System.out.println("animatePiest: positionTo is already set");
            return;
        }
        // Create a ValueAnimator that animates from the current end margin to 0
        ValueAnimator animator = ValueAnimator.ofInt(params.getMarginEnd(), positionTo);
        animator.addUpdateListener(animation -> {
            System.out.println("animatePiest: Animujem piest k "+positionToDp+" dp");
            // Update the end margin in the layout parameters
            params.setMarginEnd((Integer) animation.getAnimatedValue());
            imagePiestVysuvanie.setLayoutParams(params);
            imagePiestVysuvanie.getParent().requestLayout();
        });
        // Set the duration of the animation
        animator.setDuration(duration);

        // Start the animation
        animator.start();
    }
}