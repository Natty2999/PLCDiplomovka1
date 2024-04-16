package com.example.myapplication.plcdiplomovka1;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import Moka7.*;


public class Testing extends Fragment {
    public Testing() {
        // Required empty public constructor
    }
    public static Testing newInstance(String param1, String param2) {
        Testing fragment = new Testing();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private Button buttonRead;
    private Button buttonWrite;
    private TextInputEditText et_offset;
    private TextInputEditText et_bit;
    private MaterialSwitch switchWrite;
    private TextView tv_result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private FloatingActionButton buttonMenu;
    private com.google.android.material.navigation.NavigationView navigationView;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_testing, container, false);

        buttonRead = view.findViewById(R.id.buttonRead);
        buttonWrite = view.findViewById(R.id.buttonWrite);
        et_offset = view.findViewById(R.id.et_test_offset);
        et_bit = view.findViewById(R.id.et_test_bit);
        switchWrite = view.findViewById(R.id.switchValue);
        buttonRead.setOnClickListener(v -> {
            ReadPlc();
        });
        buttonWrite.setOnClickListener(v -> {
        });
        tv_result = view.findViewById(R.id.tv_result);






        return view;
    }
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    S7Client client = new S7Client();
    private void ReadPlc() {
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[1];
        executorService.execute(() -> {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                //inputs
                int dbOffsetInputs = 0;

                int res = client.ConnectTo("192.168.55.105",0,2);// ak je S7-300 tak je vždy 0,2
                IntByRef status = new IntByRef(0);
                client.GetPlcStatus(status);
                String statusText = "";
                if (status.Value == S7.S7CpuStatusRun)
                    statusText = "RUN";
                else if (status.Value == S7.S7CpuStatusStop)
                    statusText = "STOP";
                else
                    statusText = "Unknown";

                System.out.println("Status: " + statusText);
                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    System.out.println("Connected");
                    res = client.ReadArea(S7.S7AreaPA,1,dbOffsetInputs,1,data);
                    boolean result = S7.GetBitAt(data,0,0);
                    ret.set("Read successful: " + result);
                    //log res in console
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
                    tv_result.setText(ret.toString());
                }else {
                    tv_result.setText(ret.toString());
                }
            });
        });
    }

}