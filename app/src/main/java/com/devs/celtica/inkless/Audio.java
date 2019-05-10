/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless;


public class Audio extends Publication{
 public String nom;

    public Audio(String nom, int id_pub, String date_pub, String type, String lien) {
        super(id_pub,date_pub, type, lien);
        this.nom = nom;
    }
 
    
}
