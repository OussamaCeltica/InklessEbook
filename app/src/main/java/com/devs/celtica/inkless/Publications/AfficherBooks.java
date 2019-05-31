package com.devs.celtica.inkless.Publications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.BottiomMenu;
import com.devs.celtica.inkless.PostServerRequest5;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Users.Writer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AfficherBooks extends AppCompatActivity {

    AfficherBookAdapter mAdapter;
    public ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_books);

        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            //region Configuration de recyclervew ..
            progress=new ProgressDialog(this);
            final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.affichBook_divAffich);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(AfficherBooks.this);
            mRecyclerView.setLayoutManager(mLayoutManager);


            // specify an adapter (see also next example)
            mAdapter = new AfficherBookAdapter(AfficherBooks.this);

            mRecyclerView.setAdapter(mAdapter);

            Login.ajax.setUrlWrite("/read.php");
            ((Writer)Login.reader).getBooks(new PostServerRequest5.doBeforAndAfterGettingData() {
                @Override
                public void before() {
                    progress.show();
                }

                @Override
                public void echec(Exception e) {

                    e.printStackTrace();
                    progress.dismiss();
                }

                @Override
                public void After(String result) {
                    Log.e("bbb",result+"");
                    progress.dismiss();
                    Writer auteur=new Writer(Login.reader.id_user,Login.reader.nom);

                    try {
                        JSONArray r=new JSONArray(result);
                        for (int i=0;i<r.length();i++){
                            JSONObject obj=r.getJSONObject(i);
                            AfficherBookAdapter.books.add(new Book(obj.getInt("id_pub"),obj.getString("lien_resume"),obj.getString("lien"),obj.getString("photo"),obj.getString("maison_edition"),obj.getString("nom1"),obj.getString("nom2"),obj.getString("date"),auteur,true,0,0));
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


            //endregion
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AfficherBookAdapter.books.clear();
    }
}
