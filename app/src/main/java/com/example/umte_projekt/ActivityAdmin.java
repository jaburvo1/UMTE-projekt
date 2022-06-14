package com.example.umte_projekt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.umte_projekt.databinding.ActivityAdminBinding;

import java.util.concurrent.CountDownLatch;

public class ActivityAdmin extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityAdminBinding binding;
    private String loginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnLoginOut;
        btnLoginOut = findViewById(R.id.btnLoginOutAdmin);
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
        AlertDialog alertDialog = new AlertDialog.Builder(ActivityAdmin.this).create();
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