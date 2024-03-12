package com.example.myapplication.plcdiplomovka1;

import android.os.Handler;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import Moka7.S7;
import Moka7.S7Client;

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
                System.out.println("Connect to: " + ipAdresa);
                System.out.println("DBNumber: " + dbNumber);
                System.out.println("DBOffset: " + dbOffset);
                System.out.println("Bit: " + dbBit);
                System.out.println("Res: " + res);
                if(res == 0){
                    ret.set("Connection OK");
                    res = client.ReadArea(S7.S7AreaDB, dbNumber,dbOffset,1,data);
                    if(res == 0){
                        ret.set("Read OK");
                        bitValue.set(S7.GetBitAt(data,0,dbBit));
                        System.out.println("Bit value: " + bitValue.get());
                        S7.SetBitAt(data,0,dbBit,!bitValue.get());    // Toggle the bit
                        res = client.WriteArea(S7.S7AreaDB, dbNumber,dbOffset,data.length,data);
                        if(res == 0){
                            ret.set("Write OK");
                        }else {
                            ret.set("Write Error: " + S7Client.ErrorText(res));
                        }
                    }else {
                        ret.set("Read Error: " + S7Client.ErrorText(res));
                    }
                }else{
                    ret.set("Connection Error: " + S7Client.ErrorText(res));
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

        return !ret.toString().toLowerCase().contains("error"); // if true then no error occured
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
        System.out.println("WritePlc called");
        System.out.println("ipAdresa: " + ipAdresa);
        System.out.println("dbNumber: " + dbNumber);
        System.out.println("dbOffset: " + dbOffset);
        System.out.println("dbBit: " + dbBit);
        System.out.println("writeValue: " + writeValue);
        System.out.println("Start writePlc");
        executorService.execute(() -> {
            client.SetConnectionType(S7.S7_BASIC);
            int res = client.ConnectTo(ipAdresa,0,2);// ak je S7-300 tak je vždy 0,2
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
            client.Disconnect();
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
}
