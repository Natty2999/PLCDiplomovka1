package com.example.myapplication.plcdiplomovka1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.materialswitch.MaterialSwitch;

import Moka7.S7;
import Moka7.S7Client;
//import methods from MainActivity.java

public class MainActivityVytahOvladanie extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_vytah_ovladanie);
        checkBoxVytah0 = findViewById(R.id.checkBox0);
        debugText = findViewById(R.id.DebugText);
        switch1 = findViewById(R.id.switch1);
        checkBoxVytah0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDB0(v);
            }
        });
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startCountdown();
                counter =100;
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
                new MainActivityVytahOvladanie.PlcReader().execute();
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
        new MainActivityVytahOvladanie.PlcWriter().execute();
    }
    public void readDB0(View view){
        new MainActivityVytahOvladanie.PlcReader().execute();
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
                        S7.SetBitAt(data, 0, dbBit, writeValue);
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