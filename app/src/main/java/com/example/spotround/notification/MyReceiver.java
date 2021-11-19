package com.example.spotround.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.spotround.LoginActivity;
import com.example.spotround.R;
import com.example.spotround.SplashActivity;

public class MyReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID="10001";
    private final static String default_notification_channel_id="default";
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Intent intent1 = new Intent(context, MyNewIntentService.class);
        //context.startService(intent1);
        /*Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Spot Round");
        builder.setContentText("Notification");
        //builder.setSmallIcon(R.drawable.whatever);
        switch (intent.getAction()) {
            case "applicationFromFillingStart":
                builder.setContentText("Fill your application");
                break;
            case "Round1Start":
                builder.setContentText("Set Preference List for Round 1");
                break;
            case "Round1Result":
                builder.setContentText("Round 1 Results are out");
                break;
            case "Round2Start":
                builder.setContentText("Set Preference List for Round 2");
                break;
            case "Round2Result":
                builder.setContentText("Round 2 Results are out");
                break;
            case "Round3Start":
                builder.setContentText("Set Preference List for Round 3");
                break;
            case "Round3Result":
                builder.setContentText("Round 3 Results are out");
                break;
        }
        Intent notifyIntent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);*/

        /*NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Spot Round");
        switch (intent.getAction()) {
            case "applicationFromFillingStart":
                builder.setContentText("Fill your application");
                break;
            case "Round1Start":
                builder.setContentText("Set Preference List for Round 1");
                break;
            case "Round1Result":
                builder.setContentText("Round 1 Results are out");
                break;
            case "Round2Start":
                builder.setContentText("Set Preference List for Round 2");
                break;
            case "Round2Result":
                builder.setContentText("Round 2 Results are out");
                break;
            case "Round3Start":
                builder.setContentText("Set Preference List for Round 3");
                break;
            case "Round3Result":
                builder.setContentText("Round 3 Results are out");
                break;
        }
        Notification notification = builder.build();
        int notificationID = 0;
        notificationManager.notify(notificationID, notification);*/
        //
//Working
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyLemubit")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Spot Round")
                .setContentText("Notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        switch (intent.getAction()) {
            case "applicationFromFillingStart":
                builder.setContentText("Fill your application");
                break;
            case "Round1Start":
                builder.setContentText("Set Preference List for Round 1");
                break;
            case "Round1Result":
                builder.setContentText("Round 1 Results are out");
                break;
            case "Round2Start":
                builder.setContentText("Set Preference List for Round 2");
                break;
            case "Round2Result":
                builder.setContentText("Round 2 Results are out");
                break;
            case "Round3Start":
                builder.setContentText("Set Preference List for Round 3");
                break;
            case "Round3Result":
                builder.setContentText("Round 3 Results are out");
                break;
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(200, builder.build());*/
//
        Intent resultIntent = new Intent(context, SplashActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 100, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,default_notification_channel_id)
                .setContentTitle("Spot Round")
                .setContentText("Notification")
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        switch (intent.getAction()) {
            case "applicationFromFillingStart":
                builder.setContentText("Fill your application");
                builder.setContentInfo("Fill your application");
                break;
            case "Round1Start":
                builder.setContentText("Set Preference List for Round 1");
                builder.setContentInfo("Set Preference List for Round 1");
                break;
            case "Round1Result":
                builder.setContentText("Round 1 Results are out");
                builder.setContentInfo("Round 1 Results are out");
                break;
            case "Round2Start":
                builder.setContentText("Set Preference List for Round 2");
                builder.setContentInfo("Set Preference List for Round 2");
                break;
            case "Round2Result":
                builder.setContentText("Round 2 Results are out");
                builder.setContentInfo("Round 2 Results are out");
                break;
            case "Round3Start":
                builder.setContentText("Set Preference List for Round 3");
                builder.setContentInfo("Set Preference List for Round 3");
                break;
            case "Round3Result":
                builder.setContentText("Round 3 Results are out");
                builder.setContentInfo("Round 3 Results are out");
                break;
        }
        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
        {
            int importance=NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel(NOTIFICATION_CHANNEL_ID,"NOTIFICATION_CHANNEL_NAME",importance);
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            assert manager != null;
            manager.createNotificationChannel(channel);
        }
        assert manager!=null;
        manager.notify((int )System.currentTimeMillis(),builder.build());
    }
}
