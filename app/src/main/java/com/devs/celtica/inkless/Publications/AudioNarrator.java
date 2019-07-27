/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless.Publications;


import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioNarrator  extends Audio {
    
    public AudioNarrator(String nom, int id_pub, String date_pub, String type, String lien) {
        super(nom, id_pub, date_pub, type, lien);
    }

    public void uploadAudio(AppCompatActivity c, ArrayList<TrackForUpload> tracks){
        HashMap<String,String> datas=new HashMap<>();
        datas.put("audio_for","narrator");
        super.uploadAudio(c,datas,tracks);

    }
    
}
