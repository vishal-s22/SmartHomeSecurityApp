package com.example.smarthome;

import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    public String str;
    private static final String TAG = "MainActivity";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intent = getIntent();
        str = intent.getStringExtra("message");

        //Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();

        tabLayout= (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar_id);
        viewPager=(ViewPager) findViewById(R.id.viewpager_id);


        viewpageradapter adapter;
        adapter = new viewpageradapter(getSupportFragmentManager());

        adapter.addfragment(new fragmentmyhome(), "My Home");
        adapter.addfragment(new fragmentnotification(), "Controls");
        adapter.addfragment(new frangmentaccount(), "Account");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
         FirebaseDatabase database = FirebaseDatabase.getInstance();
        final  DatabaseReference reference = database.getReference("check").child("intruder");
        //use this reference.setValue(10000);

       // public static final String CHANNEL_ID = "10001";
        //public static final String channel_name  = "vishal";
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object x=dataSnapshot.getValue();

                long num= (long) x;
                if(num==1) {
                    //Toast.makeText(getApplicationContext(), "Fucked up", Toast.LENGTH_SHORT).show();
                    reference.setValue(0);

                    String mg="kjdscjbdwkjc";


                    Intent intent = new Intent(MainActivity.this, ListImagesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "2")
                            .setSmallIcon(R.drawable.userid)
                            .setContentTitle("SOMEONE AT YOUR DOOR!!")
                            .setContentText("know the person")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);



                    createNotificationChannel();

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify(1, builder.build());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("2", name, importance);
            // channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    String getstr()
    {
        return str;
    }

}
