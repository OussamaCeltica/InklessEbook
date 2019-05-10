/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devs.celtica.inkless;

/**
 *
 * @author INFO
 */
public class Narrator extends User{
    public String ccp;
    
    public Narrator(int id_user, String nom, String num_tel, String email, String mdp, String photo,String ccp) {
        super(id_user, nom, num_tel, email, mdp, photo);
        this.ccp=ccp;
    }
    
}
