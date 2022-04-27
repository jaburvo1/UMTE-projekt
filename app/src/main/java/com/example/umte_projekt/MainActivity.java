package com.example.umte_projekt;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_vypis_skladu);
      setContentView(R.layout.activity_form_sklad);


    }


    public void logout(View view) {
        System.out.println("ok");
    }

    public void vypisSklad(View view) {
        setContentView(R.layout.activity_vypis_skladu);
    }

    public void formular(View view) {
        setContentView(R.layout.activity_form_sklad);
    }
}