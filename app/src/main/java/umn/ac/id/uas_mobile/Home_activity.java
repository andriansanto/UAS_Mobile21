package umn.ac.id.uas_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class Home_activity extends AppCompatActivity {
    //Untuk sementara, notif progress taro disini dlu.
    private final static int TIME_LIMIT = 3;
    private AlarmManager alarm_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarm_manager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        TimeReceiver(4);
    }

    protected void TimeReceiver(int random_time) {
        int rumus;
        Calendar kalender = Calendar.getInstance();
        rumus = random_time - TIME_LIMIT;
        kalender.setTimeInMillis(System.currentTimeMillis());
        kalender.add(Calendar.MINUTE, rumus);
        kalender.set(Calendar.SECOND, 0);
        kalender.set(Calendar.MILLISECOND, 0);

        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent p_intent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        alarm_manager.setExact(
                AlarmManager.RTC_WAKEUP, kalender.getTimeInMillis(), p_intent
        );
    }
}