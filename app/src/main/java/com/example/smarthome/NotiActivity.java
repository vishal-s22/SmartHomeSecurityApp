package com.example.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);

        TextView T=findViewById(R.id.noti);

        String msg= getIntent().getStringExtra("message");
        T.setText(msg);
    }
}
