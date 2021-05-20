package umn.ac.id.uas_mobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class TrackingNotif_activity extends AppCompatActivity {
    private Context konteks;
    private int rumus_notif;
    private ImageView bullet1, bullet2, bullet3;
    private AlarmManager alarm_manager;

    //Contoh 4 menit (240s x 1000 = 240000ms)
    //FOR DEBUG 2 menit (120s x 1000 = 120000ms)
    private int total_time = 120000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_notif);
        alarm_manager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        //Rumus = 1 menit
        rumus_notif = total_time - 60000;
        bullet1 = findViewById(R.id.bulet_pertama);
        bullet2 = findViewById(R.id.bulet_kedua);
        bullet3 = findViewById(R.id.bulet_ketiga);
        bullet1.setBackgroundResource(R.drawable.pendukung_notif_2);
        bullet3.setBackgroundResource(R.drawable.pendukung_notif_1);


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeReceiver(1);
                bullet1.setBackgroundResource(R.drawable.pendukung_notif_1);
                bullet2.setBackgroundResource(R.drawable.pendukung_notif_2);
                Toast.makeText(TrackingNotif_activity.this, "Pengantar Jemput Sampahmu akan sampai dalam 1 menit lagi!", Toast.LENGTH_LONG).show();
                //Harus sama dengan sisa total_time
                Log.i("DEBUG-TEST", "Testing - Masuk Status 2");
            }
        }, rumus_notif);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                TimeReceiver(1);
                bullet1.setBackgroundResource(R.drawable.pendukung_notif_1);
                bullet2.setBackgroundResource(R.drawable.pendukung_notif_1);
                bullet3.setBackgroundResource(R.drawable.pendukung_notif_2);
                Toast.makeText(TrackingNotif_activity.this, "Pengantar Jemput telah mengambil sampahmu! Credit telah ditambahkan", Toast.LENGTH_LONG).show();
                /*CODE FIREBASE UNTUK PENAMBAHAN JUMLAH CREDIT */
                Log.i("DEBUG-TEST", "Testing - Masuk Status 3");
            }
        }, total_time);
    }

    protected void TimeReceiver(int random_time) {
        Calendar kalender = Calendar.getInstance();
       /* kalender.setTimeInMillis(System.currentTimeMillis());
        kalender.add(Calendar.MINUTE, random_time);
        kalender.set(Calendar.SECOND, 0);
        kalender.set(Calendar.MILLISECOND, 0); */
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent p_intent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        alarm_manager.setExact(
                AlarmManager.RTC_WAKEUP, kalender.getTimeInMillis(), p_intent
        );
    }
}
