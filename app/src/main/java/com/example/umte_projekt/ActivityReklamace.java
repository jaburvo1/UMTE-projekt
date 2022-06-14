package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountDownLatch;

public class ActivityReklamace extends AppCompatActivity{


    private String loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reklamace);
       Button btnLoginOut;
        btnLoginOut = findViewById(R.id.btnLoginOutReklamace);
        btnLoginOut.setOnClickListener(view -> {
            System.out.println("ok");
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

           if(loginOut.equals("")){
               alertView("Chyba odhláseni");

           }
           else {
               //setContentView(R.layout.activity_login);
               Intent intent =new Intent(this,MainActivity.class);
               startActivity(intent);
           }

        });



    }

    private void alertView(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(ActivityReklamace.this).create();
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
