package com.example.myapplication.plcdiplomovka1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.materialswitch.MaterialSwitch;

import Moka7.S7;
import Moka7.S7Client;
//import methods from MainActivity.java

public class RamenoActivity extends AppCompatActivity {
    private MaterialCheckBox checkBoxVytah0;
    private MaterialSwitch switch1;
    private TextView debugText;
    public String stringValue ="";
    public int dbNumber = 2;
    public int dbOffset = 0;
    public int dbBit = 0;
    private CountDownTimer countDownTimer;
    private int counter = 10;
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
            if (bottomNavigationView != null && bottomNavigationView.getSelectedItemId() != R.id.nav_rameno){
                bottomNavigationView.setSelectedItemId(R.id.nav_rameno);
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rameno, container, false);
        switchReadRameno = view.findViewById(R.id.sw_read_rameno);
        errorText = view.findViewById(R.id.errorText);
        tv_nadpis_rameno = view.findViewById(R.id.tv_nadpis_rameno);
        btn_start_rameno = view.findViewById(R.id.btn_start_rameno);
        btn_stop_rameno = view.findViewById(R.id.btn_stop_rameno);
        btn_rameno_kv = view.findViewById(R.id.btn_rameno_kv);
        btn_rameno_kz = view.findViewById(R.id.btn_rameno_kz);
        btn_rameno_extruder = view.findViewById(R.id.btn_rameno_extruder);
        btn_rameno_odfuk = view.findViewById(R.id.btn_rameno_odfuk);
        btn_rameno_prisavka = view.findViewById(R.id.btn_rameno_prisavka);


        tv_rameno_inp_prisiaty_vyrobok = view.findViewById(R.id.tv_rameno_inp_prisiaty_vyrobok);
        tv_rameno_inp_dom_poloha = view.findViewById(R.id.tv_rameno_inp_dom_poloha);
        tv_rameno_inp_vysunuty_extruder = view.findViewById(R.id.tv_rameno_inp_vysunuty_extruder);
        tv_rameno_inp_prazdny_zasobnik = view.findViewById(R.id.tv_rameno_inp_prazdny_zasobnik);
        tv_rameno_inp_rameno_pri_z = view.findViewById(R.id.tv_rameno_inp_rameno_pri_zasobniku);
        tv_rameno_inp_rameno_pri_v = view.findViewById(R.id.tv_rameno_inp_rameno_pri_vytahu);
        tv_rameno_inp_suc_na_v = view.findViewById(R.id.tv_rameno_inp_vedla_je_suc);
        tv_rameno_inp_rameno_vytah_dole = view.findViewById(R.id.tv_rameno_inp_vytah_dole);
        tv_rameno_out_extruder = view.findViewById(R.id.tv_rameno_out_extruder);
        tv_rameno_out_rameno_kz = view.findViewById(R.id.tv_rameno_out_rameno_kz);
        tv_rameno_out_rameno_kv = view.findViewById(R.id.tv_rameno_out_rameno_kv);
        tv_rameno_out_odfuk = view.findViewById(R.id.tv_rameno_out_odfuk);
        tv_rameno_out_prisavka = view.findViewById(R.id.tv_rameno_out_prisavka);
        tv_rameno_out_manual_rameno = view.findViewById(R.id.tv_rameno_out_manual_rameno);
        tv_rameno_out_auto_rameno = view.findViewById(R.id.tv_rameno_out_auto_rameno);
        tv_rameno_out_polo_automaticky_rameno = view.findViewById(R.id.tv_rameno_out_polo_automaticky_rameno);

        /**
         * List  textViews obsahujúci všetky textové polia, ktoré sa majú zobraziť alebo skryť po stlačení tlačidla buttonAnimateShow.
         */
        List<TextView> textViews = Arrays.asList(
                tv_rameno_inp_prisiaty_vyrobok,
                tv_rameno_inp_dom_poloha,
                tv_rameno_inp_vysunuty_extruder,
                tv_rameno_inp_prazdny_zasobnik,
                tv_rameno_inp_rameno_pri_z,
                tv_rameno_inp_rameno_pri_v,
                tv_rameno_inp_suc_na_v,
                tv_rameno_inp_rameno_vytah_dole,

                tv_rameno_out_extruder,
                tv_rameno_out_rameno_kz,
                tv_rameno_out_rameno_kv,
                tv_rameno_out_odfuk,
                tv_rameno_out_prisavka,
                tv_rameno_out_manual_rameno,
                tv_rameno_out_auto_rameno,
                tv_rameno_out_polo_automaticky_rameno
        );
        iv_rameno_arm = view.findViewById(R.id.iv_rameno_arm);
        iv_rameno_puk = view.findViewById(R.id.iv_rameno_puk);
        iv_center_circle = view.findViewById(R.id.iv_center_circle);
        buttonAnimateShow= view.findViewById(R.id.btn_animate_show);
        loadData();
        /**
         * Metóda setOnCheckedChangeListener slúži na zistenie, či je switchReadRameno zapnutý alebo vypnutý.
         * Ak je zapnutý, spustí sa metóda startCountdown, ktorá spustí časovač, ktorý každých casovac_interval milisekúnd
         * zavolá metódu ReadPlc.
         * Ak je vypnutý, zruší sa časovač.
         */
        switchReadRameno.setOnCheckedChangeListener((v, event) -> {
                if (switchReadRameno.isChecked()) {
                    startCountdown();
                } else {
                    cancelCountdown();
                }
        });

    }
    S7Client client = new S7Client();
    private void startCountdown() {
        cancelCountdown(); // Cancel any existing countdown
        countDownTimer = new CountDownTimer(10000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI with current count
                new RamenoActivity.PlcReader().execute();
                //System.out.println("Countdown: " + counter);
                counter--;
                debugText.setText("Countdown: " + counter);
            }

            @Override
            public void onFinish() {
                //System.out.println("Countdown finished");
                counter = 100; // Reset counter
                switch1.setChecked(false); // Turn off the switch after countdown finishes
            }
        };
        countDownTimer.start();
    }
    private void cancelCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    public void writeDB0(View view){
        new RamenoActivity.PlcWriter().execute();
    }
    public void readDB0(View view){
        new RamenoActivity.PlcReader().execute();
    }

    private class PlcReader extends AsyncTask<String,Void,String> {
        String ret = "";

        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);

                String ipAddress = "192.168.0.138";

                int res = client.ConnectTo(ipAddress,0,2);// ak je S7-300 tak je vždy 0,2

                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    //stringValue += "Connected" + "\n";
                    byte[] data = new byte[1];
                    res = client.ReadArea(S7.S7AreaDB,dbNumber,dbOffset,1,data);
                    ret = String.valueOf(S7.GetBitAt(data,0,dbBit));
                }else{
                    //stringValue += "res == " + res + "\n";
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
            //EditText textInputReadValue = (EditText) findViewById(R.id.TextInputReadValue);
            //log in console

            String value = String.valueOf(checkBoxVytah0.isChecked());
            String DBAddress = "DB" + dbNumber + ".DBB" + dbOffset + "." + dbBit+ " = ";

            if (!value.equalsIgnoreCase(ret)){
                stringValue = ret;
                if(stringValue.toLowerCase().contains("error")){
                    debugText.setText(stringValue);
                }
                else{
                    checkBoxVytah0.setChecked(ret.equalsIgnoreCase("true"));
                }

            }
            else{
                //code here
            }
        }
    }
    private class PlcWriter extends AsyncTask<String,Void,String> {
        String ret = "";
        @Override
        protected String doInBackground(String... params) {
            try {
                client.SetConnectionType(S7.S7_BASIC);
                boolean writeValue;
                String ipAddress = "192.168.0.138";
                writeValue = checkBoxVytah0.isChecked();

                int res = client.ConnectTo(ipAddress, 0, 2);// ak je S7-300 tak je vždy 0,2
                if (res == 0) {//ak je 0 tak je pripojenie úspešné
                    //stringValue += "Connected" + "\n";
                    byte[] data = new byte[1];
                    res = client.ReadArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                    if (res == 0) {
                        boolean bitValue = S7.GetBitAt(data, 0, dbBit);
                        S7.SetBitAt(data, 0, dbBit, !bitValue);
                        res = client.WriteArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                        if (res == 0) {
                            res = client.ReadArea(S7.S7AreaDB,dbNumber,dbOffset,1,data);
                            if (res == 0) {
                                ret = String.valueOf(S7.GetBitAt(data,0,dbBit));
                                if (ret.equalsIgnoreCase(String.valueOf(writeValue))){
                                    ret = "Zápis úspešný";
                                }else{
                                    ret = "Error: Zápis sa nepodaril možno sa snažíte zapisovať do input bitu data bloku";
                                }
                            }else {
                                ret = "Error: " + S7Client.ErrorText(res);
                            }
                        } else {
                            ret = "Error: " + S7Client.ErrorText(res);
                        }
                    } else {
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
            debugText.setText(ret);
        }
    }
}