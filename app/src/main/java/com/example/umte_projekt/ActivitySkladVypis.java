package com.example.umte_projekt;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CountDownLatch;

public class ActivitySkladVypis extends AppCompatActivity {
    private int role;
    private SkladService skladService;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        skladService = new SkladService();
        readALLItem();
    }

    private void readALLItem() {
        final CountDownLatch latch = new CountDownLatch(2);
        Thread thread = new Thread(() -> {
            try {
                String rezultGet = skladService.getItems();
                items = rezultGet.split(";");

            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.countDown();
        });
        thread.start();
        try {
            latch.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // vypis do do gui

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

    public void formular(View view) {
        setContentView(R.layout.activity_form_skald);
    }
}
