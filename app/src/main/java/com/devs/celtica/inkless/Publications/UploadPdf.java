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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

            //region uploadResumePdf ..
            resumeButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login.reader.openSelectFile(UploadPdf.this, TypeFiles.PDF);
                    request = BookFiles.RESUME;
                }
            });
            //endregion

            //region uploadCompletePdf ..
            bookButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Login.reader.openSelectFile(UploadPdf.this, TypeFiles.PDF);
                    request = BookFiles.BOOK_COMPLET;
                }
            });
            //endregion

            //region photo Book ..
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
            });
            //endregion

            //region publier le book ..
            ((TextView)findViewById(R.id.uploadPdf_publier)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isSended) {
                        isSended=true;
                        Book b=new Book(maisonEdition.getText().toString()+"",titre1.getText().toString()+"",titre2.getText().toString()+"",isbn.getText().toString()+"",decription.getText().toString()+"",category.getText().toString(),hasPaperVersion.isChecked(),Double.parseDouble(prixPaper.getText().toString()),Double.parseDouble(prixPdf.getText().toString()));
                        b.uploadBook(UploadPdf.this,files);
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
