package com.devs.celtica.inkless.Users;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class Writer extends ReaderFull{

    public String ccp;
    public boolean contrat_writer_valide=false;

    public Writer(int id_user, String nom, String num_tel, String email, String mdp,String nation, String photo,String ccp) {
        super(id_user, nom, num_tel, email, mdp,nation, photo);
        this.ccp = ccp;
    }

    @Override
    public void signUp(AppCompatActivity c){
        HashMap<String,String> data= getHashmapUserInfos ();
        data.put("request","inscription");
        data.put("type_user","writer");
        data.put("ccp",ccp);

        sendUserINfosToServer(c,data);
    }
    
}
