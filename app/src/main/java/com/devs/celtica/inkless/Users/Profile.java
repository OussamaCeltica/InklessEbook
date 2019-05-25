package com.devs.celtica.inkless.Users;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.Publications.UploadAudio;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Publications.UploadPdf;

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

                        AlertDialog.Builder mb = new AlertDialog.Builder(Profile.this); //c est l activity non le context ..

                        View v=  getLayoutInflater().inflate(R.layout.div_pub_choice,null);
                        TextView book=(TextView) v.findViewById(R.id.div_choiceBook);
                        TextView audio=(TextView) v.findViewById(R.id.div_choiceAudio);

                        book.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Profile.this,UploadPdf.class));
                            }
                        });

                        audio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(Profile.this, UploadAudio.class));
                            }
                        });

                        mb.setView(v);
                        final AlertDialog ad=mb.create();
                        ad.show();
                        ad.setCanceledOnTouchOutside(false); //ne pas fermer on click en dehors ..



                    }
                });
            }
        }
    }
}
