package com.devs.celtica.inkless.Publications;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.R;

import java.util.ArrayList;

public class UploadPdf extends AppCompatActivity {
    TextView resumeButt,bookButt,photoButt,category;
    BookFiles request;
    ScrollView form1;
    LinearLayout form2;
    CheckBox hasPaperVersion;
    EditText titre1,titre2,prixPaper,prixPdf,maisonEdition,isbn,decription;
    public  static boolean isSended=false,isForm1=true;
    public ProgressDialog progress;
    ArrayList<Uri> files=new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        if (savedInstanceState != null) {
            //region Revenir a au Login ..
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //endregion
        }else {

            progress=new ProgressDialog(this);

            resumeButt = (TextView) findViewById(R.id.uploadPdf_resumeButt);
            bookButt = (TextView) findViewById(R.id.uploadPdf_bookButt);
            photoButt = (TextView) findViewById(R.id.uploadPdf_photoButt);
            form1 = (ScrollView) findViewById(R.id.uploadPdf_form1);
            form2 = (LinearLayout) findViewById(R.id.uploadPdf_form2);

            //region infos book ..
            category=(TextView)findViewById(R.id.uploadPdf_category);
            titre1=(EditText)findViewById(R.id.uploadPdf_titre1);
            titre2=(EditText)findViewById(R.id.uploadPdf_titre2);
            hasPaperVersion = (CheckBox) findViewById(R.id.uploadPdf_hasPdfPaper);
            prixPaper = (EditText) findViewById(R.id.uploadPdf_prixPaper);
            prixPdf = (EditText) findViewById(R.id.uploadPdf_prixPdf);
            maisonEdition = (EditText) findViewById(R.id.uploadPdf_maisonEdition);
            isbn = (EditText) findViewById(R.id.uploadPdf_isbn);
            decription=((EditText)findViewById(R.id.uploadPdf_bookDesc));
            //endregion



            //region getResumePdf file ..
            resumeButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login.reader.openSelectFile(UploadPdf.this, TypeFiles.PDF);
                    request = BookFiles.RESUME;
                }
            });
            //endregion

            //region getCompletePdf file ..
            bookButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login.reader.openSelectFile(UploadPdf.this, TypeFiles.PDF);
                    request = BookFiles.BOOK_COMPLET;
                }
            });
            //endregion

            //region getphotoBook file..
            photoButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login.reader.openSelectFile(UploadPdf.this, TypeFiles.PHOTO);
                    request = BookFiles.PHOTO;
                }
            });
            //endregion

            //region valider form 1 ..
            ((Button) findViewById(R.id.uploadPdf_nextButt)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(resumeButt.getText().toString().equals("") || bookButt.getText().toString().equals("") || photoButt.getText().toString().equals("") || titre1.getText().toString().equals("") || titre2.getText().toString().equals("") || decription.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.signUp_errRemplisage),Toast.LENGTH_SHORT).show();
                    }else {
                        isForm1=false;
                        form1.animate()
                                .setDuration(500)
                                .alpha(0)
                                .start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                form1.setVisibility(View.GONE);
                                form2.setVisibility(View.VISIBLE);

                                form2.animate()
                                        .setDuration(500)
                                        .alpha(1)
                                        .start();
                            }
                        }, 520);
                    }
                }
            });
            //endregion

            //region on prixpaper check incheck ..
            hasPaperVersion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) ((LinearLayout)prixPaper.getParent()).setVisibility(View.VISIBLE);
                    else  ((LinearLayout)prixPaper.getParent()).setVisibility(View.GONE);
                }
            });
            //endregion

            //region publier le book ..
            ((TextView)findViewById(R.id.uploadPdf_publier)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (!isSended) {
                        if(prixPdf.getText().equals("")){
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.signUp_errRemplisage),Toast.LENGTH_SHORT).show();
                        }else {
                            if(hasPaperVersion.isChecked()){
                                if (prixPaper.getText().toString().equals("")){
                                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.signUp_errRemplisage),Toast.LENGTH_SHORT).show();
                                }else {
                                    progress.setTitle(getResources().getString(R.string.uploadBook_uploadWait));
                                    progress.show();
                                    isSended = true;
                                    Book b = new Book(maisonEdition.getText().toString() + "", titre1.getText().toString() + "", titre2.getText().toString() + "", isbn.getText().toString() + "", decription.getText().toString() + "", category.getText().toString(), hasPaperVersion.isChecked(), Double.parseDouble(prixPaper.getText().toString()), Double.parseDouble(prixPdf.getText().toString()));
                                    b.uploadBook(UploadPdf.this, files);
                                }
                            }else {
                                progress.setTitle(getResources().getString(R.string.uploadBook_uploadWait));
                                progress.show();
                                isSended = true;
                                Book b = new Book(maisonEdition.getText().toString() + "", titre1.getText().toString() + "", titre2.getText().toString() + "", isbn.getText().toString() + "", decription.getText().toString() + "", category.getText().toString(), hasPaperVersion.isChecked(), 0, Double.parseDouble(prixPdf.getText().toString()));
                                b.uploadBook(UploadPdf.this, files);
                            }

                        }
                    }else {
                        progress.setTitle(getResources().getString(R.string.uploadBook_uploadWait));
                        progress.show();
                    }



                }
            });
            //endregion

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri file=data.getData();
            switch (request){
                case RESUME:{
                    resumeButt.setText(Login.reader.getFilePath(getApplicationContext(),file));
                    files.add(0,file);
                }break;
                case BOOK_COMPLET:{
                    bookButt.setText(Login.reader.getFilePath(getApplicationContext(),file));
                    files.add(1,file);
                }
                break;
                case PHOTO:{
                    photoButt.setText(Login.reader.getFilePath(getApplicationContext(),file));
                    files.add(2,file);
                }
            }


        }
    }

    @Override
    public void onBackPressed() {
        if(!isSended){
            if (!isForm1) {
                isForm1=true;
                form2.animate()
                        .setDuration(500)
                        .alpha(0)
                        .start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        form2.setVisibility(View.GONE);
                        form1.setVisibility(View.VISIBLE);

                        form1.animate()
                                .setDuration(500)
                                .alpha(1)
                                .start();
                    }
                }, 520);
            }else {
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }
}
