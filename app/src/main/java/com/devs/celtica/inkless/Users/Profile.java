package com.devs.celtica.inkless.Users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.UploadPdf;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            if(Login.reader instanceof Writer){
                LinearLayout uploadButt=((LinearLayout)findViewById(R.id.profile_uploadButt));
                uploadButt.setVisibility(View.VISIBLE);
                uploadButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Profile.this,UploadPdf.class));
                    }
                });
            }
        }
    }
}
