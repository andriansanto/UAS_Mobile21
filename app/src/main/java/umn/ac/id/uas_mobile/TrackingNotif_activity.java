package umn.ac.id.uas_mobile;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TrackingNotif_activity extends AppCompatActivity {
    private Context konteks;
    private int rumus_notif;
    private TextView user;
    private ImageView bullet1, bullet2, bullet3;
    private AlarmManager alarm_manager;
    ImageView footerhome,footeract,footerrewards,footeracc;

    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;

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
        user = findViewById(R.id.recash2);

        ArrayList<String> actname = (ArrayList<String>)getIntent().getExtras().getSerializable("actname");
        ArrayList<String> actcredit = (ArrayList<String>)getIntent().getExtras().getSerializable("actcredit");
        ArrayList<String> actdate = (ArrayList<String>)getIntent().getExtras().getSerializable("actdate");
        int credits = getIntent().getIntExtra("credit",0);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fauth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user.setText("Hello "+value.getString("Username"));
            }
        });


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
                Map<String, Object> data = new HashMap<>();
                data.put("Activity_name", actname);
                data.put("Activity_credit", actcredit);
                data.put("Activity_date", actdate);
                data.put("Credit", credits);
                fstore.collection("Users").document(userID)
                        .set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(TrackingNotif_activity.this, "Pickup telah selesai",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TrackingNotif_activity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

                Log.i("DEBUG-TEST", "Testing - Masuk Status 3");
            }
        }, total_time);

        footerhome = findViewById(R.id.home);
        footerhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFooterHome();
            }
        });
        footeracc = findViewById(R.id.account);
        footeracc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFooterAcc();
            }
        });
        footeract = findViewById(R.id.activity);
        footeract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFooterAct();
            }
        });
        footerrewards = findViewById(R.id.rewards);
        footerrewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFooterRewards();
            }
        });
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

    public void openFooterHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    };
    public void openFooterAcc() {
        Intent intent = new Intent(this, Account_activity.class);
        startActivity(intent);
        finish();
    }
    public void openFooterAct() {
        Intent intent = new Intent(this, My_activity.class);
        startActivity(intent);
        finish();
    }
    public void openFooterRewards() {
        Intent intent = new Intent(this, See_voucher_activity.class);
        startActivity(intent);
        finish();
    }
}
