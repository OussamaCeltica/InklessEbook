
package com.devs.celtica.inkless.Publications;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.devs.celtica.inkless.Activities.Login;
import com.devs.celtica.inkless.PostServerRequest5;

import java.util.ArrayList;
import java.util.HashMap;

public class Book extends Publication{

    public String lien_resume,photo,maison_edition,nom1,nom2,isbn,description;
    public boolean hasPaperVersion;
    public double prixPaper,prixPdf;

    public Book(String lien_resume, String photo, String maison_edition_auteur, String nom1, String nom2, int id_pub, String date_pub, String type, String lien) {
        super(id_pub, date_pub, type, lien);
        this.lien_resume = lien_resume;
        this.photo = photo;
        this.maison_edition = maison_edition_auteur;
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    public Book(String maison_edition_auteur, String nom1, String nom2, String isbn, String description, boolean hasPaperVersion, double prixPaper, double prixPdf) {
        this.maison_edition = maison_edition_auteur;
        this.nom1 = nom1;
        this.nom2 = nom2;
        this.isbn = isbn;
        this.description = description;
        this.hasPaperVersion = hasPaperVersion;
        this.prixPaper = prixPaper;
        this.prixPdf = prixPdf;
    }

    public void uploadBook(AppCompatActivity c, ArrayList<Uri> files){
        Login.ajax.setUrlWrite("/upload_files.php");
        HashMap<String,String> data=new HashMap<String,String>();

        data.put("writer",Login.reader.id_user+"");
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

            }

            @Override
            public void After(String result) {
                Log.e("rrr","result"+result);
            }
        });
    }
}
