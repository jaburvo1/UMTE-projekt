package com.example.umte_projekt;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;



public class SkladService extends AppCompatActivity {
    public SkladService(){

    }
    public void eventDepot(int eventMode){
        if(eventMode==1){
            System.out.println("naskladneno");

        }
        else {
            System.out.println("vyskladneno");
        }
    }

}