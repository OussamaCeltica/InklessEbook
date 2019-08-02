/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless.Publications;


import android.support.v7.app.AppCompatActivity;

import com.devs.celtica.inkless.Users.Narrator;

import java.util.ArrayList;
import java.util.HashMap;

public class AudioNarrator  extends Audio {



    public Narrator narrator;



    //pour l affichages des audios ..
    public AudioNarrator(String nom, int id_pub, int id_book, String date_pub,Narrator n) {
        super(nom, id_pub, id_book,date_pub);
        this.narrator=n;
    }


    public AudioNarrator(String nom, int id_pub, String date_pub, String lien,Narrator nar) {
        super(nom, id_pub, date_pub,  "audio", lien);
        narrator=nar;
    }

    public void uploadAudio(AppCompatActivity c, ArrayList<TrackForUpload> tracks){
        HashMap<String,String> datas=new HashMap<>();
        datas.put("audio_for","narrator");
        super.uploadAudio(c,datas,tracks);

    }
    
}
