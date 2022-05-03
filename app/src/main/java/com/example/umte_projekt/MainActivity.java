package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.BreakIterator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView partName, partType, partSubtype, partManufacuter, partCouint;
    private RadioButton importPart, exportPart;
    private SkladService skladService;
    private LoginService loginService;
    private int eventMode;
    private String namePart, typePart, subtypePart, manufacture;
    private int countPart;
    private Dil dil;
   // private ScannerLiveView camera;
    //private TextView scannedTV;
  private Button btnScenQR;



    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_vypis_skladu);
      setContentView(R.layout.activity_form_sklad);
       // setContentView(R.layout.activity_login);
      skladService = new SkladService();
      loginService = new LoginService();
      dil = new Dil();

        // referencing and initializing
        // the button and textviews
        btnScenQR = findViewById(R.id.btnSekenKodu);


        // adding listener to the button
        btnScenQR.setOnClickListener(this);

    }


    public void logout(View view) {

        System.out.println("ok");
        setContentView(R.layout.activity_login);
    }

    public void vypisSklad(View view) {
        setContentView(R.layout.activity_vypis_skladu);
    }

    public void formular(View view) {
        setContentView(R.layout.activity_form_sklad);
    }

    public void odslatFormular(View view) {
        System.out.println("ok");

        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);

        partName = (TextView)findViewById(R.id.txtNazevDilu);
        namePart = partName.getText().toString();
        dil.setNamePart(namePart);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioNaskladni:
                        eventMode=1;
                        break;
                    case R.id.radioVyskladni:
                        eventMode=2;
                        break;
                    case R.id.radioNovyDil:
                        eventMode=3;
                    default:
                        System.out.println("nastala chyba");
                        break;
                }
            }
        });
        namePart=partName.getText().toString();
        skladService.eventDepot(eventMode, dil);
    }


    public void vymaz(View view) {
        partName = (TextView)findViewById(R.id.txtNazevDilu);
        partName.setText("");

        partType = (TextView)findViewById(R.id.txtDruhDilu);
        partType.setText("");

        partSubtype = (TextView)findViewById(R.id.txtTypDiliu);
        partSubtype.setText("");

        partManufacuter = (TextView)findViewById(R.id.txtVyrobce);
        partManufacuter.setText("");

        partCouint = (TextView)findViewById(R.id.txtPocetKusu);
        partCouint.setText("");

    }

    public void skenovatKod(View view) {
        System.out.println("ok");


    }


    public void prihlasit(View view) {
        TextView emailTxt = (TextView) findViewById(R.id.txtEmail);
        String email =  emailTxt.getText().toString();
        TextView passwordTxt = (TextView) findViewById(R.id.txtPasword);
        String password = passwordTxt.getText().toString();
        loginService.login(email, password);
        int role = loginService.getRole();
        switch (role) {
            case 1:
                setContentView(R.layout.activity_admin);
                break;
            case 2:
                setContentView(R.layout.activity_form_sklad);
                break;
            case 3:
                setContentView(R.layout.activity_reklamace);

                break;
            default:
                alertView("You really want this?");
                break;
        }
    }

    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Spatne zadane prihlasovaci udaje");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message


                BreakIterator messageText =null;
                messageText.setText(intentResult.getContents());
                BreakIterator messageFormat=null;
                messageFormat.setText(intentResult.getFormatName());
                String text = messageText.toString();
                String[] pomText = text.split(",");


                partName = (TextView)findViewById(R.id.txtNazevDilu);
                partName.setText(pomText[1]);

                partType = (TextView)findViewById(R.id.txtDruhDilu);
                partType.setText(pomText[3]);

                partSubtype = (TextView)findViewById(R.id.txtTypDiliu);
                partSubtype.setText(pomText[5]);

                partManufacuter = (TextView)findViewById(R.id.txtVyrobce);
                partManufacuter.setText(pomText[7]);

                partCouint = (TextView)findViewById(R.id.txtPocetKusu);
                partCouint.setText(pomText[9]);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}