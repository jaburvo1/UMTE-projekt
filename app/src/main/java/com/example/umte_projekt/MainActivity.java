package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountDownLatch;


public class MainActivity extends AppCompatActivity{



    private LoginService loginService;
    private int role;
    private String password;
    private String email;


    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setContentView(R.layout.activity_form_skald);
        loginService = new LoginService();
    }

    public void prihlasit(View view) {
        TextView emailTxt = (TextView) findViewById(R.id.txtEmail);
        email = emailTxt.getText().toString();
        TextView passwordTxt = (TextView) findViewById(R.id.txtPasword);
         password = passwordTxt.getText().toString();
        final CountDownLatch latch = new CountDownLatch(1);
        Thread thread = new Thread(new Runnable() {

            @Override

            public void run() {
                try {
                    System.out.println(email + password);
                   String roleString =loginService.login(email, password);
                System.out.println(roleString);
                role = Integer.parseInt(roleString);
               // System.out.println(role);

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


        Intent intent;
        switch (role) {
            case 1:
                //setContentView(R.layout.activity_admin);
                intent=new Intent(this,ActivityAdmin.class);
                startActivity(intent);
                break;
                case 2:
                    //setContentView(R.layout.activity_form_skald);
                    intent =new Intent(this,ActivityFormSklad.class);
                    startActivity(intent);
                    break;

            case 3:
                //setContentView(R.layout.activity_reklamace);
                intent =new Intent(this,ActivityReklamace.class);
                startActivity(intent);
                break;
            default:
                alertView("spatne zadane prihlaasovaci udaje");
                break;

        }

       // System.out.println(role);
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