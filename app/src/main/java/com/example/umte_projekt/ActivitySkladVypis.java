package com.example.umte_projekt;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountDownLatch;

public class ActivitySkladVypis extends AppCompatActivity {
    private int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void logout(View view) {
        LoginService loginService = new LoginService();
        final CountDownLatch latch = new CountDownLatch(2);
        Thread thread = new Thread(new Runnable() {

            @Override

            public void run() {
                try {
                    String roleString =loginService.logout();
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
            latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_login);
    }
}
