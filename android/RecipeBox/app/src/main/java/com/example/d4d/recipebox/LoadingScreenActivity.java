package com.example.d4d.recipebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class LoadingScreenActivity extends Activity {

    //A ProgressDialog object
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_loading_screen);
        // set time to splash out
        final int welcomeScreenDisplay = 3000;

        // create a thread to show splash up to splash time
        Thread welcomeThread = new Thread() {

            int wait = 0;
            @Override
            public void run() {
                try {
                    super.run();
                    // load stuff
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }


                } catch (Exception e) {
                    System.out.println("Welcome bugs: " + e);
                } finally {
                    startActivity(new Intent(LoadingScreenActivity.this,
                            MainActivity.class));
                    finish();
                }
            }
        };
        welcomeThread.start();

    }

}
