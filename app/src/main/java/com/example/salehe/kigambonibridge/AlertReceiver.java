package com.example.salehe.kigambonibridge;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Salehe on 7/3/2016.
 */
public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        createNotification(context, "Kigamboni bridge Notification", "Bridge situation report", "Alert");
    }

    public void createNotification(Context context,String msg,String msgText,String msgAlert){

        PendingIntent notificIntent = PendingIntent.getActivity(context,0,new Intent(context,BridgeSituation.class),0);
        NotificationCompat.Builder mBuilder =  new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.accident)
                .setContentTitle(msg)
                .setTicker(msgAlert)
                .setContentText(msgText);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1,mBuilder.build());
    }
}
