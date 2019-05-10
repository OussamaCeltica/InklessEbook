/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * @author INFO
 */
public class User {
  
   public int id_user;
   public String nom,num_tel,email,mdp,photo; //photo path

    public User(int id_user, String nom, String num_tel, String email, String mdp, String photo) {
        this.id_user = id_user;
        this.nom = nom;
        this.num_tel = num_tel;
        this.email = email;
        this.mdp = mdp;
        this.photo = photo;
    }

    public User(  String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public void connecter(final AppCompatActivity c){
        final ProgressDialog progress=new ProgressDialog(c);
        HashMap<String,String> data =new HashMap<String,String> ();
        data.put("email",email);
        Login.ajax.setUrlRead("/read.php");
        Login.ajax.read(" select u.*,contrat_reader.date_contract_reader,contrat_narrattor.date_contract_narrator,contrat_writer.date_contract_writer\n" +
                "from (SELECT user.*,reader_full.*,writer.id_writer,writer.ccp as writer_ccp,narrator.id_narrator,narrator.ccp as narrator_ccp from user left join reader_full on user.id_user=reader_full.id_reader LEFT JOIN writer on user.id_user=writer.id_writer LEFT JOIN narrator ON user.id_user=narrator.id_narrator) as u\n" +
                "LEFT JOIN\n" +
                "        (select id_user as id_reader,DATEDIFF(date_fin_contrat,NOW()) as date_contract_reader from \n" +
                "         contract where type='reader'   order by date_contract desc) as contrat_reader on  \n" +
                "         u.id_user=contrat_reader.id_reader \n" +
                "         left join\n" +
                "                  (select id_user as id_writer ,DATEDIFF(date_fin_contrat,NOW()) as date_contract_writer\n" +
                "                   from contract where type='writer'   order by date_contract desc) as contrat_writer \n" +
                "                   on contrat_reader.id_reader=contrat_writer.id_writer\n" +
                "                   left join\n" +
                "\n" +
                "                          (select id_user as id_narrator,DATEDIFF(date_fin_contrat,NOW()) as \n" +
                "                           date_contract_narrator\n" +
                "                           from contract where type='narrator'   order by date_contract desc) as \n" +
                "                           contrat_narrattor on contrat_narrattor.id_narrator=contrat_writer.id_writer where \n" +
                "  u.email=?", data, new PostServerRequest5.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                progress.setTitle("جاري البحث ..");
                progress.setMessage("انتظر..");
                progress.show();
            }

            @Override
            public void echec(Exception e) {
                e.printStackTrace();
                progress.dismiss();

            }

            @Override
            public void After(String result) {
                progress.dismiss();



                try {
                    JSONArray r=new JSONArray(result);

                    if(r.length() == 0){
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(c.getApplicationContext(),c.getResources().getString(R.string.login_email_err),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        JSONObject user=r.getJSONObject(0);
                        if(!user.getString("mdp").equals(mdp)){
                            c.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(c,c.getResources().getString(R.string.login_mdp_err),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else {

                            //region testé si le user est un writter ou reader ..
                            if (!user.getString("id_writer").equals("null")){
                                Login.reader=new Writer(user.getInt("id_writer"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","","");
                                if(user.getString("date_contract_writer").equals("null") && user.getInt("date_contract_writer")>0){
                                    ((Writer)Login.reader).contrat_writer_valide=true;
                                }
                            }else {
                                if (!user.getString("id_reader").equals("null")){
                                    Login.reader=new ReaderFull(user.getInt("id_reader"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","");
                                    if(!user.getString("date_contract_reader").equals("null") && user.getInt("date_contract_reader")>0){
                                        Login.reader.contrat_reader_valide=true;
                                    }
                                }
                            }
                            //endregion

                            //region testé si le user est un narrator
                            if (!user.getString("id_narrator").equals("null")){
                                Login.narrator=new Narrator(user.getInt("id_narrator"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","","");
                                if(!user.getString("date_contract_narrator").equals("null") && user.getInt("date_contract_narrator")>0){
                                    Login.reader.contrat_reader_valide=true;
                                }
                            }
                            //endregion

                            c.startActivity(new Intent(c,Accueil.class));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
