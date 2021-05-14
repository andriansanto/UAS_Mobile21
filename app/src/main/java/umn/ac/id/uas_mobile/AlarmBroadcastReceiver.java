package umn.ac.id.uas_mobile;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private NotificationManagerCompat notif_manager;
    private Notification notif;
    public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";
    public static final String CHANNEL_NAME = "Testing";
    public static final String CHANNEL_DESC = "Test123 masuk";
    public static final int NOTIFICATION_ID = 1337;

    @Override
    public void onReceive(Context context, Intent intent) {
        notif = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSound(null)
                .setContentTitle("Testing pertama")
                .setContentText("1 2 3 1 2 3 test test")
                .setSmallIcon(R.drawable.ic_alarm)
                .setVibrate(new long[0])
                .build();
        notif_manager = NotificationManagerCompat.from(context);

        //mChannel buat API > 26
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setSound(null, null);
            mChannel.setDescription(CHANNEL_DESC);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[0]);

            notif_manager.createNotificationChannel(mChannel);
        }
        notif_manager.notify(1, notif);
    }

}
