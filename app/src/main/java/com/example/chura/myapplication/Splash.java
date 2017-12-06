package com.example.chura.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by chura on 6/27/17.
 */

public class Splash extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle splashscreen) {
        super.onCreate(splashscreen);
        setContentView(R.layout.splash);
        Thread myThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Intent splashscreen = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(splashscreen);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        };
        myThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
