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
    private CountDownLatch latch;
    private String message;

    public ActivityFormSklad() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_skald);
        conectionById();
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

        Button btnsendtData = findViewById(R.id.btnOK);
        btnsendtData .setOnClickListener(view -> {
            System.out.println("ok");
            odeslatData();
        });

        Button btnReadQR = findViewById(R.id.btnVypisSklad);
        btnReadQR .setOnClickListener(view -> {
            nactiQR();
        });

        Button btnOK= findViewById(R.id.btnVypisSklad);
        btnOK .setOnClickListener(view -> {
        });

    }

    private void conectionById() {
        namePartTxt = findViewById(R.id.txtNazevDilu);
        subtypePartTxt = findViewById(R.id.txtTypDiliu);
        typePartTxt = findViewById(R.id.txtDruhDilu);
        parametrsPartTxt = findViewById(R.id.txtParametryDilu);
        parametrsPartTxt = findViewById(R.id.txtParametryDilu);
        manufacturePartTxt = findViewById(R.id.txtVyrobce);
        countPartTxt = findViewById(R.id.txtPocetKusu);
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

        namePartTxt.setText("");
        subtypePartTxt.setText("");
        typePartTxt.setText("");
        parametrsPartTxt.setText("");
        manufacturePartTxt.setText("");
        countPartTxt.setText("");
    }

   private void odeslatData() {
        System.out.println("ok");
               boolean ok =false;
               ok = fillCheck(3);
               if (ok == true) {
                   partCount = Integer.parseInt(countPartString);
                   RadioGroup radioGroup = findViewById(R.id.radioGroup);

                   if (partCount > 0) {

                       switch (radioGroup.getCheckedRadioButtonId()) {
                           case R.id.radioNaskladni:
                               ok = fillCheck(1);
                               if (ok == true) {
                                   final CountDownLatch latch = new CountDownLatch(1);
                                   Thread thread = new Thread(new Runnable() {
                                       @Override

                                       public void run() {
                                           try {
                                   message=skladService.addItemPiece(namePart, partCount);
                                               vymaz();
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                           latch.countDown();
                                       }

                                   });
                                   thread.start();
                                   try {
                                       latch.await();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               } else {
                                   message="Pro naskladneni dil musi byt vyplnena pole nazev dilu a pocet kusu";
                               }
                               break;
                           case R.id.radioVyskladni:
                               ok = fillCheck(1);
                               if (ok == true) {

                                   final CountDownLatch latch = new CountDownLatch(1);
                                   Thread thread = new Thread(new Runnable() {
                                       @Override

                                       public void run() {
                                           try {
                                               message=skladService.removeItemPiece(namePart, partCount);
                                               vymaz();
                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                           latch.countDown();
                                       }

                                   });
                                   thread.start();
                                   try {
                                       latch.await();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               } else {
                                   message="Pro vyskladneni dil musi byt vyplnena pole nazev dilu a pocet kusu";

                               }
                               break;
                           case R.id.radioNovyDil:
                               ok = fillCheck(2);
                               if (ok == true) {
                                   final CountDownLatch latch = new CountDownLatch(1);
                                   Thread thread = new Thread(new Runnable() {
                                       @Override

                                       public void run() {
                                           try {
                                               message=skladService.newItem(namePart, typePart, subtypePart, parametrsPart, manufacturePart, partCount);
                                              // alertView(message);
                                               vymaz();

                                           } catch (Exception e) {
                                               e.printStackTrace();
                                           }
                                           latch.countDown();
                                       }

                                   });
                                   thread.start();
                                   try {
                                       latch.await();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }



                               } else {
                                   message="Pro noy dil musi byt vyplnena vsechna pole ve formulari";
                               }
                           default:
                              // message="Nebyl vbrana zadna možnost akce";
                               break;

                       }
                   } else {
                       message="Pocet dilu mus byt vetsi nez 0";
                   }
               } else {
                   message="Nevyplneno pole pocet kusu";
               }
               alertView(message);
           }



    private void nactiQR(){

        ScenerQR scenerQR = new ScenerQR();
        String[] nactenaData = scenerQR.readQR();

        namePartTxt.setText(nactenaData[1]);
        typePartTxt.setText(nactenaData[3]);
        subtypePartTxt.setText(nactenaData[5]);
        parametrsPartTxt.setText(nactenaData[7]);
        manufacturePartTxt.setText(nactenaData[9]);
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
                //alertView(countPartString);
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

