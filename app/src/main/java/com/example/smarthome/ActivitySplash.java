
package com.example.smarthome;
//import com.microsoft.windowsazure.mobileservices.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import java.lang.Thread;

public class ActivitySplash extends AppCompatActivity {

   // private MobileServiceClient mClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        FirebaseApp.initializeApp(this);

        /*mClient=new MobileServiceClient(
                "https://smarthomsec.azurewebsites.net",
                this
        );*/
    }

    public void onResume() {
        super.onResume();
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                startActivity(new Intent(ActivitySplash.this, activity_login.class));
                finish();
            }

        };
        t.start();
    }
}
