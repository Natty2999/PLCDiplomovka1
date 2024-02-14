package com.example.myapplication.plcdiplomovka1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import Moka7.*;
//TODO pridat Toasty na upozornenie prebehnutia deju
//TODO pridat kontrolu ci je IP adresa spravne zadaná (spravny format)
//TODO pridat kontrolu ci je DBNumber, DBOffset a DBBit spravne zadané (čísla), bit moze byt 0-7
//TODO pridat kontrolu ci je WriteValue spravne zadaný (true, false, 0, 1) !done
//TODO pozriet ako sa pridavaju dalsie View do aplikacie
//TODO pridat uchovanie fungujucich IP adries s moznostou pomenovania a vyberu z listu

public class MainActivity extends AppCompatActivity {
public String stringValue ="";
    private Switch switchRead;
    private EditText textInputDBNumber;
    private EditText textInputDBOffset;
    private EditText textInputDBBit;
    private EditText textInputIPAddress;
    private EditText textInputWriteValue;
    private CountDownTimer countDownTimer;
    private TextView errorText;
    private int counter = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchRead = findViewById(R.id.SwitchRead);
        textInputDBNumber = findViewById(R.id.TextInputDBNumber);
        textInputDBOffset = findViewById(R.id.TextInputDBOffset);
        textInputDBBit = findViewById(R.id.TextInputDBBit);
        textInputIPAddress = findViewById(R.id.TextInputIPAddress);
        textInputWriteValue = findViewById(R.id.TextInputWriteValue);
        errorText = findViewById(R.id.ErrorText);

        switchRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startCountdown();
                counter =100;
            } else {
                cancelCountdown();
            }
        });
    }
    private void startCountdown() {
        cancelCountdown(); // Cancel any existing countdown
        countDownTimer = new CountDownTimer(10000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update UI with current count
                new PlcReader().execute();
                System.out.println("Countdown: " + counter);
                counter--;
            }

            @Override
            public void onFinish() {
                System.out.println("Countdown finished");
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
    public void writeDB(View view){
        new PlcWriter().execute();
    }
    public void readDB(View view){
        new PlcReader().execute();
    }
    public void readDB2(View view){
        String ret = "";
        try{
        client.SetConnectionType(S7.S7_BASIC);
        int res = client.ConnectTo("192.168.0.138",0,2);// ak je S7-300 tak je vždy 0,2
        if(res == 0){//ak je 0 tak je pripojenie úspešné
            //stringValue += "Connected" + "\n";
            byte[] data = new byte[1];
            res = client.ReadArea(S7.S7AreaDB,1,0,1,data);
            ret = String.valueOf(S7.GetBitAt(data,0,0));
        }else{
            //stringValue += "res == " + res + "\n";
            ret = "Error: " + S7Client.ErrorText(res);
        }
        client.Disconnect();
    }catch (Exception e){
        ret = e.getMessage();
        Thread.interrupted();
    }
        EditText textInputReadValue = (EditText) findViewById(R.id.TextInputReadValue);
        textInputReadValue.setText(ret);
    }
    public void readDBContinuous(View view){
        int i = 0;
        String ret;
        EditText textInputReadValue = (EditText) findViewById(R.id.TextInputReadValue);
        System.out.println(switchRead.isChecked());
        while (switchRead.isChecked() && i < 30){
            System.out.println(i + " " + switchRead.isChecked());
            if(!switchRead.isChecked()){
                System.out.println("break");
                break;
            }
            i += 1;
            textInputReadValue.setText(i + " " + switchRead.isChecked());

            //new PlcReader().execute();

        }
    }
    private class PlcReader extends AsyncTask<String,Void,String> {
        String ret = "";
        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);

                String ipAddress = textInputIPAddress.getText().toString();


                Integer dbNumber = Integer.parseInt(textInputDBNumber.getText().toString());
                Integer dbOffset = Integer.parseInt(textInputDBOffset.getText().toString());
                Integer dbBit = Integer.parseInt(textInputDBBit.getText().toString());

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
            EditText textInputReadValue = (EditText) findViewById(R.id.TextInputReadValue);
            //log in console

            String value = textInputReadValue.getText().toString();
            String DBAddress = "DB" + textInputDBNumber.getText().toString() + ".DBB" + textInputDBOffset.getText().toString() + "." + textInputDBBit.getText().toString()+ " = ";

            if (!value.equalsIgnoreCase(DBAddress + ret)){
                if(ret.toLowerCase().contains("error")){//ak vypisujem error nedavaj tam ciso datoveho blokui
                    stringValue = ret;
                }
                else{
                    Integer dbNumber = Integer.parseInt(textInputDBNumber.getText().toString());
                    Integer dbOffset = Integer.parseInt(textInputDBOffset.getText().toString());
                    Integer dbBit = Integer.parseInt(textInputDBBit.getText().toString());

                    stringValue = DBAddress  + ret;
                }
                if(stringValue.toLowerCase().contains("error")){
                    errorText.setText(stringValue);
                }
                else{
                    textInputReadValue.setText(stringValue);
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
                String ipAddress = textInputIPAddress.getText().toString();
                Integer dbNumber = Integer.parseInt(textInputDBNumber.getText().toString());
                Integer dbOffset = Integer.parseInt(textInputDBOffset.getText().toString());
                Integer dbBit = Integer.parseInt(textInputDBBit.getText().toString());
                boolean isWriteValueValid = textInputWriteValue.getText().toString().equalsIgnoreCase("true")
                            || textInputWriteValue.getText().toString().equalsIgnoreCase("false")
                            || textInputWriteValue.getText().toString().equals("0")
                            || textInputWriteValue.getText().toString().equals("1");
                if (!isWriteValueValid) {
                    ret = "Error: Write value must be a boolean or 0 or 1";
                    return "executed";
                }else{
                   writeValue = textInputWriteValue.getText().toString().equalsIgnoreCase("true")
                            || textInputWriteValue.getText().toString().equals("1");
                }

                int res = client.ConnectTo(ipAddress, 0, 2);// ak je S7-300 tak je vždy 0,2
                if (res == 0) {//ak je 0 tak je pripojenie úspešné
                    //stringValue += "Connected" + "\n";
                    byte[] data = new byte[1];
                    res = client.ReadArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                    if (res == 0) {
                        S7.SetBitAt(data, 0, dbBit, writeValue);
                        res = client.WriteArea(S7.S7AreaDB, dbNumber, dbOffset, 1, data);
                        if (res == 0) {
                            ret = "Write successful";
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
            errorText.setText(ret);
        }
    }
}