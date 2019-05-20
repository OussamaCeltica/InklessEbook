/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.devs.celtica.inkless.Activities.Accueil;
import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.PostServerRequest5;
import com.devs.celtica.inkless.Publications.TypeFiles;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Activities.SignUp;
import com.devs.celtica.inkless.Sha1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static android.bluetooth.BluetoothClass.Service.AUDIO;

/**
 *
 * @author INFO
 */
public class User {
  
   public int id_user;
   public String nom,num_tel,email,mdp,nation,photo; //photo path


    public User(int id_user, String nom, String num_tel, String email, String mdp,String nation ,String photo) {
        this.id_user = id_user;
        this.nom = nom;
        this.num_tel = num_tel;
        this.email = email;
        this.mdp = mdp;
        this.nation=nation;
        this.photo = photo;
    }

    public User(  String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public HashMap<String,String> getHashmapUserInfos (){
        HashMap<String,String> data=new HashMap<String,String>();

        data.put("username",nom);
        data.put("mdp",mdp);
        data.put("email",email);
        data.put("nation",nation);

        return data;
    }

    protected void sendUserINfosToServer(final AppCompatActivity c, HashMap<String,String> data){
        final ProgressDialog progress=new ProgressDialog(c);
        Login.ajax.setUrlWrite("/write.php");
        Login.ajax.send(data, new PostServerRequest5.doBeforAndAfterGettingData() {
            @Override
            public void before() {
                progress.setTitle(c.getResources().getString(R.string.signUp_cnct_msg));
                progress.setMessage(c.getResources().getString(R.string.signUp_cnct_wait_msg));
                progress.show();
            }

            @Override
            public void echec(Exception e) {
                progress.dismiss();
                e.printStackTrace();
                SignUp.isOnSend=false;

            }

            @Override
            public void After(String result) {
                SignUp.isOnSend=false;
                progress.dismiss();

                if(result.equals("email err")){
                    c.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(c,c.getResources().getString(R.string.signUp_email_err),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (result.equals("succ")){
                    c.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(c,c.getResources().getString(R.string.signUp_succ),Toast.LENGTH_SHORT).show();

                            //region Revenir a au Login ..
                            SignUp.inscTerminé=true;
                            Intent intent = new Intent(c, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            c.startActivity(intent);
                            //endregion
                        }
                    });

                }

            }
        });
    }

    public void connecter(final AppCompatActivity c){
        final ProgressDialog progress=new ProgressDialog(c);
        HashMap<String,String> data =new HashMap<String,String> ();
        data.put("email",email);
        Login.ajax.setUrlRead("/read.php");
        Login.ajax.read("select * from user where  email=?", data, new PostServerRequest5.doBeforAndAfterGettingData() {
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

                try {
                    JSONArray r=new JSONArray(result);

                    //region email erreur ..
                    if(r.length() == 0){
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                                Toast.makeText(c.getApplicationContext(),c.getResources().getString(R.string.login_email_err),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    //endregion

                    else {
                        //region mot de passe err ..
                        JSONObject user=r.getJSONObject(0);
                        if(!user.getString("mdp").equals(Sha1.hash(mdp))){
                            c.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.dismiss();
                                    Toast.makeText(c,c.getResources().getString(R.string.login_mdp_err),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        //endregion

                        //region récupére le type user et son contrat ..
                        else {

                            HashMap<String,String> data=new HashMap<String,String>();
                            data.put("1",user.getString("id_user"));
                            data.put("2",user.getString("id_user"));
                            data.put("3",user.getString("id_user"));
                            data.put("4",user.getString("id_user"));
                            Login.ajax.read("SELECT u.*, \n" +
                                    "       c_r.licence_reader, \n" +
                                    "       c_w.licence_writer, \n" +
                                    "       licence_narrator \n" +
                                    "FROM   ( (SELECT uu.*, \n" +
                                    "               r.id_reader, \n" +
                                    "               nar.id_narrator, \n" +
                                    "               nar.ccp AS narrator_ccp, \n" +
                                    "               wr.id_writer, \n" +
                                    "               wr.ccp  AS writer_ccp \n" +
                                    "        FROM   user uu \n" +
                                    "               LEFT JOIN reader_full r \n" +
                                    "                      ON uu.id_user = r.id_reader \n" +
                                    "               LEFT JOIN writer wr \n" +
                                    "                      ON uu.id_user = wr.id_writer \n" +
                                    "               LEFT JOIN narrator nar \n" +
                                    "                      ON uu.id_user = nar.id_narrator \n" +
                                    "        WHERE  id_user = ?) AS u \n" +
                                    "         LEFT JOIN(SELECT id_user                           AS id_reader, \n" +
                                    "                          Datediff(date_fin_contrat, Now()) AS licence_reader \n" +
                                    "                   FROM   contract \n" +
                                    "                   WHERE  type = 'reader' \n" +
                                    "                          AND id_user = ? \n" +
                                    "                   ORDER  BY id_contrat DESC \n" +
                                    "                   LIMIT  1) AS c_r \n" +
                                    "                ON c_r.id_reader = u.id_user \n" +
                                    "         LEFT JOIN(SELECT id_user                           AS id_writer, \n" +
                                    "                          Datediff(date_fin_contrat, Now()) AS licence_writer \n" +
                                    "                   FROM   contract \n" +
                                    "                   WHERE  type = 'writer' \n" +
                                    "                          AND id_user = ? \n" +
                                    "                   ORDER  BY id_contrat DESC \n" +
                                    "                   LIMIT  1) AS c_w \n" +
                                    "                ON c_w.id_writer = u.id_user \n" +
                                    "         LEFT JOIN(SELECT id_user                           AS id_narrator, \n" +
                                    "                          Datediff(date_fin_contrat, Now()) AS licence_narrator \n" +
                                    "                   FROM   contract \n" +
                                    "                   WHERE  type = 'narrator' \n" +
                                    "                          AND id_user = ? \n" +
                                    "                   ORDER  BY id_contrat DESC \n" +
                                    "                   LIMIT  1) AS c_n \n" +
                                    "                ON c_n.id_narrator = u.id_user ) ",data, new PostServerRequest5.doBeforAndAfterGettingData() {
                                @Override
                                public void before() {

                                }

                                @Override
                                public void echec(Exception e) {
                                    progress.dismiss();
                                }

                                @Override
                                public void After(String result) {
                                    Log.e("rrr",result+"");
                                    progress.dismiss();

                                    try {
                                        JSONArray r = new JSONArray(result);
                                        JSONObject user=r.getJSONObject(0);

                                        //region testé si le user est un writter ou reader ..
                                        if (!user.getString("id_writer").equals("null")){
                                            Login.reader=new Writer(user.getInt("id_writer"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","","",user.getString("writer_ccp"));
                                            if(!user.getString("licence_writer").equals("null") && user.getInt("licence_writer")>0){
                                                ((Writer)Login.reader).contrat_writer_valide=true;
                                            }
                                        }else {
                                            if (!user.getString("id_reader").equals("null")){
                                                Login.reader=new ReaderFull(user.getInt("id_reader"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","","");
                                                if(!user.getString("licence_reader").equals("null") && user.getInt("licence_reader")>0){
                                                    Login.reader.contrat_reader_valide=true;
                                                }
                                            }
                                        }
                                        //endregion

                                        //region testé si le user est un narrator
                                        if (!user.getString("id_narrator").equals("null")){
                                            Login.narrator=new Narrator(user.getInt("id_narrator"),user.getString("nom")+"","",user.getString("nom")+"",user.getString("mdp")+"","","",user.getString("narrator_ccp"));
                                            if(!user.getString("licence_narrator").equals("null") && user.getInt("licence_narrator")>0){
                                                Login.reader.contrat_reader_valide=true;
                                            }
                                        }
                                        //endregion

                                        c.startActivity(new Intent(c,Profile.class));
                                        c.finish();

                                    }catch (JSONException e){
                                        e.printStackTrace();

                                    }




                                }
                            });
                        }
                        //endregion
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void onSignUpTerminé(AppCompatActivity c){
        SignUp.inscTerminé=true;
        //region Revenir a au Login ..
        Intent intent = new Intent(c, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(intent);
        //endregion
    }

    public void openSelectFile(AppCompatActivity c,TypeFiles type_file){

        switch (type_file){
            case  PDF:{
                Intent i2=new Intent();
                i2.setType("application/pdf");
                i2.setAction(Intent.ACTION_GET_CONTENT);
                c.startActivityForResult(i2,2);
            }
            break;
            case AUDIO:{

            }break;
            case PHOTO:{
                Intent i2=new Intent();
                i2.setType("image/*");
                i2.setAction(Intent.ACTION_GET_CONTENT);
                c.startActivityForResult(i2,1);
            }
            break;
        }

    }

    public  String getFilePath(Context context, Uri uri) {

        Cursor cursor = null;
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, null, null,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
}
