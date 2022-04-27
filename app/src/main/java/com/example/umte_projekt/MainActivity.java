package com.example.umte_projekt;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private TextView namePart, typePart, subtypePart, manufacuter, couintPart;
    private RadioButton importPart, exportPart;
    private SkladService skladService;
    private int eventMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_vypis_skladu);
      setContentView(R.layout.activity_form_sklad);
      skladService = new SkladService();


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

    public void odslatFormular(View view) {
        //System.out.println("ok");
        //importPart = (RadioButton)findViewById(R.id.radioVyskladni);
        //exportPart = (RadioButton)findViewById(R.id.radioVyskladni);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioNaskladni:
                        eventMode=1;
                        break;
                    case R.id.radioVyskladni:
                        eventMode=2;
                        break;
                    default:
                        System.out.println("nastala chyba");
                        break;
                }
            }
        });
        skladService.eventDepot(eventMode);
    }


    public void vymaz(View view) {
        namePart = (TextView)findViewById(R.id.txtNazevDilu);
        namePart.setText("");

        typePart = (TextView)findViewById(R.id.txtDruhDilu);
        typePart.setText("");

        subtypePart = (TextView)findViewById(R.id.txtTypDiliu);
        subtypePart.setText("");

        manufacuter = (TextView)findViewById(R.id.txtVyrobce);
        manufacuter.setText("");

        couintPart = (TextView)findViewById(R.id.txtPocetKusu);
        couintPart.setText("");

    }

    public void skenovatKod(View view) {
        System.out.println("ok");
    }
}