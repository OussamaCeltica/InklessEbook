package com.devs.celtica.inkless.Publications;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Users.Profile;

import java.util.ArrayList;

/**
 * Created by celtica on 21/05/19.
 */

public class AudioUploadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AppCompatActivity c;
    public static ArrayList<AudioForUpload> audios=new  ArrayList<AudioForUpload>();

    public AudioUploadAdapter(AppCompatActivity c) {
        this.c = c;

    }

    public static class AudioUploadView extends RecyclerView.ViewHolder  {
        EditText titre;
        TextView uploadButt;
        LinearLayout body;
        public AudioUploadView(View v) {
            super(v);
              titre=(EditText) v.findViewById(R.id.div_addAudio_titre);
              uploadButt=(TextView)v.findViewById(R.id.div_addAudio_uploadButt);
              body=(LinearLayout)v.findViewById(R.id.body);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.div_add_audio,parent,false);

        AudioUploadView vh = new AudioUploadView(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {



        if(audios.get(position).nom == null){
            ((AudioUploadView)holder).titre.setHint(c.getResources().getString(R.string.uploadAudio_titre)+" "+(position+1));
        }else {
            ((AudioUploadView)holder).titre.setText(audios.get(position).nom);
        }

        if(audios.get(position).audioFile == null){
            ((AudioUploadView)holder).uploadButt.setText(c.getResources().getString(R.string.uploadBook_upload));
        }else {
            ((AudioUploadView)holder).uploadButt.setText(Login.reader.getFilePath(c,audios.get(position).audioFile));
        }


        ((AudioUploadView)holder).uploadButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Login.reader.openSelectFile(c,TypeFiles.PDF);
            }
        });


    }

    @Override
    public int getItemCount() {
        return audios.size();
    }
}
