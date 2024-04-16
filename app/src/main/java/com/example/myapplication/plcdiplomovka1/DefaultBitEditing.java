package com.example.myapplication.plcdiplomovka1;

import static java.lang.Math.floor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Moka7.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

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

    private MaterialSwitch switchRead;
    private EditText textInputDBNumber;
    private EditText textInputDBOffset;
    private EditText textInputDBBit;
    private EditText textInputTrvanie;
    private EditText textInputInterval;
    private EditText textInputIPAddress;
    private EditText textInputWriteValue;
    private EditText textInputReadValue;
    private CountDownTimer countDownTimer;
    private TextView errorText;
    private TextView tv_info;

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String IP_ADRESA_VYTAH = "ipAdresaVytah";
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
    public void onResume() {
        super.onResume();
        loadData();
        // Get the MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // Get the BottomNavigationView from the MainActivity
            BottomNavigationView bottomNavigationView = mainActivity.findViewById(R.id.bottomNavigationView);
            // Set the selected item
            if (bottomNavigationView != null && bottomNavigationView.getSelectedItemId() != R.id.nav_home){
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
                //Toast.makeText(getActivity(), "Set nav home", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default_bit_editing, container, false);

        // Inflate the layout for this fragment
        //buttonNextPage = view.findViewById(R.id.buttonNextPage);

        textInputIPAddress = view.findViewById(R.id.TextInputIPAddress);
        errorText = view.findViewById(R.id.errorText);
        tv_info = view.findViewById(R.id.tv_info);
        Button buttonRead = view.findViewById(R.id.buttonRead);



        buttonRead.setOnClickListener(v -> readPlc());

        loadData();
        return view;


    }
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    private void readPlc() {
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[5];
        boolean[][] dataBools = new boolean[data.length][8];
        executorService.execute(() -> {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                //inputs


                int res = client.ConnectTo(textInputIPAddress.getText().toString(),0,2);// ak je S7-300 tak je vždy 0,2
                int resOrderCode = 0;
                if(res == 0){//ak je 0 tak je pripojenie úspešné
                //get info about plc
                    S7CpuInfo info = new S7CpuInfo();

                    res = client.GetCpuInfo(info);
                    if(res == 0){
                        ret.set("Model: " + info.ModuleTypeName() + "\n" +
                                "Serial number: " + info.SerialNumber() + "\n" +
                                "AS name: " + info.ASName() + "\n" +
                                "Module name: " + info.ModuleName() + "\n" +
                                "Copy right: " + info.Copyright() + "\n");
                    }else {
                        ret.set("Error: " + S7Client.ErrorText(res));
                    }

                    S7OrderCode orderCode = new S7OrderCode();
                    resOrderCode = client.GetOrderCode(orderCode);
                    if(resOrderCode == 0){
                        ret.set(ret + "Order code: " + orderCode.Code() + "\n");}
                    else {
                        ret.set(ret + "Error: " + S7Client.ErrorText(resOrderCode) + "\n");
                    }
                    S7Protection s7Protection = new S7Protection();
                    res = client.GetProtection(s7Protection);
                    if(res == 0){
                        ret.set(ret + "Protection: " + s7Protection.sch_schal + "\n");
                    }else {
                        ret.set(ret + "Error: " + S7Client.ErrorText(res) + "\n");
                    }
                    IntByRef status = new IntByRef(0);
                    res = client.GetPlcStatus(status);
                    if(res == 0) {
                        if (status.Value == S7.S7CpuStatusRun){
                            ret.set(ret + "Status: " +  "RUN" + "\n");
                        }
                        else if(status.Value == S7.S7CpuStatusStop){
                            ret.set(ret + "Status: " + "STOP" + "\n");
                        } else if (status.Value == S7.S7CpuStatusUnknown){
                            ret.set(ret + "Status: " + "STATUS UNKNOWN" + "\n");
                        }
                    }else {
                        ret.set(ret + "Error: " + S7Client.ErrorText(res) + "\n");
                    }
                }else{
                    System.out.println("Error: " + S7Client.ErrorText(res));
                    ret.set("Error: " + S7Client.ErrorText(res));
                }
                client.Disconnect();
            }catch (Exception e){
                ret.set(e.getMessage());
                Thread.interrupted();
            }

            handler.post(() -> {
                // UI updates with result.
                if(ret.toString().toLowerCase().contains("error")){
                    errorText.setText(ret.toString());
                }else {
                    tv_info.setText(ret.toString());
                }
            });
        });
    }

    S7Client client = new S7Client();


    private void loadData(){
        if (getActivity()!=null){

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            textInputIPAddress.setText(sharedPreferences.getString(IP_ADRESA_VYTAH, "192.168.0.138"));
            //Toast.makeText(getContext(), "Dáta načítané.", Toast.LENGTH_SHORT).show();
        }

    }
}