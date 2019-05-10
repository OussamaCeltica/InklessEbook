package com.devs.celtica.inkless;

public class Writer extends ReaderFull{

    public String ccp;
    public boolean contrat_writer_valide=false;

    public Writer(int id_user, String nom, String num_tel, String email, String mdp, String photo,String ccp) {
        super(id_user, nom, num_tel, email, mdp, photo);
        this.ccp = ccp;
    }
    
}
