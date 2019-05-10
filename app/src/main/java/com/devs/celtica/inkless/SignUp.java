package com.devs.celtica.inkless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    RadioGroup type_user;
    RadioButton reader,writer,narrator;
    EditText username,email,mdp,confirmMdp,ccp;
    TextView nation;
    LinearLayout div_ccp,div_type_user;
    ScrollView div_insc;
    ProgressDialog progress;

    RadioButton type_userChecked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            progress=new ProgressDialog(this);

            type_user=(RadioGroup)findViewById(R.id.signUp_type);
            reader=((RadioButton)findViewById(R.id.signUp_reader));
            writer=((RadioButton)findViewById(R.id.signUp_writer));
            narrator=((RadioButton)findViewById(R.id.signUp_narrator));
            div_type_user=((LinearLayout)findViewById(R.id.div_type_user));
            div_insc=((ScrollView)findViewById(R.id.div_insc_form));
            div_ccp=((LinearLayout)findViewById(R.id.signUp_divCCP));
            type_userChecked=reader;

            //region input form insc
            username=(EditText)findViewById(R.id.signUp_insc_username);
            email=(EditText)findViewById(R.id.signUp_insc_email);
            mdp=(EditText)findViewById(R.id.signUp_insc_mdp);
            confirmMdp=(EditText)findViewById(R.id.signUp_insc_confirmMdp);
            ccp=(EditText)findViewById(R.id.signUp_insc_ccp);
            nation=(TextView)findViewById(R.id.signUp_insc_confirmMdp);
            //endregion

            //region change check type user
            ((LinearLayout)findViewById(R.id.signUp_readerButt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reader.setChecked(true);
                }
            });

            ((LinearLayout)findViewById(R.id.signUp_writer_butt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writer.setChecked(true);
                }
            });

            ((LinearLayout)findViewById(R.id.signUp_narratorButt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    narrator.setChecked(true);
                }
            });

            type_user.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    type_userChecked=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                }
            });
            //endregion

            //region valider type et afficher formulaire d inscription
            ((Button)findViewById(R.id.signUp_type_valider)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    div_type_user.animate()
                            .setDuration(500)
                            .alpha(0)
                            .start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            div_type_user.setVisibility(View.GONE);
                            div_insc.setVisibility(View.VISIBLE);
                            if (type_userChecked==reader){
                                div_ccp.setVisibility(View.GONE);
                            }
                            div_insc.animate()
                                    .setDuration(500)
                                    .alpha(1)
                                    .start();
                        }
                    },520);
                }
            });
            //endregion

            //region valider l inscription
            ((Button)findViewById(R.id.signUp_insc_valider)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(username.getText().toString().equals("") || email.getText().toString().equals("") || mdp.getText().toString().equals("") || confirmMdp.getText().toString().equals("") || nation.getText().toString().equals("") || (type_userChecked!=reader && ccp.getText().toString().equals(""))){

                    }else {
                        final HashMap<String,String> data =new HashMap<String,String> ();

                        data.put("request","inscription");
                        data.put("username",username.getText().toString());
                        data.put("mdp",mdp.getText().toString());
                        data.put("email",email.getText().toString());
                        //data.put("phone",.getText().toString());
                        data.put("nation",nation.getText().toString()+"");
                        if (type_userChecked==reader){
                            data.put("type_user","reader");
                        }else if(type_userChecked==writer){
                            data.put("type_user","writer");
                            data.put("ccp",ccp.getText().toString());
                        }else {
                            data.put("type_user","narrator");
                            data.put("ccp",ccp.getText().toString());
                        }

                        Login.ajax.setUrlWrite("/write.php");
                        Login.ajax.send(data, new PostServerRequest5.doBeforAndAfterGettingData() {
                            @Override
                            public void before() {
                                progress.setTitle("Uploading");
                                progress.setMessage("Please wait...");
                                progress.show();
                            }

                            @Override
                            public void echec(Exception e) {
                                progress.dismiss();
                                e.printStackTrace();

                            }

                            @Override
                            public void After(String result) {
                                progress.dismiss();

                                if(result.equals("email err")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.signUp_email_err),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else if (result.equals("succ")){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.signUp_succ),Toast.LENGTH_SHORT).show();
                                            revenirLogin();
                                        }
                                    });

                                }

                            }
                        });
                    }




                }
            });

            //endregion
        }

    }

    public void revenirLogin(){
        //region Revenir a au Login ..
        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //endregion
    }


}
