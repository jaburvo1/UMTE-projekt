package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity{


    private TextView partName, partType, partSubtype, partManufacuter, partCouint;
    private RadioButton importPart, exportPart;
    private SkladService skladService;
    private LoginService loginService;
    private int eventMode;
    private String namePart, typePart, subtypePart, manufacture;
    private int countPart;
    private Dil dil;
    private int role;
    // private ScannerLiveView camera;
    //private TextView scannedTV;
    private Button btnScenQR;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginService = new LoginService();
    }

    public void prihlasit(View view) throws InterruptedException {
        TextView emailTxt = (TextView) findViewById(R.id.txtEmail);
        String email = emailTxt.getText().toString();
        TextView passwordTxt = (TextView) findViewById(R.id.txtPasword);
        String password = passwordTxt.getText().toString();
        Thread thread = new Thread(new Runnable() {

            @Override

            public void run() {
                try {
                   String roleString =loginService.login(email, password);
                System.out.println(roleString);
                role = Integer.parseInt(roleString);
                System.out.println(role);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (this) {
            this.wait(100);
            thread.start();


        }
         System.out.println(role);



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
                alertView("spatne zadane prihlaasovaci udaje");
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