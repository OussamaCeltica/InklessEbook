package com.devs.celtica.inkless.Users;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.PostServerRequest5;

import java.net.URI;
import java.util.ArrayList;
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

    public void uploadBook(AppCompatActivity c){
        HashMap<String,String> data= getHashmapUserInfos ();
        ArrayList<Uri> files=new ArrayList<Uri>();
        Login.ajax.sendWithFiles(data, files, c, new PostServerRequest5.doBeforAndAfterGettingData() {
            @Override
            public void before() {

            }

            @Override
            public void echec(Exception e) {

            }

            @Override
            public void After(String result) {

            }
        });
    }
    
}
