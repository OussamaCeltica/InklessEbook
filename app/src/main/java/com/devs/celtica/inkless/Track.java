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
public class Track {
    
private int id_track;
private String lien;

    public Track(int id_track, String lien) {
        this.id_track = id_track;
        this.lien = lien;
    }

    public int getId_track() {
        return id_track;
    }

    public void setId_track(int id_track) {
        this.id_track = id_track;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }
    

    
}
