package umn.ac.id.uas_mobile;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TrackingNotif_activity extends AppCompatActivity {
    private Context konteks;
    private int rumus_notif;
    private ImageView notif1, notif2, notif3;

    //Contoh 4 menit (240s x 1000 = 240000ms)
    private int total_time = 240000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_notif);
        rumus_notif = total_time - 80000;
        notif1 = findViewById(R.id.notif1);
        notif2 = findViewById(R.id.notif2);
        notif3 = findViewById(R.id.notif3);
        notif1.setBackgroundResource(R.drawable.pendukung_notif_2);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                notif1.setBackgroundResource(R.drawable.pendukung_notif_1);
                notif2.setBackgroundResource(R.drawable.pendukung_notif_2);
                Log.i("DEBUG-TEST", "Pengantar Jemput Sampahmu akan sampai dalam 1 menit lagi!");
            }
        }, rumus_notif);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                notif1.setBackgroundResource(R.drawable.pendukung_notif_1);
                notif2.setBackgroundResource(R.drawable.pendukung_notif_1);
                notif3.setBackgroundResource(R.drawable.pendukung_notif_2);
                Log.i("DEBUG-TEST", "Pengantar Jemput telah mengambil Sampahmu!");
            }
        }, total_time);
    }
}
