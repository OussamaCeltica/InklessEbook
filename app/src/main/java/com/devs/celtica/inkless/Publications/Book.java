
package com.devs.celtica.inkless.Publications;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.PostServerRequest5;
import com.devs.celtica.inkless.R;
import com.devs.celtica.inkless.Users.Writer;

import java.util.ArrayList;
import java.util.HashMap;

public class Book extends Publication{

    public String lien_resume,photo,maison_edition,nom1,nom2,isbn,description,categorie;
    public boolean hasPaperVersion;
    public double prixPaper,prixPdf;
    public Writer auteur;

    public Book(int id_pub, String lien_resume, String lien, String photo, String maison_edition_auteur, String nom1, String nom2, String date_pub,Writer auteur,boolean hasPaperVersion, double prixPaper, double prixPdf) {
        super(id_pub, date_pub, "book", lien);
        this.lien_resume = lien_resume;
        this.photo = photo;
        this.maison_edition = maison_edition_auteur;
        this.nom1 = nom1;
        this.nom2 = nom2;
        this.categorie=categorie;
        this.auteur=auteur;
    }

    public Book(String maison_edition_auteur, String nom1, String nom2, String isbn, String description,String categorie, boolean hasPaperVersion, double prixPaper, double prixPdf) {
        this.maison_edition = maison_edition_auteur;
        this.nom1 = nom1;
        this.nom2 = nom2;
        this.isbn = isbn;
        this.description = description;
        this.hasPaperVersion = hasPaperVersion;
        this.prixPaper = prixPaper;
        this.prixPdf = prixPdf;
        this.categorie=categorie;
    }

    public void uploadBook(final AppCompatActivity c, ArrayList<Uri> files){
        Login.ajax.setUrlWrite("/upload_files.php");
        HashMap<String,String> data=new HashMap<String,String>();

        data.put("writer",Login.reader.id_user+"");
        data.put("categorie",categorie);
        data.put("titre1",nom1);
        data.put("titre2",nom2);
        data.put("prix_paper",prixPaper+"");
        data.put("prix_pdf",prixPdf+"");
        data.put("maison_edition",maison_edition);
        data.put("isbn",isbn+"");
        data.put("book_desc",description+"");
        if (hasPaperVersion)
            data.put("has_paper_version","1");
        else
            data.put("has_paper_version","0");

        Login.ajax.sendWithFiles(data, files, c, new PostServerRequest5.doBeforAndAfterGettingData() {
            @Override
            public void before() {

            }

            @Override
            public void echec(Exception e) {
                e.printStackTrace();
                Toast.makeText(c,c.getResources().getString(R.string.uploadBook_err),Toast.LENGTH_SHORT).show();
                UploadPdf.isSended=false;
                ((UploadPdf)c).progress.dismiss();
            }

            @Override
            public void After(String result) {
                ((UploadPdf)c).progress.dismiss();
                UploadPdf.isSended=false;
                if (result.equals("good")){
                    c.finish();
                    Toast.makeText(c,c.getResources().getString(R.string.uploadBook_succ),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(c,c.getResources().getString(R.string.uploadBook_err),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
