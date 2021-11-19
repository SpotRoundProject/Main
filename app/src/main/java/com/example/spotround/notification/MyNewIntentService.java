package com.example.spotround.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

import com.example.spotround.LoginActivity;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
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
        Intent notifyIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
