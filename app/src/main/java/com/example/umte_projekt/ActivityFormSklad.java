package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountDownLatch;

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
    private String typePart, subtypePart, parametrsPart, manufacturePart, countPartString;
    private TextView parametrsPartTxt;
    private String loginOut;

    public ActivityFormSklad() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_skald);
        Button btnVymaz =  findViewById(R.id.btnVymazFormular);
        btnVymaz.setOnClickListener(view -> {
            //System.out.println("ok");
            vymaz();
        });
        Button btnLoginOut = findViewById(R.id.btnLoginOutSkladForm);
        btnLoginOut.setOnClickListener(view -> {
            logout();
        });

        Button btnShowDepot = findViewById(R.id.btnVypisSklad);
        btnShowDepot .setOnClickListener(view -> {
            vypisSklad();
        });

        Button btnsendtData = findViewById(R.id.btnVypisSklad);
        btnsendtData .setOnClickListener(view -> {
            odeslatData();
        });

        Button btnReadQR = findViewById(R.id.btnVypisSklad);
        btnReadQR .setOnClickListener(view -> {
            nactiQR();
        });

    }
    private void logout() {
        loginOut="";
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            try {
                LoginService loginService = new LoginService();
                loginOut =loginService.logout();
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });
        thread.start();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//zde ošettřit výjimku pro neuspesne odhlaseni
        //if(loginOut.equals("")){
        //    alertView("Chyba odhláseni");

    //    }
       // else {
            //setContentView(R.layout.activity_login);
            Intent intent =new Intent(this,MainActivity.class);
            startActivity(intent);
       // }
    }
    

    private void vypisSklad() {
        Intent intent =new Intent(this,ActivitySkladVypis.class);
        startActivity(intent);
    }

    public void vymaz() {
        namePartTxt = findViewById(R.id.txtNazevDilu);
        namePartTxt.setText("");

        subtypePartTxt = findViewById(R.id.txtTypDiliu);
        subtypePartTxt.setText("");

        typePartTxt = findViewById(R.id.txtDruhDilu);
        typePartTxt.setText("");

       parametrsPartTxt = findViewById(R.id.txtParametryDilu);
        parametrsPartTxt.setText("");

        manufacturePartTxt = findViewById(R.id.txtVyrobce);
        manufacturePartTxt.setText("");

        countPartTxt = findViewById(R.id.txtPocetKusu);
        countPartTxt.setText("");
    }

   private void odeslatData() {

       String message = "";
       boolean ok;
       ok = fillCheck(3);
       if(ok==true) {
           partCount = Integer.parseInt(countPartString);
           RadioGroup radioGroup = findViewById(R.id.radioGroup);

           if (partCount > 0) {

               switch (radioGroup.getCheckedRadioButtonId()) {
                   case R.id.radioNaskladni:
                       ok = fillCheck(1);
                       if (ok == true) {
                           message = skladService.addItemPiece(namePart, partCount);
                       } else {
                           message = "Pro naskladneni dil musi byt vyplnena pole nazev dilu a pocet kusu";
                       }
                       break;
                   case R.id.radioVyskladni:
                       ok = fillCheck(1);
                       if (ok == true) {
                           message = skladService.removeItemPiece(namePart, partCount);
                       } else {
                           message = "Pro vyskladneni dil musi byt vyplnena pole nazev dilu a pocet kusu";

                       }
                       break;
                   case R.id.radioNovyDil:
                       ok = fillCheck(2);
                       if (ok == true) {
                           message = skladService.newItem(namePart, typePart, subtypePart, parametrsPart, manufacturePart, partCount);
                       } else {
                           message = "Pro noy dil musi byt vyplnena vsechna pole ve formulari";
                       }
                   default:
                       message = "Nebyl vbrana zadna možnost akce";
                       break;

               }
           } else {
               message = "Pocet dilu mus byt vetsi nez 0";
           }
       }
       else{
           message="Nevyplneno pole pocet kusu";
       }
        alertView(message);
    }

    private void nactiQR(){

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

    private boolean fillCheck(int modeFillCheck) {
        boolean ok = false;
        switch (modeFillCheck) {
            case 1:
                namePart = namePartTxt.getText().toString();
                if (namePart.equals("")||namePart.equals(" ")){
                    ok = false;
                }
                else {
                    ok = true;
                }
                break;

            case 2:
            namePart = namePartTxt.getText().toString();
            typePart = typePartTxt.getText().toString();
        subtypePart = subtypePartTxt.getText().toString();
        parametrsPart = parametrsPartTxt.getText().toString();
        manufacturePart = manufacturePartTxt.getText().toString();
        if (namePart.equals("")||namePart.equals(" ")||typePart.equals("") || typePart.equals(" ") || subtypePart.equals("") ||
                subtypePart.equals(" ") || parametrsPart.equals("") || parametrsPart.equals(" ") ||
                manufacturePart.equals("") || manufacturePart.equals(" ")) {
            ok = false;
        } else {
            ok = true;
        }
        break;
            case 3:
                countPartString = countPartTxt.getText().toString();
                if(countPartString.equals("")||countPartString.equals(" ")){
                    ok=false;
                }
                else {
                    ok = true;
                }
                break;
    }
        return ok;
    }

    }