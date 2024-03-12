package com.example.myapplication.plcdiplomovka1;

import static java.lang.Math.floor;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.widget.Toast;

import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.atomic.AtomicReference;

import Moka7.S7;
import Moka7.S7Client;

public class RamenoFragment extends Fragment {
    private int casovac_interval = 100;
    private int casovac_time = 120000;
    private int anim_called =0;
    private CountDownTimer countDownTimer;
    private int counter = 10;
    private MaterialSwitch switchReadRameno;
    private Button btn_start_rameno;
    private Button btn_stop_rameno;

    private Button btn_rameno_kv;
    private Button btn_rameno_kz;

    private Button btn_rameno_extruder;
    private Button btn_rameno_odfuk;
    private Button btn_rameno_prisavka;
    private Button buttonAnimateShow;
    private TextView errorText;
    private TextView tv_nadpis_rameno;
    private ImageView iv_rameno_arm;
    private ImageView iv_rameno_puk;
    private ImageView iv_center_circle;
    private boolean animateToZasobnikBuffer = false;
    private boolean animateToVytahBuffer = false;
    private boolean lastAnimationTask = false;

    private boolean animToVytahInProgress = false;
    private boolean animToZasobnikInProgress = false;
    private String ramenoIPAdresa;
    private String snimace_DBNumber;

    //inputs
    private TextView tv_rameno_inp_prisiaty_vyrobok;
    private TextView tv_rameno_inp_dom_poloha;
    private TextView tv_rameno_inp_vysunuty_extruder;
    private TextView tv_rameno_inp_prazdny_zasobnik;
    private TextView tv_rameno_inp_rameno_pri_z;
    private TextView tv_rameno_inp_rameno_pri_v;
    private TextView tv_rameno_inp_suc_na_v;
    private TextView tv_rameno_inp_rameno_vytah_dole;
    //output
    private TextView tv_rameno_out_extruder;
    private TextView tv_rameno_out_rameno_kz;
    private TextView tv_rameno_out_rameno_kv;
    private TextView tv_rameno_out_odfuk;
    private TextView tv_rameno_out_prisavka;
    private TextView tv_rameno_out_manual_rameno;
    private TextView tv_rameno_out_auto_rameno;
    private TextView tv_rameno_out_polo_automaticky_rameno;

    //shared preferences sting values
    //Inputs
    //offset
    private Integer snimacPrisiatyDBOffset;
    private Integer snimacDomPolohaDBOffset;
    private Integer snimacVysExtruderDBOffset;
    private Integer snimacPrazdnyZasobnikDBOffset;
    private Integer snimacRamenoPriZDBOffset;
    private Integer snimacRamenoPriVDBOffset;
    private Integer snimacSucNaVDBOffset;
    private Integer snimacRVytahDoleDBOffset;
    //bit
    private Integer snimacPrisiatyDBBit;
    private Integer snimacDomPolohaDBBit;
    private Integer snimacVysExtruderDBBit;
    private Integer snimacPrazdnyZasobnikDBBit;
    private Integer snimacRamenoPriZDBBit;
    private Integer snimacRamenoPriVDBBit;
    private Integer snimacSucNaVDBBit;
    private Integer snimacRVytahDoleDBBit;

    //OutputInteger
    //offset
    private Integer vystupExtruderDBOffset;
    private Integer vystupRamenoKzDBOffset;
    private Integer vystupRamenoKvDBOffset;
    private Integer vystupOdfukDBOffset;
    private Integer vystupPrisavkaDBOffset;
    private Integer vystupManualRamenoDBOffset;
    private Integer vystupAutoRamenoDBOffset;
    private Integer vystupPoloAutomatickyRamenoDBOffset;
    private Integer vystupStartStopRamenoDBOffset;
    //bit
    private Integer vystupExtruderDBBit;
    private Integer vystupRamenoKzDBBit;
    private Integer vystupRamenoKvDBBit;

    private Integer vystupOdfukDBBit;
    private Integer vystupPrisavkaDBBit;
    private Integer vystupManualRamenoDBBit;
    private Integer vystupAutoRamenoDBBit;
    private Integer vystupPoloAutomatickyRamenoDBBit;
    private Integer vystupStartStopRamenoDBBit;






    private static final String SHARED_PREFS = "sharedPrefs";

    private static final String IP_ADRESA_RAMENO = "ipAdresaRameno";
    private static final String SNIMACE_DBNUMBER = "snimaceDBNumberRameno";
    //INPUTS
    //offset
    private static final String SNIMAC_PRISIATY_DBOFFSET = "snimacPrisiatyDBOffset";
    private static final String SNIMAC_DOM_POLOHA_DBOFFSET = "snimacDomPolohaDBOffset";
    private static final String SNIMAC_VYS_EXTRUDER_DBOFFSET = "snimacVysExtruderDBOffset";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET = "snimacPrazdnyZasobnikDBOffset";
    private static final String SNIMAC_RAMENO_PRI_Z_DBOFFSET = "snimacRamenoPriZDBOffset";
    private static final String SNIMAC_RAMENO_PRI_V_DBOFFSET = "snimacRamenoPriVDBOffset";
    private static final String SNIMAC_SUC_NA_V_DBOFFSET = "snimacSucNaVDBOffset";
    private static final String SNIMAC_RAMENO_VYTAH_DOLE_DBOFFSET = "snimacRVytahDoleDBOffset";
    //bit
    private static final String SNIMAC_PRISIATY_DBBIT = "snimacPrisiatyDBBit";
    private static final String SNIMAC_DOM_POLOHA_DBBIT = "snimacDomPolohaDBBit";
    private static final String SNIMAC_VYS_EXTRUDER_DBBIT = "snimacVysExtruderDBBit";
    private static final String SNIMAC_PRAZDNY_ZASOBNIK_DBBIT = "snimacPrazdnyZasobnikDBBit";
    private static final String SNIMAC_RAMENO_PRI_Z_DBBIT = "snimacRamenoPriZDBBit";
    private static final String SNIMAC_RAMENO_PRI_V_DBBIT = "snimacRamenoPriVDBBit";
    private static final String SNIMAC_SUC_NA_V_DBBIT = "snimacSucNaVDBBit";
    private static final String SNIMAC_R_VYTAH_DOLE_DBBIT = "snimacRVytahDoleDBBit";

    //OUTPUTS
    //offset
    private static final String VYSTUP_EXTRUDER_DBOFFSET = "vystupExtruderDBOffset";
    private static final String VYSTUP_RAMENO_KZ_DBOFFSET = "vystupRamenoKzDBOffset";
    private static final String VYSTUP_RAMENO_KV_DBOFFSET = "vystupRamenoKvDBOffset";
    private static final String VYSTUP_ODFUK_DBOFFSET = "vystupOdfukDBOffset";
    private static final String VYSTUP_PRISAVKA_DBOFFSET = "vystupPrisavkaDBOffset";
    private static final String VYSTUP_MANUAL_RAMENO_DBOFFSET = "vystupManualRamenoDBOffset";
    private static final String VYSTUP_AUTO_RAMENO_DBOFFSET = "vystupAutoRamenoDBOffset";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET = "vystupPoloAutomatickyRamenoDBOffset";
    private static final String VYSTUP_START_STOP_RAMENO_DBOFFSET = "vystupStartStopRamenoDBOffset";
    //bit
    private static final String VYSTUP_EXTRUDER_DBBIT = "vystupExtruderDBBit";
    private static final String VYSTUP_RAMENO_KZ_DBBIT = "vystupRamenoKzDBBit";
    private static final String VYSTUP_RAMENO_KV_DBBIT = "vystupRamenoKvDBBit";
    private static final String VYSTUP_ODFUK_DBBIT = "vystupOdfukDBBit";
    private static final String VYSTUP_PRISAVKA_DBBIT = "vystupPrisavkaDBBit";
    private static final String VYSTUP_MANUAL_RAMENO_DBBIT = "vystupManualRamenoDBBit";
    private static final String VYSTUP_AUTO_RAMENO_DBBIT = "vystupAutoRamenoDBBit";
    private static final String VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT = "vystupPoloAutomatickyRamenoDBBit";
    private static final String VYSTUP_START_STOP_RAMENO_DBBIT = "vystupStartStopRamenoDBBit";


    public RamenoFragment() {
        // Required empty public constructor
    }
    S7Client client = new S7Client();

    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
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
        int duration = 1710;
        iv_center_circle.setRotation(180);
        animToZasobnikInProgress = false;
        animToVytahInProgress = false;

        btn_start_rameno.setOnClickListener(v -> {
            IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupStartStopRamenoDBOffset, vystupStartStopRamenoDBBit, true);//start rameno
            ReadPlc();
        });
        btn_stop_rameno.setOnClickListener(v -> {
            IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupStartStopRamenoDBOffset, vystupStartStopRamenoDBBit, false);//stop rameno
            ReadPlc();
        });
        btn_rameno_kz.setOnClickListener(v -> {
            if(IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupRamenoKvDBOffset, vystupRamenoKvDBBit, false) == 0){
                IOMethods.ToggleWriteBit(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupRamenoKzDBOffset, vystupRamenoKzDBBit);//rameno kz
            }//rameno kz
        });
        btn_rameno_kv.setOnClickListener(v -> {
            if(IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupRamenoKzDBOffset, vystupRamenoKzDBBit, false) == 0){
                IOMethods.ToggleWriteBit(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupRamenoKvDBOffset, vystupRamenoKvDBBit);//rameno kv
            }//rameno kv
        });

        btn_rameno_extruder.setOnClickListener(v -> {
            IOMethods.ToggleWriteBit(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupExtruderDBOffset, vystupExtruderDBBit);//extruder
        });
        btn_rameno_odfuk.setOnClickListener(v -> {
           if (IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupPrisavkaDBOffset, vystupPrisavkaDBBit, false) == 0){//prisavka
               IOMethods.ToggleWriteBit(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupOdfukDBOffset, vystupOdfukDBBit);//odfuk
           }

        });
        btn_rameno_prisavka.setOnClickListener(v -> {
            if (IOMethods.WritePlc(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupOdfukDBOffset, vystupOdfukDBBit, false) == 0){
                IOMethods.ToggleWriteBit(errorText,client,handler,executorService,ramenoIPAdresa, Integer.parseInt(snimace_DBNumber), vystupPrisavkaDBOffset, vystupPrisavkaDBBit);//prisavka
            }//odfuk

        });
        /**
         * Metóda buttonAnimateShow.setOnClickListener slúži na zobrazenie alebo skrytie všetkých textových polí, ktoré sú v liste textViews.
         */
        buttonAnimateShow.setOnClickListener(v -> {
            if (textViews.get(0).getVisibility() == View.VISIBLE) {
                buttonAnimateShow.setText(R.string.action_show);
                buttonAnimateShow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_minimize_24, 0);
                for (TextView textView : textViews) {
                        textView.setVisibility(View.GONE);
                }
            } else {
                for (TextView textView : textViews) {
                    textView.setVisibility(View.VISIBLE);
                }
                buttonAnimateShow.setText(R.string.action_hide);
                buttonAnimateShow.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_play_arrow_24, 0);
            }
        });
        //get color from resources
        setAllToRed(getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
        ReadPlc();
        return view;
    }
    /**
     * Metóda loadData slúži na načítanie uložených hodnôt z SharedPreferences.
     * Tieto hodnoty zahŕňajú IP adresu PLC, číslo databloku, offsety a bity pre vstupy a výstupy PLC.
     * Po načítaní týchto hodnôt sú uložené do príslušných premenných triedy.
     * Ak aktivita nie je null, metóda tiež nastaví textové pole tv_nadpis_rameno na IP adresu PLC.
     */
    public void loadData(){
        if (getActivity() != null) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        tv_nadpis_rameno.setText(String.format("%s - %s", getResources().getString(R.string.menu_arm), sharedPreferences.getString(IP_ADRESA_RAMENO, "192.168.0.138")));
        ramenoIPAdresa = sharedPreferences.getString(IP_ADRESA_RAMENO, "192.168.0.138");
        snimace_DBNumber = sharedPreferences.getString(SNIMACE_DBNUMBER, "1");
        //inputs
            //offset
            snimacPrisiatyDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRISIATY_DBOFFSET, "0"));
            snimacDomPolohaDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBOFFSET, "0"));
            snimacVysExtruderDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBOFFSET, "0"));
            snimacPrazdnyZasobnikDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBOFFSET, "0"));
            snimacRamenoPriZDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBOFFSET, "0"));
            snimacRamenoPriVDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_V_DBOFFSET, "0"));
            snimacSucNaVDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_SUC_NA_V_DBOFFSET, "0"));
            snimacRVytahDoleDBOffset = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_VYTAH_DOLE_DBOFFSET, "0"));
            //bit
            snimacPrisiatyDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRISIATY_DBBIT, "0"));
            snimacDomPolohaDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_DOM_POLOHA_DBBIT, "0"));
            snimacVysExtruderDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_VYS_EXTRUDER_DBBIT, "0"));
            snimacPrazdnyZasobnikDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_PRAZDNY_ZASOBNIK_DBBIT, "0"));
            snimacRamenoPriZDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_Z_DBBIT, "0"));
            snimacRamenoPriVDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_RAMENO_PRI_V_DBBIT, "0"));
            snimacSucNaVDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_SUC_NA_V_DBBIT, "0"));
            snimacRVytahDoleDBBit = Integer.parseInt(sharedPreferences.getString(SNIMAC_R_VYTAH_DOLE_DBBIT, "0"));
        //outputs

            vystupExtruderDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_EXTRUDER_DBOFFSET, "0"));
            vystupRamenoKzDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_RAMENO_KZ_DBOFFSET, "0"));
            vystupRamenoKvDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_RAMENO_KV_DBOFFSET, "0"));
            vystupOdfukDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_ODFUK_DBOFFSET, "0"));
            vystupPrisavkaDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_PRISAVKA_DBOFFSET, "0"));
            vystupManualRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBOFFSET, "0"));
            vystupAutoRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBOFFSET, "0"));
            vystupPoloAutomatickyRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBOFFSET, "0"));
            vystupStartStopRamenoDBOffset = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBOFFSET, "0"));
            //bitInteger
            vystupExtruderDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_EXTRUDER_DBBIT, "0"));
            vystupRamenoKzDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_RAMENO_KZ_DBBIT, "0"));
            vystupRamenoKvDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_RAMENO_KV_DBBIT, "0"));
            vystupOdfukDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_ODFUK_DBBIT, "0"));
            vystupPrisavkaDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_PRISAVKA_DBBIT, "0"));
            vystupManualRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_MANUAL_RAMENO_DBBIT, "0"));
            vystupAutoRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_AUTO_RAMENO_DBBIT, "0"));
            vystupPoloAutomatickyRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_POLO_AUTOMATICKY_RAMENO_DBBIT, "0"));

            vystupStartStopRamenoDBBit = Integer.parseInt(sharedPreferences.getString(VYSTUP_START_STOP_RAMENO_DBBIT, "0"));

    }

    }
    private void startCountdown(){
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
                switchReadRameno.setChecked(false); // Turn off the switch after countdown finishes
            }
        };
        countDownTimer.start();
    }
    private void cancelCountdown(){
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
    private void setAllToRed(int falseRed){
        //TODO: Set all to red + add all images
        //inputs
        tv_rameno_inp_prisiaty_vyrobok.setTextColor(falseRed);
        tv_rameno_inp_dom_poloha.setTextColor(falseRed);
        tv_rameno_inp_vysunuty_extruder.setTextColor(falseRed);
        tv_rameno_inp_prazdny_zasobnik.setTextColor(falseRed);
        tv_rameno_inp_rameno_pri_z.setTextColor(falseRed);
        tv_rameno_inp_rameno_pri_v.setTextColor(falseRed);
        tv_rameno_inp_suc_na_v.setTextColor(falseRed);
        tv_rameno_inp_rameno_vytah_dole.setTextColor(falseRed);
        //outputs
        tv_rameno_out_extruder.setTextColor(falseRed);
        tv_rameno_out_rameno_kz.setTextColor(falseRed);
        tv_rameno_out_rameno_kv.setTextColor(falseRed);
        tv_rameno_out_odfuk.setTextColor(falseRed);
        tv_rameno_out_prisavka.setTextColor(falseRed);
        tv_rameno_out_manual_rameno.setTextColor(falseRed);
        tv_rameno_out_auto_rameno.setTextColor(falseRed);
        tv_rameno_out_polo_automaticky_rameno.setTextColor(falseRed);

        //buttons
        btn_rameno_kv.setBackgroundColor(falseRed);
        btn_rameno_kz.setBackgroundColor(falseRed);
        btn_rameno_extruder.setBackgroundColor(falseRed);
        btn_rameno_odfuk.setBackgroundColor(falseRed);
        btn_rameno_prisavka.setBackgroundColor(falseRed);
        btn_start_rameno.setBackgroundColor(falseRed);
        btn_stop_rameno.setBackgroundColor(falseRed);


    }
    /**
     * Metóda animatePuk slúži na animáciu pohybu puku v PLC.
     *
     * @param startAngle Počiatočný uhol rotácie puku v stupňoch.
     * @param endAngle Konečný uhol rotácie puku v stupňoch.
     * @param duration Trvanie animácie v milisekundách.
     */
    private void animatePuk(float startAngle,float endAngle,int duration){
        if (iv_rameno_puk == null) {
            return;
        }

        if (iv_center_circle == null) {
            return;
        }

        if (iv_center_circle.getRotation() == 180){
            // animate to 0
            ObjectAnimator animator = ObjectAnimator.ofFloat(iv_center_circle, "rotation", 0);
            animator.setDuration(duration); // duration of the animation in milliseconds
            animator.start();
        }else if(iv_center_circle.getRotation() == 0){
            //animate to 180
            ObjectAnimator animator = ObjectAnimator.ofFloat(iv_center_circle, "rotation", 180);
            animator.setDuration(duration); // duration of the animation in milliseconds
            animator.start();
        }



        // Create a Path object and add a circle to it
        Path path = new Path();


        //get center x and y from iv_center_circle
        float x = iv_rameno_arm.getX();
        float y = iv_rameno_arm.getY() + iv_rameno_arm.getHeight();
        float radius = iv_rameno_arm.getWidth()*0.8f;

        // Adjust the x and y coordinates of the path to animate the center of the view
        float adjustedX = x - iv_rameno_puk.getWidth() / 2.0f;
        float adjustedY = y- iv_rameno_puk.getHeight()/1.5f;

        //Toast.makeText(requireContext(), "x: "+adjustedX+" y: "+adjustedY, Toast.LENGTH_SHORT).show();


        RectF rectF = new RectF(adjustedX - radius, adjustedY - radius, adjustedX + radius, adjustedY + radius);
        // Add a semi-circle to the path
        path.arcTo(rectF, startAngle, endAngle);
        // path.addCircle(x, y, iv_rameno_arm.getWidth(), Path.Direction.CW);

        // Create an ObjectAnimator object and animate along the path
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_rameno_puk, View.X, View.Y, path);

        // Set the duration of the animation
        animator.setDuration(duration);



        //on animation end set rotation to endAngle
        animator.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                if (endAngle == 180) {
                    iv_rameno_puk.setColorFilter(ContextCompat.getColor(requireContext(), R.color.trueGreen));
                    iv_rameno_puk.setRotation(0);
                }else {
                    iv_rameno_puk.setColorFilter(ContextCompat.getColor(requireContext(), R.color.falseRed));
                    iv_rameno_puk.setRotation(180);
                }
            }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) {

            }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) {

            }
        });
        // Start the animation
        animator.start();
    }
    /**
     * Metóda animateArm slúži na animáciu pohybu ramena v PLC.
     *
     * @param rotationToDp Požadovaný uhol rotácie ramena v stupňoch. Hodnota -180 znamená rotáciu doľava, hodnota 0 znamená rotáciu doprava.
     * @param duration Trvanie animácie v milisekundách.
     */
    private void animateArm(int rotationToDp,int duration){
        if (iv_rameno_arm == null) {
            return;
        }
            if (iv_rameno_arm.getRotation() == rotationToDp){
                return;
            }
            iv_rameno_arm.setPivotX(0);
            iv_rameno_arm.setPivotY(convertDpToPixel(32, requireContext()));
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(iv_rameno_arm.getRotation(),rotationToDp);
            valueAnimator.setDuration(duration);
            valueAnimator.addUpdateListener(animation -> iv_rameno_arm.setRotation((Float) animation.getAnimatedValue()));
            valueAnimator.start();
    }
    private float convertDpToPixel(float dp, Context context){
        return dp * context.getResources().getDisplayMetrics().density;
    }
    /**
     * Metóda ReadPlc slúži na čítanie hodnôt z PLC a aktualizáciu UI na základe týchto hodnôt.
     * Táto metóda vytvára nové vlákno, ktoré sa pripojí na PLC, prečíta hodnoty a potom sa odpojí.
     * Po prečítaní hodnôt sa UI aktualizuje na hlavnom vlákne pomocou Handlera.
     * Ak nastane chyba pri pripojení, čítaní alebo odpojení, chybová správa sa zobrazí v UI.
     */
    private void ReadPlc() {
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[5];
        boolean[][] dataBools = new boolean[data.length][8];
        executorService.execute(() -> {
            try{
                client.SetConnectionType(S7.S7_BASIC);
                //inputs
                int dbOffsetInputs = 0;

                int res = client.ConnectTo(ramenoIPAdresa,0,2);// ak je S7-300 tak je vždy 0,2

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
                    //inputs
                    //log dataBools[snimac_L_DBOffset][snimac_L_DBBit] in console
                    tv_rameno_inp_prisiaty_vyrobok.setTextColor(dataBools[snimacPrisiatyDBOffset][snimacPrisiatyDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_dom_poloha.setTextColor(dataBools[snimacDomPolohaDBOffset][snimacDomPolohaDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_vysunuty_extruder.setTextColor(dataBools[snimacVysExtruderDBOffset][snimacVysExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_prazdny_zasobnik.setTextColor(dataBools[snimacPrazdnyZasobnikDBOffset][snimacPrazdnyZasobnikDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_rameno_pri_z.setTextColor(dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_rameno_pri_v.setTextColor(dataBools[snimacRamenoPriVDBOffset][snimacRamenoPriVDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_suc_na_v.setTextColor(dataBools[snimacSucNaVDBOffset][snimacSucNaVDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_inp_rameno_vytah_dole.setTextColor(dataBools[snimacRVytahDoleDBOffset][snimacRVytahDoleDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    //outputs
                    tv_rameno_out_extruder.setTextColor(dataBools[vystupExtruderDBOffset][vystupExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_rameno_kz.setTextColor(dataBools[vystupRamenoKzDBOffset][vystupRamenoKzDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_rameno_kv.setTextColor(dataBools[vystupRamenoKvDBOffset][vystupRamenoKvDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_odfuk.setTextColor(dataBools[vystupOdfukDBOffset][vystupOdfukDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_prisavka.setTextColor(dataBools[vystupPrisavkaDBOffset][vystupPrisavkaDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_manual_rameno.setTextColor(dataBools[vystupManualRamenoDBOffset][vystupManualRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_auto_rameno.setTextColor(dataBools[vystupAutoRamenoDBOffset][vystupAutoRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    tv_rameno_out_polo_automaticky_rameno.setTextColor(dataBools[vystupPoloAutomatickyRamenoDBOffset][vystupPoloAutomatickyRamenoDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));


                    boolean jeVoStope = dataBools[vystupStartStopRamenoDBOffset][vystupStartStopRamenoDBBit];

                    if (jeVoStope) {
                        btn_start_rameno.setBackgroundColor(getResources().getColor(R.color.trueGreen, requireActivity().getTheme()));
                        btn_stop_rameno.setBackgroundColor(getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_rameno_extruder.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));
                        btn_rameno_kz.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));
                        btn_rameno_kv.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));
                        btn_rameno_odfuk.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));
                        btn_rameno_prisavka.setBackgroundColor(getResources().getColor(R.color.material_button_background_disabled, requireActivity().getTheme()));

                    }else {
                        btn_start_rameno.setBackgroundColor(getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_stop_rameno.setBackgroundColor(getResources().getColor(R.color.trueGreen, requireActivity().getTheme()));

                        btn_rameno_extruder.setBackgroundColor(dataBools[vystupExtruderDBOffset][vystupExtruderDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_rameno_kz.setBackgroundColor(dataBools[vystupRamenoKzDBOffset][vystupRamenoKzDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_rameno_kv.setBackgroundColor(dataBools[vystupRamenoKvDBOffset][vystupRamenoKvDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_rameno_odfuk.setBackgroundColor(dataBools[vystupOdfukDBOffset][vystupOdfukDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                        btn_rameno_prisavka.setBackgroundColor(dataBools[vystupPrisavkaDBOffset][vystupPrisavkaDBBit] ? getResources().getColor(R.color.trueGreen, requireActivity().getTheme()) : getResources().getColor(R.color.falseRed, requireActivity().getTheme()));
                    }

                    btn_rameno_extruder.setEnabled(!jeVoStope);
                    btn_rameno_kz.setEnabled(!jeVoStope);
                    btn_rameno_kv.setEnabled(!jeVoStope);
                    btn_rameno_odfuk.setEnabled(!jeVoStope);
                    btn_rameno_prisavka.setEnabled(!jeVoStope);







                    boolean ramenoPriZasobniku = dataBools[snimacRamenoPriZDBOffset][snimacRamenoPriZDBBit];
                    boolean ramenoPriVytahu = dataBools[snimacRamenoPriVDBOffset][snimacRamenoPriVDBBit];
                    boolean chodKZ = dataBools[vystupRamenoKzDBOffset][vystupRamenoKzDBBit];
                    boolean chodKV = dataBools[vystupRamenoKvDBOffset][vystupRamenoKvDBBit];

                    boolean animateToZasobnik = ramenoPriVytahu && chodKZ;
                    boolean animateToVytah = ramenoPriZasobniku && chodKV;
                    int duration = 2300;

                    if(!ramenoPriVytahu && chodKV){//last animation task was to zasobnik
                    //anim to vytah
                        if (iv_rameno_arm.getRotation() != -180 && !animToVytahInProgress){
                            animToZasobnikInProgress = false;
                            animateArm(-180,duration);
                            animToVytahInProgress = true;
                            animatePuk(iv_rameno_arm.getRotation(),-180 + Math.abs(iv_rameno_arm.getRotation()),duration);
                        }
                    }
                    if(!ramenoPriZasobniku && chodKZ){//last animation task was to zasobnik
                        //anim to vytah
                        if (iv_rameno_arm.getRotation() != 0 && !animToZasobnikInProgress){
                            animToVytahInProgress = false;
                            animateArm(0,duration);
                            animToZasobnikInProgress = true;
                            animatePuk(iv_rameno_arm.getRotation(), Math.abs(iv_rameno_arm.getRotation()),duration);
                        }
                    }
                }
            });
        });
    }

}