package com.example.smarthome;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String Tag = "MainActivity";
    private Context mContext;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("new token",s);
        getSharedPreferences("_",MODE_PRIVATE).edit().putString("fb",s).apply();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent i = new Intent(this,ListImagesActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,0);
        Notification notification = new Notification.Builder( mContext)
                .setContentTitle("Smart home notification")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager manager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);


       /*if(remoteMessage.getData().size()>0)
       {
           Log.e(Tag, "Message data payload:" + remoteMessage.getData());
       }

       if(remoteMessage.getNotification()!=null)
       {
           Log.d(Tag,"Message Notification Body:" + remoteMessage.getNotification().getBody());

       }*/

    }

}
