package com.devs.celtica.inkless.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.celtica.inkless.PostServerRequest5;
import com.devs.celtica.inkless.Publications.AfficherBooks;
import com.devs.celtica.inkless.Publications.TypeFiles;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Users.Narrator;
import com.devs.celtica.inkless.Users.Profile;
import com.devs.celtica.inkless.Users.ReaderFull;
import com.devs.celtica.inkless.Users.User;
import com.devs.celtica.inkless.Users.Writer;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    private EditText email,mdp;
    public static PostServerRequest5 ajax;
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

        //startActivity(new Intent(Login.this, AfficherBooks.class));

        //reader=new Writer(4,"","","","","","","");
        //reader.openSelectFile(this, TypeFiles.PHOTO);

        User user=new User("a@a","1");
        user.connecter(Login.this);

        //region connecter ..
        ((Button)findViewById(R.id.login_cnctButt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().equals("") || mdp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.login_errRemplisage),Toast.LENGTH_SHORT).show();

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

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            ajax.setUrlWrite("/tst_up.php");
            Uri uri =data.getData();
            ArrayList<Uri> d=new ArrayList<>();
            d.add(uri);
            HashMap<String,String> hash=new HashMap<>();
            hash.put("v","v");
            ajax.sendWithFiles(hash, d ,Login.this, new PostServerRequest5.doBeforAndAfterGettingData() {
                @Override
                public void before() {
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
                    Log.e("rrr",result+"");
                }
            });
        }
    }
    */
}
