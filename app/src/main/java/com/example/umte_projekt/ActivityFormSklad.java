package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
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
    private String typePart, subtypePart, parametrsPart, manufacturePart;
    private TextView parametrsPartTxt;

    public ActivityFormSklad() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_skald);
        Button btnVymaz = (Button) findViewById(R.id.btnVymazFormular);
        btnVymaz.setOnClickListener(view -> {
            System.out.println("ok");
            vymaz();
        });
    }
    public void logout(View view) {
        System.out.println("ok");
        LoginService loginService = new LoginService();

        loginService.logout();

        setContentView(R.layout.activity_login);
    }

    public void vypisSklad(View view) {

        setContentView(R.layout.activity_vypis_skladu);
    }

    public void vymaz() {
        namePartTxt = (TextView)findViewById(R.id.txtNazevDilu);
        namePartTxt.setText("");

        subtypePartTxt = (TextView)findViewById(R.id.txtTypDiliu);
        subtypePartTxt.setText("");

        typePartTxt = (TextView)findViewById(R.id.txtDruhDilu);
        typePartTxt.setText("");

        //parametrsPartTxt = (TextView) findViewById(R.id.txtParametry);
        parametrsPartTxt.setText("");

        manufacturePartTxt = (TextView)findViewById(R.id.txtVyrobce);
        manufacturePartTxt.setText("");

        countPartTxt = (TextView)findViewById(R.id.txtPocetKusu);
        countPartTxt.setText("");
    }

    public void odeslatData(View view) {
        namePart = namePartTxt.getText().toString();
        partCount = Integer.parseInt(countPartTxt.getText().toString());
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        String message = "";
        if(partCount>0) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.radioNaskladni:
                    message = skladService.addItemPiece(namePart, partCount);
                    break;
                case R.id.radioVyskladni:
                    message = skladService.removeItemPiece(namePart, partCount);
                    break;
                case R.id.radioNovyDil:
                   boolean ok = fillCheck();
                    if (ok == true) {
                        message = skladService.newItem(namePart, typePart, subtypePart, parametrsPart, manufacturePart, partCount);
                    }
                    else{
                        message = "Pro noy dil musi byt vyplnena vsechna pole ve formulari";
                    }
                default:
                    message = "Nebyl vbrana zadna možnost akce";
                    break;

            }
        }
        else {
            message = "Pocet dilu mus byt vetsi nez 0";
        }
        alertView(message);
    }

    public void nactiQR(View view){

        ScenerQR scenerQR = new ScenerQR();
        String[]nactenaData=scenerQR.readQR();

    }
    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ActivityFormSklad.this).create();
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

    private boolean fillCheck() {
        boolean ok;
        typePart = typePartTxt.getText().toString();
        subtypePart =subtypePartTxt.getText().toString();
        parametrsPart = parametrsPartTxt.getText().toString();
        manufacturePart = manufacturePartTxt.getText().toString();
        if(typePart.equals("")|| typePart.equals(" ")||subtypePart.equals("")||
                subtypePart.equals(" ")||parametrsPart.equals("")||parametrsPart.equals(" ")||
                manufacturePart.equals("")||manufacturePart.equals(" ")){
            ok=false;
        }
        else{
            ok=true;
        }
        return ok;
    }

    }