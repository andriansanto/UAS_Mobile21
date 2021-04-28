package umn.ac.id.uas_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Home_activity extends AppCompatActivity {

    ArrayList<Putarlagu> audio_list;
    RecyclerView recyclervieww;
    MediaPlayer medplayr;
    double pos, countdur;
    TextView curr, tot,judulaudio;
    ImageView previous, next, pause;
    SeekBar seekBar;
    int idx_audio = 0;
    public static final int PERMISSION_READ = 0;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listlagu);
        if (checkPermission()) {
            setAudio();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_activity.this);
        builder.setIcon(R.drawable.ic_baseline_check);
        builder.setTitle("Login Berhasil");
        builder.setMessage("Selamat Datang Zefanya Wijaya(00000027182)");
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.listmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugan)
        {
            Intent intent = new Intent(getApplicationContext(), credit_activity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.logoutmas)
        {
            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            medplayr.stop();
            medplayr.release();
            startActivity(intent1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAudio() {
        recyclervieww = (RecyclerView) findViewById(R.id.recycler_view);
        recyclervieww.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclervieww.setItemAnimator(new DefaultItemAnimator());

        curr = (TextView) findViewById(R.id.curr);
        tot = (TextView) findViewById(R.id.tot);
        judulaudio = (TextView) findViewById(R.id.judulaudio);
        previous = (ImageView) findViewById(R.id.previous);
        next = (ImageView) findViewById(R.id.next);
        pause = (ImageView) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);

        audio_list = new ArrayList<>();
        medplayr = new MediaPlayer();

        getAudioFiles();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pos = seekBar.getProgress();
                medplayr.seekTo((int) pos);
            }
        });



        medplayr.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                idx_audio++;
                if (idx_audio < (audio_list.size())) {
                    playAudio(idx_audio);
                } else {
                    idx_audio = 0;
                    playAudio(idx_audio);
                }

            }
        });

        if (!audio_list.isEmpty()) {
            playAudio(idx_audio);
            previousAudio();
            nextAudio();
            setPause();
        }
    }


    public void playAudio(int pos) {
        try  {
            medplayr.reset();
            medplayr.setDataSource(this, audio_list.get(pos).getaudUri());
            medplayr.prepare();
            medplayr.start();
            pause.setImageResource(R.drawable.lagiplay);
            judulaudio.setText(audio_list.get(pos).getjudulaud());
            idx_audio=pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAudioProgress();
    }


    public void setAudioProgress() {
        pos = medplayr.getCurrentPosition();
        countdur = medplayr.getDuration();


        tot.setText(timerConversion((long) countdur));
        curr.setText(timerConversion((long) pos));
        seekBar.setMax((int) countdur);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    pos = medplayr.getCurrentPosition();
                    curr.setText(timerConversion((long) pos));
                    seekBar.setProgress((int) pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }


    public void previousAudio() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_audio > 0) {
                    idx_audio--;
                    playAudio(idx_audio);
                } else {
                    idx_audio = audio_list.size() - 1;
                    playAudio(idx_audio);
                }
            }
        });
    }


    public void nextAudio() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idx_audio < (audio_list.size()-1)) {
                    idx_audio++;
                    playAudio(idx_audio);
                } else {
                    idx_audio = 0;
                    playAudio(idx_audio);
                }
            }
        });
    }


    public void setPause() {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medplayr.isPlaying()) {
                    medplayr.pause();
                    pause.setImageResource(R.drawable.lagipause);
                    pause.setImageResource(R.drawable.lagipause);
                } else {
                    medplayr.start();
                    pause.setImageResource(R.drawable.lagiplay);
                }
            }
        });
    }


    public String timerConversion(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }


    public void getAudioFiles() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Putarlagu modelAudio = new Putarlagu();
                modelAudio.setjudulaud(title);
                modelAudio.setartist(artist);
                modelAudio.setaudUri(Uri.parse(url));
                modelAudio.setcountduration(duration);
                audio_list.add(modelAudio);

            } while (cursor.moveToNext());
        }

        Adapt adapter = new Adapt(this, audio_list);
        recyclervieww.setAdapter(adapter);

        adapter.setOnItemClickListener(new Adapt.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                playAudio(pos);
            }
        });
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else {
                        setAudio();
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (medplayr!=null){
            medplayr.release();
        }
    }
}