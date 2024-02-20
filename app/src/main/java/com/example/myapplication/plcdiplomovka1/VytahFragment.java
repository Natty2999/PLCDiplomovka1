package com.example.myapplication.plcdiplomovka1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Arrays;

import Moka7.S7;
import Moka7.S7Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultBitEditing#newInstance} factory method to
 * create an instance of this fragment.
 */

public class VytahFragment extends Fragment {
    private String ipAddress = "192.168.0.138";
    private int dbNumber = 1;

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
    private CountDownTimer countDownTimer;
    private Button buttonDraha;
    private MaterialSwitch switchRead;
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
        buttonDraha = view.findViewById(R.id.buttonDraha);
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
        int falseRed = getResources().getColor(R.color.falseRed,  getActivity().getTheme());
        int trueGreen = getResources().getColor(R.color.trueGreen, getActivity().getTheme());
        setAllToRed(view,falseRed);
        switchRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startCountdown();
                counter =100;
            } else {
                cancelCountdown();
            }
        });
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
        //the code

        loadData();
        return view;
    }
    public void loadData(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

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
    private void startCountdown() {
        cancelCountdown(); // Cancel any existing countdown
        countDownTimer = new CountDownTimer(10000, 100) {
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
                counter = 100; // Reset counter
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

                int res = client.ConnectTo(ipAddress,0,2);// ak je S7-300 tak je vždy 0,2

                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    res = client.ReadArea(S7.S7AreaDB,dbNumber,dbOffsetInputs,3,data);
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

                i_snimac_l.setTextColor(dataBools[snimac_L_DBOffset][snimac_L_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_snimac_p.setTextColor(dataBools[snimac_P_DBOffset][snimac_P_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_snimac_h.setTextColor(dataBools[snimac_H_DBOffset][snimac_H_DBBit] ? getResources().getColor(R.color.falseRed, getActivity().getTheme()) : getResources().getColor(R.color.trueGreen, getActivity().getTheme()));
                i_rameno_pritomne.setTextColor(dataBools[snimac_Rameno_DBoffset][snimac_Rameno_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_vytah_h.setTextColor(dataBools[snimac_Vytah_H_DBOffset][snimac_Vytah_H_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_vytah_d.setTextColor(dataBools[snimac_Vytah_D_DBOffset][snimac_Vytah_D_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                i_piest_vysunuty.setTextColor(dataBools[snimac_Piest_DBOffset][snimac_Piest_DBBit] ? getResources().getColor(R.color.falseRed, getActivity().getTheme()) : getResources().getColor(R.color.trueGreen, getActivity().getTheme()));
                //outputs
                q_draha.setTextColor(dataBools[vystup_Draha_DBOffset][vystup_Draha_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_vytah_h.setTextColor(dataBools[vystup_Vytah_H_DBOffset][vystup_Vytah_H_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_vytah_d.setTextColor(dataBools[vystup_Vytah_D_DBOffset][vystup_Vytah_D_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_piest.setTextColor(dataBools[vystup_Piest_DBOffset][vystup_Piest_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
                q_manual.setTextColor(dataBools[vystup_Manual_DBOffset][vystup_Manual_DBBit] ? getResources().getColor(R.color.trueGreen, getActivity().getTheme()) : getResources().getColor(R.color.falseRed, getActivity().getTheme()));
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
    private void setAllToRed(View v,int falseRed){
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
}