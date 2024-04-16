package com.example.myapplication.plcdiplomovka1;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.material.materialswitch.MaterialSwitch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import Moka7.S7;
import Moka7.S7Client;
//TODO PlcWriteRequest class pre zjednodušenie prenosu parametrov do WritePlc a ToggleWriteBit
public class IOMethods {
    /**
     * Metóda ToggleWriteBit slúži na zmenu hodnoty bitu v PLC. Na rozdiel od {@link #WritePlc(TextView,S7Client, Handler, ExecutorService, String, int, int, int, boolean)},
     * ktorá zapisuje hodnotu bitu, ktorú jej pošleme, ToggleWriteBit zmení hodnotu bitu na opačnú.
     * Ak je bit true tak ho zmení na false a naopak.
     *
     * @param ipAdresa IP adresa PLC, s ktorým sa má spojiť.
     * @param dbNumber Číslo databloku v PLC, kde sa nachádza bit, ktorý chceme zmeniť.
     * @param dbOffset Offset v databloku, kde sa nachádza bit, ktorý chceme zmeniť.
     * @param dbBit Pozícia bitu v byte, ktorý chceme zmeniť.
     * @param client S7Client, ktorý sa má použiť na komunikáciu s PLC.
     * @param handler Handler, ktorý sa má použiť na aktualizáciu UI.
     * @param executorService ExecutorService, ktorý sa má použiť na spustenie vlákna.
     * @return Vracia hodnotu true, ak sa operácia vykonala úspešne, inak false.
     */
    public static boolean ToggleWriteBit(TextView errorText, S7Client client, Handler handler, ExecutorService executorService, String ipAdresa, int dbNumber, int dbOffset, int dbBit){
        AtomicReference<String> ret = new AtomicReference<>("");
        byte[] data = new byte[1];
        AtomicBoolean bitValue = new AtomicBoolean(false);
        executorService.execute(() ->{
            try{
                client.SetConnectionType(S7.S7_BASIC);
                int res = client.ConnectTo(ipAdresa,0,2);
                if(res == 0){
                    ret.set("Pripojenie OK");
                    res = client.ReadArea(S7.S7AreaDB, dbNumber,dbOffset,1,data);
                    if(res == 0){
                        ret.set("Prečítanie OK");
                        bitValue.set(S7.GetBitAt(data,0,dbBit));
                        //System.out.println("Bit value: " + bitValue.get());
                        S7.SetBitAt(data,0,dbBit,!bitValue.get());    // Toggle the bit
                        res = client.WriteArea(S7.S7AreaDB, dbNumber,dbOffset,data.length,data);
                        if(res == 0){
                            ret.set("Zapísanie OK");
                        }else {
                            ret.set("Error Zapisovania: " + S7Client.ErrorText(res));
                        }
                    }else {
                        ret.set("Error Čítania: " + S7Client.ErrorText(res));
                    }
                }else{
                    ret.set("Error Pripojenia: " + S7Client.ErrorText(res));
                }

                client.Disconnect();
                handler.post(() -> {
                    if(ret.toString().toLowerCase().contains("error")){
                        errorText.setText(ret.toString());
                    }else {
                        errorText.setText(ret.toString());
                    }
                });
            }
            catch (Exception e){
                if (Thread.interrupted()){
                    ret.set(Thread.currentThread().getName() + " was interrupted");

                }else {
                    ret.set(e.getMessage());
                }
            }
        });

        return !ret.toString().toLowerCase().contains("error"); // ak true tak nenastal error
    }
    /**
     * Metóda WritePlc slúži na zápis hodnoty bitu v PLC. Na rozdiel od {@link #ToggleWriteBit(TextView,S7Client,Handler,ExecutorService,String, int, int, int)},
     * ktorá zapisuje opačnú hodnotu bitu, WritePlc zapisuje hodnotu bitu, ktorú jej pošleme pomocou writeValue.
     *
     * @param ipAdresa IP adresa PLC, s ktorým sa má spojiť.
     * @param dbNumber Číslo databloku v PLC, kde sa nachádza bit, ktorý chceme zmeniť.
     * @param dbOffset Offset v databloku, kde sa nachádza bit, ktorý chceme zmeniť.
     * @param dbBit Pozícia bitu v byte, ktorý chceme zmeniť.
     * @param writeValue Hodnota, ktorú chceme zapísať do bitu.
     * @param client S7Client, ktorý sa má použiť na komunikáciu s PLC.
     * @param handler Handler, ktorý sa má použiť na aktualizáciu UI.
     * @param executorService ExecutorService, ktorý sa má použiť na spustenie vlákna.
     * @return Vracia hodnotu 0, ak sa operácia vykonala úspešne, inak 1.
     */
    public static int WritePlc(TextView errorText, S7Client client, Handler handler, ExecutorService executorService, String ipAdresa, int dbNumber, int dbOffset, int dbBit, boolean writeValue){
        AtomicReference<String> ret = new AtomicReference<>("Not called");
        AtomicInteger returnValue = new AtomicInteger();
        executorService.execute(() -> {
            if (client.getConnectionType() != S7.S7_BASIC){
                client.SetConnectionType(S7.S7_BASIC);
            }
            int res = 1;
            if (!client.Connected){
                System.out.println("Nepripojený");
                 res = client.ConnectTo(ipAdresa,0,2);// ak je S7-300 tak je vždy 0,2
            }else{
                System.out.println("Pripojený");
                res = 0;
            }

            if(res ==0){
                byte[] data = new byte[1];
                res = client.ReadArea(S7.S7AreaDB,dbNumber,dbOffset,1,data);
                if(res == 0){
                    S7.SetBitAt(data,0,dbBit,writeValue);
                    res = client.WriteArea(S7.S7AreaDB,dbNumber,dbOffset,1,data);
                    if(res == 0){
                        returnValue.set(0);
                        ret.set("Write successful");
                    }else{
                        returnValue.set(1);
                        ret.set("Error: " + S7Client.ErrorText(res));
                    }
                }else{
                    returnValue.set(1);
                    ret.set("Error: " + S7Client.ErrorText(res));
                }
            }else {
                returnValue.set(1);
                ret.set("Error: " + S7Client.ErrorText(res));
            }
            if (client.Connected){
                client.Disconnect();
            }
            System.out.println("End writePlc");
            handler.post(() -> {
                System.out.println("Handler post");
                System.out.println(ret.toString());
                errorText.setText(ret.toString());
                if (ret.toString().toLowerCase().contains("error")){
                    returnValue.set(1); //nok
                }else {
                    returnValue.set(0);//ok
                }
            });
        });
        return returnValue.get();
    }

    public static class CountdownRequest {
        private int timerDuration;
        private int timerInterval;
        private int counterIn;
        private CountDownTimer countDownTimer;
        private MaterialSwitch switchRead;
        private Runnable action;

        public int getTimerDuration() {
            return timerDuration;
        }
        public int getTimerInterval() {
            return timerInterval;
        }
        public int getCounterIn() {
            return counterIn;
        }
        public CountDownTimer getCountDownTimer() {
            return countDownTimer;
        }
        public MaterialSwitch getSwitchRead() {
            return switchRead;
        }
        public Runnable getAction() {
            return action;
        }

        public void setTimerDuration(int timerDuration) {
            this.timerDuration = timerDuration;
        }
        public void setTimerInterval(int timerInterval) {
            this.timerInterval = timerInterval;
        }
        public void setCounterIn(int counterIn) {
            this.counterIn = counterIn;
        }
        public void setCountDownTimer(CountDownTimer countDownTimer) {
            this.countDownTimer = countDownTimer;
        }
        public void setSwitchRead(MaterialSwitch switchRead) {
            this.switchRead = switchRead;
        }
        public void setAction(Runnable action) {
            this.action = action;
        }

        public CountdownRequest(int timerDuration, int timerInterval, int counterIn, CountDownTimer countDownTimer, MaterialSwitch switchRead, Runnable action) {
            this.timerDuration = timerDuration;
            this.timerInterval = timerInterval;
            this.counterIn = counterIn;
            this.countDownTimer = countDownTimer;
            this.switchRead = switchRead;
            this.action = action;
        }
    }
    public static CountDownTimer startCountdown(CountdownRequest request){
        int casovac_time = request.getTimerDuration();
        int casovac_interval = request.getTimerInterval();
        int counterIn = request.getCounterIn();
        CountDownTimer countDownTimer = request.getCountDownTimer();
        MaterialSwitch switchRead = request.getSwitchRead();
        Runnable action = request.getAction();

        cancelCountdown(countDownTimer); // Zruší existujúci časovač
        countDownTimer = new CountDownTimer(casovac_time, casovac_interval) {
            private int counter = counterIn;
            @Override
            public void onTick(long millisUntilFinished) {
                //Aktualizuje UI s aktuálnym počtom.
                action.run();
                counter--;
            }
            @Override
            public void onFinish() {
                counter = (int) Math.floor((double)casovac_time / casovac_interval); // Reset counter
                switchRead.setChecked(false); // Vypni prepínač po skončení odpočítavania
            }
        };
        countDownTimer.start();
        return countDownTimer;
    }
    public static void cancelCountdown(CountDownTimer countDownTimer){
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
