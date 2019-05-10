
package com.devs.celtica.inkless;


public class Book extends Publication{

    private String lien_resume,photo,maison_edition_auteur,nom1,nom2;

    public Book(String lien_resume, String photo, String maison_edition_auteur, String nom1, String nom2, int id_pub, String date_pub, String type, String lien) {
        super(id_pub, date_pub, type, lien);
        this.lien_resume = lien_resume;
        this.photo = photo;
        this.maison_edition_auteur = maison_edition_auteur;
        this.nom1 = nom1;
        this.nom2 = nom2;
    }

    
}
