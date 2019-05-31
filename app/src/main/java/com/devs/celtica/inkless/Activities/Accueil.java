package com.devs.celtica.inkless.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Publications.UploadPdf;
import com.devs.celtica.inkless.Users.Profile;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

        }

    }
}
