package com.devs.celtica.inkless;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private EditText email,mdp;
    public static PostServerRequest5 ajax;
    public static User user;
    public static ReaderFull reader;
    public static Narrator narrator;
    public ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress=new ProgressDialog(this);

        ajax=new PostServerRequest5("https://testingebook.000webhostapp.com");

        email=(EditText)findViewById(R.id.login_email);
        mdp=(EditText)findViewById(R.id.login_mdp);

        Login.reader=new ReaderFull(1, "","", "", "","");

        //region connecter ..
        ((Button)findViewById(R.id.login_cnctButt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().equals("") || mdp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.login_mdp_errRemplisage),Toast.LENGTH_SHORT).show();

                }else {

                    User user=new User(email.getText().toString(),mdp.getText().toString());
                    user.connecter(Login.this);
                }

            }
        });

        //endregion

        //region sign up ..
        ((TextView)findViewById(R.id.login_signUpButt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });
        //endregion
    }
}
