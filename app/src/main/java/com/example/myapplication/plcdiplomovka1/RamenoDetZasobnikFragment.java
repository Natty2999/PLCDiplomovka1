package com.example.myapplication.plcdiplomovka1;

import static java.lang.Math.floor;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import Moka7.S7;
import Moka7.S7Client;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RamenoDetZasobnikFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RamenoDetZasobnikFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int casovac_interval = 100;
    private int casovac_time = 120000;
    private int anim_called =0;
    private CountDownTimer countDownTimer;
    private int counter = 10;
    private MaterialSwitch switchReadRameno;

    private Button btn_start_rameno;
    private Button btn_stop_rameno;
    private Button btn_rameno_extruder_vysun;
    private Button btn_rameno_extruder_zasun;


    private TextView errorText;
    private TextView tv_nadpis_rameno;


    private ImageView iv_rameno_puk;
    private ImageView iv_rameno_extruder;

    // mozno aj rameno
    private ImageView iv_rameno_arm;
    private ImageView iv_center_circle;

    private boolean animateToZasobnikBuffer = false;
    private boolean animateToVytahBuffer = false;
    private boolean lastAnimationTask = false;

    private boolean animToVytahInProgress = false;
    private boolean animToZasobnikInProgress = false;
    private String ramenoIPAdresa;
    private String snimace_DBNumber;

    //inputs
    private TextView tv_rameno_inp_dom_poloha;
    private TextView tv_rameno_inp_vysunuty_extruder;
    private TextView tv_rameno_inp_prazdny_zasobnik;
    private TextView tv_rameno_inp_rameno_pri_z;

    private TextView tv_rameno_inp_is_on;

    //output
    private TextView tv_rameno_out_extruder;

    private TextView tv_rameno_out_manual_rameno;
    private TextView tv_rameno_out_auto_rameno;
    private TextView tv_rameno_out_polo_automaticky_rameno;

    //shared preferences sting values
    //Inputs
    //offset

    private Integer snimacDomPolohaDBOffset;
    private Integer snimacVysExtruderDBOffset;
    private Integer snimacPrazdnyZasobnikDBOffset;
    private Integer snimacRamenoPriZDBOffset;

    //bit

    private Integer snimacDomPolohaDBBit;
    private Integer snimacVysExtruderDBBit;
    private Integer snimacPrazdnyZasobnikDBBit;
    private Integer snimacRamenoPriZDBBit;

    //OutputInteger
    //offset
    private Integer vystupExtruderDBOffset;

    private Integer vystupManualRamenoDBOffset;
    private Integer vystupAutoRamenoDBOffset;
    private Integer vystupPoloAutomatickyRamenoDBOffset;

    private Integer vystupStartStopRamenoDBOffset;
    //bit
    private Integer vystupExtruderDBBit;

    private Integer vystupManualRamenoDBBit;
    private Integer vystupAutoRamenoDBBit;
    private Integer vystupPoloAutomatickyRamenoDBBit;

    private Integer vystupStartStopRamenoDBBit;
    private boolean connectingFirstTime = true;





    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String IP_ADRESA_RAMENO = "ipAdresaRameno";
    private static final String SNIMACE_DBNUMBER = "snimaceDBNumberRameno";
    //INPUTS
    //offset
    private static final String SNIMAC_DOM_POLOHA_DBOFFSET = "snimacDomPolohaDBOffset";
    private static final String SNIMAC_VYS_EXTRUDER_DBOFFSET = "snimacVysExtruderDBOffset";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET = "snimacPrazdnyZasobnikDBOffset";
    private static final String SNIMAC_RAMENO_PRI_Z_DBOFFSET = "snimacRamenoPriZDBOffset";

    //bit
    private static final String SNIMAC_DOM_POLOHA_DBBIT = "snimacDomPolohaDBBit";
    private static final String SNIMAC_VYS_EXTRUDER_DBBIT = "snimacVysExtruderDBBit";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBBIT = "snimacPrazdnyZasobnikDBBit";
    private static final String SNIMAC_RAMENO_PRI_Z_DBBIT = "snimacRamenoPriZDBBit";


    //OUTPUTS
    //offset
    private static final String VYSTUP_EXTRUDER_DBOFFSET = "vystupExtruderDBOffset";

    private static final String VYSTUP_MANUAL_RAMENO_DBOFFSET = "vystupManualRamenoDBOffset";
    private static final String VYSTUP_AUTO_RAMENO_DBOFFSET = "vystupAutoRamenoDBOffset";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET = "vystupPoloAutomatickyRamenoDBOffset";

    private static final String VYSTUP_START_STOP_RAMENO_DBOFFSET = "vystupStartStopRamenoDBOffset";
    //bit
    private static final String VYSTUP_EXTRUDER_DBBIT = "vystupExtruderDBBit";

    private static final String VYSTUP_MANUAL_RAMENO_DBBIT = "vystupManualRamenoDBBit";
    private static final String VYSTUP_AUTO_RAMENO_DBBIT = "vystupAutoRamenoDBBit";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT = "vystupPoloAutomatickyRamenoDBBit";
    private static final String VYSTUP_START_STOP_RAMENO_DBBIT = "vystupStartStopRamenoDBBit";





    public RamenoDetZasobnikFragment() {
        // Required empty public constructor
    }
    S7Client client = new S7Client();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RamenoDetZasobnikFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RamenoDetZasobnikFragment newInstance(String param1, String param2) {
        RamenoDetZasobnikFragment fragment = new RamenoDetZasobnikFragment();
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
    public void onPause() {
        super.onPause();
        if (switchReadRameno.isChecked()) {
            switchReadRameno.setChecked(false);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Vykonať nafúknutie layoutu pre tento fragment
        View view= inflater.inflate(R.layout.fragment_rameno_det_zasobnik, container, false);

        errorText = view.findViewById(R.id.errorText);

        tv_nadpis_rameno = view.findViewById(R.id.tv_Title);
        switchReadRameno = view.findViewById(R.id.sw_read_rameno);

        tv_rameno_inp_dom_poloha = view.findViewById(R.id.tv_rameno_inp_zasunuty_extruder_value);
        tv_rameno_inp_vysunuty_extruder = view.findViewById(R.id.tv_rameno_inp_vysunuty_extruder_value);
        tv_rameno_inp_prazdny_zasobnik = view.findViewById(R.id.tv_rameno_inp_prazdny_zasobnik_value);
        tv_rameno_inp_rameno_pri_z = view.findViewById(R.id.tv_rameno_inp_rameno_pri_zasobniku_value);
        tv_rameno_out_extruder = view.findViewById(R.id.tv_rameno_tv_rameno_out_vysunuty_extruder_value);

        tv_rameno_inp_is_on = view.findViewById(R.id.tv_rameno_mode_value);

        btn_rameno_extruder_vysun = view.findViewById(R.id.btn_extruder_out_true);
        btn_rameno_extruder_zasun = view.findViewById(R.id.btn_extruder_out_false);

        btn_start_rameno = view.findViewById(R.id.btn_rameno_start);
        btn_stop_rameno = view.findViewById(R.id.btn_rameno_stop);

        iv_rameno_puk = view.findViewById(R.id.iv_rameno_puck);
        iv_rameno_extruder = view.findViewById(R.id.iv_rameno_extruder);
        switchReadRameno.setOnCheckedChangeListener((v, event) -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    ((MainActivity) getActivity()).hideMenu();
                }
            }
            if (switchReadRameno.isChecked()) {
                IOMethods.CountdownRequest request = new IOMethods.CountdownRequest(casovac_time, casovac_interval, counter, countDownTimer, switchReadRameno, this::readPlc);
                countDownTimer = IOMethods.startCountdown(request);
            } else {
                IOMethods.cancelCountdown(countDownTimer);
                if (client != null && client.Connected){
                client.Disconnect();
                }
            }
        });
        btn_rameno_extruder_vysun.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    ((MainActivity) getActivity()).hideMenu();
                }
            }
            IOMethods.WritePlc(errorText, client, handler, executorService, ramenoIPAdresa, Integer.parseInt(snimace_DBNumber),vystupExtruderDBOffset, vystupExtruderDBBit, true);
            if (!switchReadRameno.isChecked()){
                switchReadRameno.setChecked(true);
            }
        });
        btn_rameno_extruder_zasun.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    ((MainActivity) getActivity()).hideMenu();
                }
            }
            IOMethods.WritePlc(errorText, client, handler, executorService, ramenoIPAdresa, Integer.parseInt(snimace_DBNumber),vystupExtruderDBOffset, vystupExtruderDBBit, false);
            if (!switchReadRameno.isChecked()){
                switchReadRameno.setChecked(true);
            }
        });
        btn_start_rameno.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    ((MainActivity) getActivity()).hideMenu();
                }
            }
            IOMethods.WritePlc(errorText, client, handler, executorService, ramenoIPAdresa, Integer.parseInt(snimace_DBNumber),vystupStartStopRamenoDBOffset, vystupStartStopRamenoDBBit, true);
            if (!switchReadRameno.isChecked()){
                switchReadRameno.setChecked(true);
            }
        });
        btn_stop_rameno.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                if (((MainActivity) getActivity()).bottomNavigationView.getVisibility() == View.VISIBLE){
                    ((MainActivity) getActivity()).hideMenu();
                }
            }
            IOMethods.WritePlc(errorText, client, handler, executorService, ramenoIPAdresa, Integer.parseInt(snimace_DBNumber),vystupStartStopRamenoDBOffset, vystupStartStopRamenoDBBit, false);
            if (!switchReadRameno.isChecked()){
                switchReadRameno.setChecked(true);
            }
        });
        loadData();
        readPlc();
        return view;
    }

    private void loadData(){
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
            tv_nadpis_rameno.setText(String.format("%s - %s", getResources().getString(R.string.menu_arm), sharedPreferences.getString(IP_ADRESA_RAMENO, "192.168.0.138")));
            ramenoIPAdresa = sharedPreferences.getString(IP_ADRESA_RAMENO, "192.168.0.138");
            snimace_DBNumber = sharedPreferences.getString(SNIMACE_DBNUMBER, "1");
            //inputs
            //offset
            snimacDomPolohaDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBOFFSET, "0"));
            snimacVysExtruderDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBOFFSET, "0"));
            snimacPrazdnyZasobnikDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET, "0"));
            snimacRamenoPriZDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBOFFSET, "0"));
            //bit
            snimacDomPolohaDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBBIT, "0"));
            snimacVysExtruderDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBBIT, "0"));
            snimacPrazdnyZasobnikDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBBIT, "0"));
            snimacRamenoPriZDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBBIT, "0"));
            //outputs
            vystupExtruderDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_EXTRUDER_DBOFFSET, "0"));
            vystupManualRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBOFFSET, "0"));
            vystupAutoRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBOFFSET, "0"));
            vystupPoloAutomatickyRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET, "0"));
            vystupStartStopRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBOFFSET, "0"));
            //bitInteger
            vystupExtruderDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_EXTRUDER_DBBIT, "0"));
            vystupManualRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBBIT, "0"));
            vystupAutoRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBBIT, "0"));
            vystupPoloAutomatickyRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT, "0"));
            vystupStartStopRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBBIT, "0"));

        }

    }
    /**
     * Metóda pre čítanie dát z PLC.
     * Táto metóda sa spúšťa v novom vlákne pomocou ExecutorService.
     * Ak nastane chyba pri pripojení alebo čítaní dát, chybová správa sa uloží do reťazca ret.
     * Po úspešnom prečítaní dát sa dáta prevedú na boolean hodnoty a uložia sa do dvojrozmerného poľa dataBools.
     * Nakoniec sa aktualizuje UI na hlavnom vlákne pomocou Handlera.
     * Ak je prepínač switchReadRameno vypnutý, metóda odpojí klienta.
     */
    private void readPlc() {
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[5];
        boolean[][] dataBools = new boolean[data.length][8];
        executorService.execute(() -> {
            try{
                if(client.getConnectionType()!= S7.S7_BASIC){
                    client.SetConnectionType(S7.S7_BASIC);
                }
                int res=1;
                //inputs
                int dbOffsetInputs = 0;
                if (!client.Connected){
                    if (connectingFirstTime){
                        res = client.ConnectTo(ramenoIPAdresa,0,2);// ak je S7-300 tak je vždy 0,2
                        connectingFirstTime = false;
                    }else {
                        res = client.Connect();
                    }
                }else {
                    res = 0;
                }
                if(res == 0){//ak je 0 tak je pripojenie úspešné
                    res = client.ReadArea(S7.S7AreaDB, Integer.parseInt(snimace_DBNumber),dbOffsetInputs,3,data);
                    for(int i = 0; i < data.length; i++){
                        boolean[] dataBool = new boolean[8];
                        for (int j = 0; j < dataBool.length; j++) {
                            dataBool[j] = S7.GetBitAt(data,i,j);
                        }
                        dataBools[i] = dataBool;
                    }
                }else{
                    System.out.println("Error: " + S7Client.ErrorText(res));
                    ret.set("Error: " + S7Client.ErrorText(res));
                }
                if(!switchReadRameno.isChecked()){
                    client.Disconnect();
                }
            }catch (Exception e){
                ret.set(e.getMessage());
                Thread.interrupted();
            }
            handler.post(() -> {
                // Aktualizácia používateľského rozhrania s výsledkom.
                if(ret.toString().toLowerCase().contains("error")){
                    errorText.setText(ret.toString());
                }else {
                    //inputs
                    //log dataBools[snimac_L_DBOffset][snimac_L_DBBit] in console

                    //tv_rameno_inp_dom_poloha.setTextColor(dataBools[snimacDomPolohaDBOffset][snimacDomPolohaDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_inp_vysunuty_extruder.setTextColor(dataBools[snimacVysExtruderDBOffset][snimacVysExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_inp_prazdny_zasobnik.setTextColor(dataBools[snimacPrazdnyZasobnikDBOffset][snimacPrazdnyZasobnikDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_inp_rameno_pri_z.setTextColor(dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));

                    tv_rameno_inp_dom_poloha.setText(dataBools[snimacDomPolohaDBOffset][snimacDomPolohaDBBit] ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));
                    tv_rameno_inp_vysunuty_extruder.setText(dataBools[snimacVysExtruderDBOffset][snimacVysExtruderDBBit] ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));
                    tv_rameno_inp_prazdny_zasobnik.setText(dataBools[snimacPrazdnyZasobnikDBOffset][snimacPrazdnyZasobnikDBBit] ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));
                    tv_rameno_inp_rameno_pri_z.setText(dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit] ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));

                    tv_rameno_inp_dom_poloha.setTextColor(dataBools[snimacDomPolohaDBOffset][snimacDomPolohaDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_vysunuty_extruder.setTextColor(dataBools[snimacVysExtruderDBOffset][snimacVysExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_prazdny_zasobnik.setTextColor(dataBools[snimacPrazdnyZasobnikDBOffset][snimacPrazdnyZasobnikDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_rameno_pri_z.setTextColor(dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));

                    boolean jeVoStope = dataBools[vystupStartStopRamenoDBOffset][vystupStartStopRamenoDBBit];
                    tv_rameno_inp_is_on.setText(jeVoStope ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));
                    tv_rameno_inp_is_on.setTextColor(jeVoStope ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //outputs
                    //tv_rameno_out_extruder.setTextColor(dataBools[vystupExtruderDBOffset][vystupExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_out_manual_rameno.setTextColor(dataBools[vystupManualRamenoDBOffset][vystupManualRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_out_auto_rameno.setTextColor(dataBools[vystupAutoRamenoDBOffset][vystupAutoRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //tv_rameno_out_polo_automaticky_rameno.setTextColor(dataBools[vystupPoloAutomatickyRamenoDBOffset][vystupPoloAutomatickyRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_extruder.setText(dataBools[vystupExtruderDBOffset][vystupExtruderDBBit] ? getResources().getString(R.string.value_true) : getResources().getString(R.string.value_false));
                    tv_rameno_out_extruder.setTextColor(dataBools[vystupExtruderDBOffset][vystupExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    iv_rameno_puk.setVisibility(dataBools[snimacPrazdnyZasobnikDBOffset][snimacPrazdnyZasobnikDBBit] ? View.INVISIBLE : View.VISIBLE);
                    if (dataBools[vystupExtruderDBOffset][vystupExtruderDBBit]) {
                        if (anim_called == 0) {
                            animatePiest(64, 400);
                            anim_called = 1;
                        }
                    } else {
                        if (anim_called == 1) {
                            animatePiest(0, 400);
                            anim_called = 0;
                        }
                    }
                    if (jeVoStope) {
                        //btn_start_rameno.setBackgroundColor(getResources().getColor(R.color.trueGreen, requireActivity().getTheme()));
                        //btn_stop_rameno.setBackgroundColor(getResources().getColor(R.color.falseRed, requireActivity().getTheme()));

                    }else {
                        //btn_start_rameno.setBackgroundColor(getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        //btn_stop_rameno.setBackgroundColor(getResources().getColor(R.color.trueGreen, requireActivity().getTheme()));
                    }

                    btn_rameno_extruder_vysun.setEnabled(!jeVoStope);
                    btn_rameno_extruder_zasun.setEnabled(!jeVoStope);

                    boolean ramenoPriZasobniku = dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit];
                }
            });
        });
    }
    /**
     * Metóda pre animáciu pohybu piestu.
     *
     * Táto metóda mení koncový okraj (margin) ImageView iv_rameno_extruder, čím sa dosiahne efekt animácie.
     *
     * @param positionToDp Cieľová pozícia piestu v dp (density-independent pixels). Táto hodnota sa prepočíta na pixely v závislosti od hustoty obrazovky zariadenia.
     * @param duration Trvanie animácie v milisekundách.
     */
    private void animatePiest(int positionToDp, int duration){
        // Získanie hustoty obrazovky
        float density = getResources().getDisplayMetrics().density;
        // Prevod cieľového umiestnenia z DP na pixely
        int positionTo = Math.round(positionToDp * density);

        // Získanie aktuálnych parametrov rozloženia
        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) iv_rameno_extruder.getLayoutParams();

        // Ak je cieľové umiestnenie už nastavené, vrátiť sa
        if(params.getMarginEnd() == positionTo){
            return;
        }

        // Vytvorenie ValueAnimatora, ktorý animuje pohyb od aktuálneho koncového okraja na 0
        ValueAnimator animator = ValueAnimator.ofInt(params.getMarginEnd(), positionTo);
        animator.addUpdateListener(animation -> {
            // Aktualizácia koncového okraja v parametroch rozloženia
            params.setMarginEnd((Integer) animation.getAnimatedValue());
            iv_rameno_extruder.setLayoutParams(params);
            iv_rameno_extruder.getParent().requestLayout();
        });

        // Nastavenie trvania animácie
        animator.setDuration(duration);

        // Spustenie animácie
        animator.start();
    }
}