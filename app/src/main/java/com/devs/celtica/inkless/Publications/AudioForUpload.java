package com.devs.celtica.inkless.Publications;

import android.net.Uri;

/**
 * Created by celtica on 21/05/19.
 */

public class AudioForUpload extends Audio {
    Uri audioFile;

    public AudioForUpload(String nom,Uri audioFile) {
        this.audioFile = audioFile;
        this.nom=nom;
    }

    public AudioForUpload() {
         this.nom=null;
         this.audioFile=null;
    }
}
