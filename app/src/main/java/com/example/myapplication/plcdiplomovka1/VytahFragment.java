package com.example.myapplication.plcdiplomovka1;


import static java.lang.Math.floor;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

import Moka7.S7;
import Moka7.S7Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultBitEditing#newInstance} factory method to
 * create an instance of this fragment.
 */

public class VytahFragment extends Fragment {

    private String vytahIPAdresa;
    private String snimace_DBNumber;
    // Snimace
    // Offsety
    private int snimac_L_DBOffset;
    private int snimac_P_DBOffset;
    private int snimac_H_DBOffset;
    private int snimac_Vytah_H_DBOffset;
    private int snimac_Vytah_D_DBOffset;
    private int snimac_Rameno_DBoffset;
    private int snimac_Piest_DBOffset;
    //Bity
    private int snimac_P_DBBit;
    private int snimac_H_DBBit;
    private int snimac_L_DBBit;
    private int snimac_Vytah_H_DBBit;
    private int snimac_Vytah_D_DBBit;
    private int snimac_Rameno_DBBit;
    private int snimac_Piest_DBBit;
    //Vystupy
    // Offsety
    private int vystup_Draha_DBOffset;
    private int vystup_Piest_DBOffset;
    private int vystup_Vytah_H_DBOffset;
    private int vystup_Vytah_D_DBOffset;
    private int vystup_Manual_DBOffset;
    //Bity
    private int vystup_Draha_DBBit;
    private int vystup_Piest_DBBit;
    private int vystup_Vytah_H_DBBit;
    private int vystup_Vytah_D_DBBit;
    private int vystup_Manual_DBBit;

    private ImageView imageViewDrahaVzduch;
    private ImageView imageViewVytahHore;
    private ImageView imageViewVytahDole;

    private ImageView imageViewSnimacL;
    private ImageView imageViewSnimacP;

    private ImageView imageSuciastka;
    private ImageView imagePiest;
    private ImageView imagePiestVysuvanie;
    private ImageView imageDraha;
    private CountDownTimer countDownTimer;
    private Button buttonDraha;
    private Button buttonVytahHore;
    private Button buttonVytahDole;
    private Button buttonPiest;
    private MaterialSwitch switchRead;
    private TextView vytahNadpis;
    private TextView errorText;
    private TextView i_snimac_l;
    private TextView i_snimac_p;
    private TextView i_snimac_h;
    private TextView i_rameno_pritomne;
    private TextView i_vytah_h;
    private TextView i_vytah_d;
    private TextView i_piest_vysunuty;
    private TextView q_vytah_h;
    private TextView q_vytah_d;
    private TextView q_piest;
    private TextView q_manual;
    private TextView q_draha;
    private int counter = 10;
    private int casovac_interval = 100;
    private int casovac_time = 50000;

    private boolean isPiestVysunuty = false;
    private boolean isVytahHore = false;
    private boolean isVytahDole = false;

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

    private int functionCalled = 0;



    public VytahFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefaultBitEditing.
     */
    // TODO: Rename and change types and number of parameters
    public static VytahFragment newInstance(String param1, String param2) {
        VytahFragment fragment = new VytahFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vytah, container, false);
        // Inflate the layout for this fragment
        //tlacidla
        errorText = view.findViewById(R.id.errorTextVytah);
        buttonDraha = view.findViewById(R.id.buttonDraha);
        buttonVytahHore = view.findViewById(R.id.buttonVytahHore);
        buttonVytahDole = view.findViewById(R.id.buttonVytahDole);
        buttonPiest = view.findViewById(R.id.buttonPiest);
        //switch
        switchRead = view.findViewById(R.id.switchReadVytah);
        //vstupy
        i_snimac_l = view.findViewById(R.id.i_snimac_l);
        i_snimac_p = view.findViewById(R.id.i_snimac_p);
        i_snimac_h = view.findViewById(R.id.i_snimac_h);
        i_rameno_pritomne = view.findViewById(R.id.i_rameno_pritomne);
        i_vytah_h = view.findViewById(R.id.i_vytah_h);
        i_vytah_d = view.findViewById(R.id.i_vytah_d);
        i_piest_vysunuty = view.findViewById(R.id.i_piest_vysunuty);
        //vystupy
        q_draha = view.findViewById(R.id.q_draha);
        q_vytah_h = view.findViewById(R.id.q_vytah_h);
        q_vytah_d = view.findViewById(R.id.q_vytah_d);
        q_piest = view.findViewById(R.id.q_piest);
        q_manual = view.findViewById(R.id.q_manual);
        //ikony
        imageViewDrahaVzduch = view.findViewById(R.id.imageViewDrahaVzduch);
        imageViewVytahHore = view.findViewById(R.id.imageViewVytahHore);
        imageViewVytahDole = view.findViewById(R.id.imageViewVytahDole);
        imageViewSnimacL = view.findViewById(R.id.imageViewSnimacL);
        imageViewSnimacP = view.findViewById(R.id.imageViewSnimacP);
        imageSuciastka = view.findViewById(R.id.imageSuciastka);
        imagePiest = view.findViewById(R.id.imagePiest);
        imagePiestVysuvanie = view.findViewById(R.id.imagePiestVysuvanie);
        imageDraha = view.findViewById(R.id.imageDraha);
        //text
        vytahNadpis = view.findViewById(R.id.vytahNadpis);
        loadData();
        int falseRed = getResources().getColor(R.color.falseRed,  requireActivity().getTheme());
        int trueGreen = getResources().getColor(R.color.trueGreen, requireActivity().getTheme());
        setAllToRed(falseRed);
        switchRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startCountdown();
                counter =100;
            } else {
                cancelCountdown();
            }
        });
        //while button is held down change color of imageview2 to green, when let go change back to red
        //TODO implement this
        buttonDraha.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //write bit to 1
                new PlcWriter(Integer.parseInt(snimace_DBNumber),vystup_Draha_DBOffset,vystup_Draha_DBBit,true).execute();

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //write bit to 0
                new PlcWriter(Integer.parseInt(snimace_DBNumber),vystup_Draha_DBOffset,vystup_Draha_DBBit,false).execute();
            }
            return true;
        });
        buttonPiest.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //write bit to 1
                new PlcWriter(Integer.parseInt(snimace_DBNumber),vystup_Piest_DBOffset,vystup_Piest_DBBit,true).execute();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                //write bit to 0
                new PlcWriter(Integer.parseInt(snimace_DBNumber),vystup_Piest_DBOffset,vystup_Piest_DBBit,false).execute();
                //Toast.makeText(getContext(), "Piest sa vysunul "+vystup_Piest_DBOffset+"."+vystup_Piest_DBBit, Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        //the code
        buttonVytahDole.setOnClickListener(v -> {
            new PlcWriter(
                    Integer.parseInt(snimace_DBNumber),
                    vystup_Vytah_D_DBOffset,
                    vystup_Vytah_D_DBBit,
                    true
            ).execute();
            new PlcWriter(
                    Integer.parseInt(snimace_DBNumber),
                    vystup_Vytah_H_DBOffset,
                    vystup_Vytah_H_DBBit,
                    false
            ).execute();

        });
        buttonVytahHore.setOnClickListener(v -> {
            new PlcWriter(
                    Integer.parseInt(snimace_DBNumber),
                    vystup_Vytah_H_DBOffset,
                    vystup_Vytah_H_DBBit,
                    true
            ).execute();
            new PlcWriter(
                    Integer.parseInt(snimace_DBNumber),
                    vystup_Vytah_D_DBOffset,
                    vystup_Vytah_D_DBBit,
                    false
            ).execute();

        });

        return view;
    }
    public void loadData(){
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            vytahNadpis.setText(String.format("%s - %s", getResources().getString(R.string.vytah), sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138")));
            vytahIPAdresa = sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138");
            snimace_DBNumber = sharedPreferences.getString(SNIMACE_DBNUMBER, "1");
            // Snimace
            // Offsety

            snimac_L_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_L_DBOFFSET, "0"));
            snimac_P_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_P_DBOFFSET, "0"));
            snimac_H_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_H_DBOFFSET, "0"));
            snimac_Vytah_H_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYTAH_H_DBOFFSET, "0"));
            snimac_Vytah_D_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYTAH_D_DBOFFSET, "0"));
            snimac_Rameno_DBoffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_DBOFFSET, "0"));
            snimac_Piest_DBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_PIEST_DBOFFSET, "0"));

            snimac_L_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_L_DBBIT, "0"));
            snimac_P_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_P_DBBIT, "1"));
            snimac_H_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_H_DBBIT, "3"));
            snimac_Vytah_H_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYTAH_H_DBBIT, "4"));
            snimac_Vytah_D_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYTAH_D_DBBIT, "5"));
            snimac_Rameno_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_DBBIT, "2"));
            snimac_Piest_DBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_PIEST_DBBIT, "6"));

            vystup_Draha_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_DRAHA_DBOFFSET, "2"));
            vystup_Piest_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_PIEST_DBOFFSET, "2"));
            vystup_Vytah_H_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_H_DBOFFSET, "2"));
            vystup_Vytah_D_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_D_DBOFFSET, "2"));
            vystup_Manual_DBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_DBOFFSET, "2"));

            vystup_Draha_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_DRAHA_DBBIT, "3"));
            vystup_Piest_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_PIEST_DBBIT, "2"));
            vystup_Vytah_H_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_H_DBBIT, "1"));
            vystup_Vytah_D_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_VYTAH_D_DBBIT, "0"));
            vystup_Manual_DBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_DBBIT, "4"));
        }
    }
    private void startCountdown() {
        cancelCountdown(); // Cancel any existing countdown
        countDownTimer = new CountDownTimer(casovac_time, casovac_interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI with current count
                new PlcReader().execute();
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
    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    S7Client client = new S7Client();
    private class PlcReader extends AsyncTask<String,Void,String> {
        String ret = "";
        byte[] data = new byte[3];
        boolean[][] dataBools = new boolean[data.length][8];
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                //inputs
                int dbOffsetInputs = 0;

                int res = client.ConnectTo(vytahIPAdresa,0,2);// ak je S7-300 tak je vždy 0,2

                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    res = client.ReadArea(S7.S7AreaDB, Integer.parseInt(snimace_DBNumber),dbOffsetInputs,3,data);
                    //log res in console

                    for(int i = 0; i < data.length; i++){
                        boolean[] dataBool = new boolean[8];
                        for (int j = 0; j < dataBool.length; j++) {
                            dataBool[j] = S7.GetBitAt(data,i,j);
                        }
                        dataBools[i] = dataBool;
                    }
                }else{
                    System.out.println("Error: " + S7Client.ErrorText(res));
                    ret = "Error: " + S7Client.ErrorText(res);
                }
                client.Disconnect();
            }catch (Exception e){
                ret = e.getMessage();
                Thread.interrupted();
            }
            return "executed";
        }

        @Override
        protected void onPostExecute(String result){

            if(ret.toLowerCase().contains("error")){
                errorText.setText(ret);
            }else {
                //inputs
                //log dataBools[snimac_L_DBOffset][snimac_L_DBBit] in console

                i_snimac_l.setTextColor(dataBools[snimac_L_DBOffset][snimac_L_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_snimac_p.setTextColor(dataBools[snimac_P_DBOffset][snimac_P_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_snimac_h.setTextColor(dataBools[snimac_H_DBOffset][snimac_H_DBBit] ? getResources().getColor(R.color.falseRed, requireActivity().getTheme()) : getResources().getColor(R.color.trueGreen, getActivity().getTheme()));
                i_rameno_pritomne.setTextColor(dataBools[snimac_Rameno_DBoffset][snimac_Rameno_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_vytah_h.setTextColor(dataBools[snimac_Vytah_H_DBOffset][snimac_Vytah_H_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_vytah_d.setTextColor(dataBools[snimac_Vytah_D_DBOffset][snimac_Vytah_D_DBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_piest_vysunuty.setTextColor(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit] ? getResources().getColor(R.color.falseRed, requireActivity().getTheme()) : getResources().getColor(R.color.trueGreen, getActivity().getTheme()));
                //outputs
                q_draha.setTextColor(dataBools[vystup_Draha_DBOffset][vystup_Draha_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_vytah_h.setTextColor(dataBools[vystup_Vytah_H_DBOffset][vystup_Vytah_H_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_vytah_d.setTextColor(dataBools[vystup_Vytah_D_DBOffset][vystup_Vytah_D_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_piest.setTextColor(dataBools[vystup_Piest_DBOffset][vystup_Piest_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_manual.setTextColor(dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                //ikony
                imageViewDrahaVzduch.setColorFilter(dataBools[vystup_Draha_DBOffset][vystup_Draha_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                imageViewVytahHore.setColorFilter(dataBools[snimac_Vytah_H_DBOffset][snimac_Vytah_H_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                imageViewVytahDole.setColorFilter(dataBools[snimac_Vytah_D_DBOffset][snimac_Vytah_D_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                imageViewSnimacL.setColorFilter(dataBools[snimac_L_DBOffset][snimac_L_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                imageViewSnimacP.setColorFilter(dataBools[snimac_P_DBOffset][snimac_P_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                //imageSuciastka , if isnimacl or isnimacp or isnimach is true then set image to visible
                //isnimach has opposite logic
                if(dataBools[snimac_L_DBOffset][snimac_L_DBBit] || dataBools[snimac_P_DBOffset][snimac_P_DBBit] || !dataBools[snimac_H_DBOffset][snimac_H_DBBit]){
                    imageSuciastka.setVisibility(View.VISIBLE);
                }else{
                    imageSuciastka.setVisibility(View.INVISIBLE);
                }
                //imagePiest , if isnimacpiest is true then animate image to move to the right
                if (isPiestVysunuty != dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit]){
                    if(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit]){

                        animatePiest(14,300);
                    }else {
                        animatePiest(96,300);
                    }
                }
                isPiestVysunuty = dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit];
                boolean isVytahD = isVytahD(dataBools);
                boolean isVytahH = isVytahH(dataBools);
                boolean isVytahDoleNow = isVytahD && !isVytahH;
                boolean isVytahHoreNow = isVytahH && !isVytahD;

                if(isVytahHore != isVytahHoreNow ){
                    if(isVytahH){

                        animateVytah(232,500);
                    }
                }
                if(isVytahDole != isVytahDoleNow){
                    if(isVytahD){
                    animateVytah(52,500);
                    }
                }
                isVytahDole = isVytahDoleNow;
                isVytahHore = isVytahHoreNow;
                System.out.println("Function called: "+functionCalled);


                /*
                //Print all values
                System.out.println("i_snimac_l = "+(dataBools[snimac_L_DBOffset][snimac_L_DBBit]));
                System.out.println("i_snimac_p = "+(dataBools[snimac_P_DBOffset][snimac_P_DBBit]));
                System.out.println("i_snimac_h = "+(dataBools[snimac_H_DBOffset][snimac_H_DBBit]));
                System.out.println("i_rameno_pritomne = "+(dataBools[snimac_Rameno_DBoffset][snimac_Rameno_DBBit]));
                System.out.println("i_vytah_h = "+(dataBools[snimac_Vytah_H_DBOffset][snimac_Vytah_H_DBBit]));
                System.out.println("i_vytah_d = "+(dataBools[snimac_Vytah_D_DBOffset][snimac_Vytah_D_DBBit]));
                System.out.println("i_piest_vysunuty = "+(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit]));

                System.out.println("q_draha = "+(dataBools[vystup_Draha_DBOffset][vystup_Draha_DBBit]));
                System.out.println("q_vytah_h = "+(dataBools[vystup_Vytah_H_DBOffset][vystup_Vytah_H_DBBit]));
                System.out.println("q_vytah_d = "+(dataBools[vystup_Vytah_D_DBOffset][vystup_Vytah_D_DBBit]));
                System.out.println("q_piest = "+(dataBools[vystup_Piest_DBOffset][vystup_Piest_DBBit]));
                System.out.println("q_manual = "+(dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit]));
                */
                /*
                //Print all ADRESSES
                System.out.println("i_snimac_l = "+snimac_L_DBOffset+"."+snimac_L_DBBit);
                System.out.println("i_snimac_p = "+snimac_P_DBOffset+"."+snimac_P_DBBit);
                System.out.println("i_snimac_h = "+snimac_H_DBOffset+"."+snimac_H_DBBit);
                System.out.println("i_rameno_pritomne = "+snimac_Rameno_DBoffset+"."+snimac_Rameno_DBBit);
                System.out.println("i_vytah_h = "+snimac_Vytah_H_DBOffset+"."+snimac_Vytah_H_DBBit);
                System.out.println("i_vytah_d = "+snimac_Vytah_D_DBOffset+"."+snimac_Vytah_D_DBBit);
                System.out.println("i_piest_vysunuty = "+snimac_Piest_DBOffset+"."+snimac_Piest_DBBit);

                System.out.println("q_draha = "+vystup_Draha_DBOffset+"."+vystup_Draha_DBBit);
                System.out.println("q_vytah_h = "+vystup_Vytah_H_DBOffset+"."+vystup_Vytah_H_DBBit);
                System.out.println("q_vytah_d = "+vystup_Vytah_D_DBOffset+"."+vystup_Vytah_D_DBBit);
                System.out.println("q_piest = "+vystup_Piest_DBOffset+"."+vystup_Piest_DBBit);
                System.out.println("q_manual = "+vystup_Manual_DBOffset+"."+vystup_Manual_DBBit);
                 */
            }
        }
    }
    private boolean isVytahD(boolean[][] dataBools) {
        functionCalled++;
        return dataBools[snimac_Vytah_D_DBOffset][snimac_Vytah_D_DBBit] || dataBools[vystup_Vytah_D_DBOffset][vystup_Vytah_D_DBBit];
    }

    private boolean isVytahH(boolean[][] dataBools) {
        functionCalled++;
        return dataBools[snimac_Vytah_H_DBOffset][snimac_Vytah_H_DBBit] || dataBools[vystup_Vytah_H_DBOffset][vystup_Vytah_H_DBBit];
    }
    private class PlcWriter extends AsyncTask<String,Void,String> {
        String ret = "";
        int dbNumber;
        int dbOffset;
        int dbBit;
        boolean writeValue;

        // Constructor to accept DBNumber, DBBit, and DBOffset
        public PlcWriter(int dbNumber, int dbOffset, int dbBit,boolean writeValue) {
            this.dbNumber = dbNumber;
            this.dbOffset = dbOffset;
            this.dbBit = dbBit;
            this.writeValue = writeValue;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                client.SetConnectionType(S7.S7_BASIC);

                int res = client.ConnectTo(vytahIPAdresa, 0, 2);// ak je S7-300 tak je vždy 0,2
                if (res == 0) {
                    byte[] data = new byte[1];
                    res = client.ReadArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                    if (res == 0) {
                        S7.SetBitAt(data, 0, dbBit, writeValue);
                        res = client.WriteArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                        if (res == 0) {
                            ret="Success";
                        } else {
                        ret = "Error: " + S7Client.ErrorText(res);
                        }
                    }else {
                        ret = "Error: " + S7Client.ErrorText(res);
                    }
                } else {
                    //stringValue += "res == " + res + "\n";
                    ret = "Error: " + S7Client.ErrorText(res);
                }
                client.Disconnect();
            } catch (Exception e) {
                ret = e.getMessage();
                Thread.interrupted();
            }
            return "executed";
        }
        @Override
        protected void onPostExecute(String result){
            errorText.setText(ret);
        }
    }
    private void setAllToRed(int falseRed){
        //ikony
        imageViewDrahaVzduch.setColorFilter(falseRed);
        imageViewVytahDole.setColorFilter(falseRed);
        imageViewVytahHore.setColorFilter(falseRed);
        imageViewSnimacL.setColorFilter(falseRed);
        imageViewSnimacP.setColorFilter(falseRed);
        //vstupy
        i_snimac_h.setTextColor(falseRed);
        i_snimac_l.setTextColor(falseRed);
        i_snimac_p.setTextColor(falseRed);
        i_vytah_h.setTextColor(falseRed);
        i_vytah_d.setTextColor(falseRed);
        i_piest_vysunuty.setTextColor(falseRed);
        i_rameno_pritomne.setTextColor(falseRed);
        //vystupy
        q_draha.setTextColor(falseRed);
        q_vytah_h.setTextColor(falseRed);
        q_vytah_d.setTextColor(falseRed);
        q_piest.setTextColor(falseRed);
        q_manual.setTextColor(falseRed);
    }

    private void animatePiest(int positionToDp,int duration){
        float density = getResources().getDisplayMetrics().density;
        int positionTo = Math.round(positionToDp * density);
        // Get the current layout parameters
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imagePiestVysuvanie.getLayoutParams();
        // if positionTo is already set then return
        if(params.getMarginEnd() == positionTo){
            return;
        }
        //Toast.makeText(getContext(), "Piest sa vysunul "+positionTo, Toast.LENGTH_SHORT).show();
        // Create a ValueAnimator that animates from the current end margin to 0
        ValueAnimator animator = ValueAnimator.ofInt(params.getMarginEnd(), positionTo);
        animator.addUpdateListener(animation -> {
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
    private void animateVytah(int positionToDp,int duration){
        float density = getResources().getDisplayMetrics().density;
        int positionTo = Math.round(positionToDp * density);
        // Get the current layout parameters
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) imagePiest.getLayoutParams();
        // if positionTo is already set then return
        if(params.bottomMargin == positionTo){
            System.out.println("positionTo is already set");
            return;
        }
        //Toast.makeText(getContext(), "Piest sa vysunul "+positionTo, Toast.LENGTH_SHORT).show();
        // Create a ValueAnimator that animates from the current end margin to 0
        ValueAnimator animator = ValueAnimator.ofInt(params.bottomMargin, positionTo);
        animator.addUpdateListener(animation -> {
            // Update the end margin in the layout parameters
            params.bottomMargin = (Integer) animation.getAnimatedValue();
            imagePiest.setLayoutParams(params);
            imagePiest.getParent().requestLayout();
        });

        // Set the duration of the animation
        animator.setDuration(duration);

        // Start the animation
        animator.start();
    }
}