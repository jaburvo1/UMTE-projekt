package com.example.umte_projekt;

import android.widget.TextView;


public class ScenerQR {

    private TextView scannedTV;
    private String dataRezult;

    public ScenerQR() {
    }

    public String[] readQR() {
        getQR();
        String[] data = dataRezult.split(";");
        return data;
    }

    private void getQR() {
        // zde bude obsluha qr skeneru
    }

}
