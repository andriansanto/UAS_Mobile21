package umn.ac.id.uas_mobile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private LinearLayout btn_activity, btn_profile, btn_rewards, btn_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_activity = (LinearLayout) findViewById(R.id.activity_dash);
        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        btn_profile = (LinearLayout) findViewById(R.id.profile_dash);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        btn_profile = (LinearLayout) findViewById(R.id.profile_dash);
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert activity//
            }
        });

        btn_rewards = (LinearLayout) findViewById(R.id.reward_dash);
        btn_rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out",Toast.LENGTH_SHORT).show();
                openLoginActivity();
            }
        });

        btn_pick = (LinearLayout) findViewById(R.id.pickup_dash);
        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert Activity here//
            }
        });
    }
    public void openProfileActivity() {
        Intent intent = new Intent(this, credit_activity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, Login_activity.class);
        startActivity(intent);
    }
}