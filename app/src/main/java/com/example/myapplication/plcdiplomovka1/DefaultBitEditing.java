package com.example.myapplication.plcdiplomovka1;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import Moka7.*;
import com.google.android.material.materialswitch.MaterialSwitch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DefaultBitEditing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultBitEditing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public String stringValue ="";
    private Button buttonNextPage;
    private Button buttonWrite;
    private Button buttonRead;

    private MaterialSwitch switchRead;
    private EditText textInputDBNumber;
    private EditText textInputDBOffset;
    private EditText textInputDBBit;
    private EditText textInputIPAddress;
    private EditText textInputWriteValue;
    private EditText textInputReadValue;
    private CountDownTimer countDownTimer;
    private TextView errorText;
    private int counter = 10;
    public DefaultBitEditing() {
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
    public static DefaultBitEditing newInstance(String param1, String param2) {
        DefaultBitEditing fragment = new DefaultBitEditing();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_bit_editing, container, false);
        // Inflate the layout for this fragment
        buttonNextPage = view.findViewById(R.id.buttonNextPage);
        switchRead = view.findViewById(R.id.switchRead);
        textInputDBNumber = view.findViewById(R.id.TextInputDBNumber);
        textInputDBOffset = view.findViewById(R.id.TextInputDBOffset);
        textInputDBBit = view.findViewById(R.id.TextInputDBBit);
        textInputIPAddress = view.findViewById(R.id.TextInputIPAddress);
        textInputWriteValue = view.findViewById(R.id.TextInputWriteValue);
        textInputReadValue = view.findViewById(R.id.TextInputReadValue);
        errorText = view.findViewById(R.id.errorText);
        buttonWrite = view.findViewById(R.id.buttonWrite);
        buttonRead = view.findViewById(R.id.buttonRead);
        switchRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                startCountdown();
                counter =100;
            } else {
                cancelCountdown();
            }
        });
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDB(v);
            }
        });
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeDB(v);
            }
        });

        return view;
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
    public void writeDB(View view){
        new PlcWriter().execute();
    }
    public void readDB(View view){
        new PlcReader().execute();
    }

    private class PlcReader extends AsyncTask<String,Void,String> {
        String ret = "";

        @Override
        protected String doInBackground(String... params) {
            try{
                client.SetConnectionType(S7.S7_BASIC);

                String ipAddress = textInputIPAddress.getText().toString();


                int dbNumber = Integer.parseInt(textInputDBNumber.getText().toString());
                int dbOffset = Integer.parseInt(textInputDBOffset.getText().toString());
                int dbBit = Integer.parseInt(textInputDBBit.getText().toString());

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
                int dbNumber = Integer.parseInt(textInputDBNumber.getText().toString());
                int dbOffset = Integer.parseInt(textInputDBOffset.getText().toString());
                int dbBit = Integer.parseInt(textInputDBBit.getText().toString());
                boolean isWriteValueValid = textInputWriteValue.getText().toString().equalsIgnoreCase("true")
                        || textInputWriteValue.getText().toString().equalsIgnoreCase("false")
                        || textInputWriteValue.getText().toString().equals("0")
                        || textInputWriteValue.getText().toString().equals("1");
                if (!isWriteValueValid) {
                    ret = "Chyba: Hodnota na zápis musí byť boolean alebo 0 alebo 1";
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
            errorText.setText(ret);
        }
    }

}