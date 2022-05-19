package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityFormSklad extends AppCompatActivity {

    private int role;
    private TextView namePartTxt;
    private TextView subtypePartTxt;
    private TextView typePartTxt;
    private TextView manufacturePartTxt;
    private TextView countPartTxt;
    private SkladService skladService = new SkladService();
    private String namePart;
    private int partCount;

    public ActivityFormSklad() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_form_skald);
    }


    public void logout(View view) {
        System.out.println("ok");
        //LoginService loginService = new LoginService();

        //loginService.logout();

        setContentView(R.layout.activity_login);
    }

    public void vypisSklad(View view) {

        setContentView(R.layout.activity_vypis_skladu);
    }

    public void vymaz(View view) {
        namePartTxt = (TextView)findViewById(R.id.txtNazevDilu);
        namePartTxt.setText("");

        subtypePartTxt = (TextView)findViewById(R.id.txtTypDiliu);
        subtypePartTxt.setText("");

        typePartTxt = (TextView)findViewById(R.id.txtDruhDilu);
        typePartTxt.setText("");

        manufacturePartTxt = (TextView)findViewById(R.id.txtVyrobce);
        manufacturePartTxt.setText("");

        countPartTxt = (TextView)findViewById(R.id.txtPocetKusu);
        countPartTxt.setText("");
    }

    public void odeslat(View view) {
        namePart = namePartTxt.getText().toString();
        partCount = Integer.parseInt(countPartTxt.getText().toString());

        if(findViewById(findViewById(R.id.radioNaskladni).checkInputConnectionProxy()==true){
            String message = skladService.addItemPiece(namePart, partCount);
            alertView(message);
        }
        else{
            if(findViewById(findViewById(R.id.radioVyskladni).checkInputConnectionProxy()==true){
                String message = skladService.removeItemPiece(namePart, partCount);
                alertView(message);
            }
        }
    }
    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}