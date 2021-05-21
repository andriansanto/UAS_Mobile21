package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Rewards_Activity extends AppCompatActivity {

    ImageView footerhome,footeract,footerrewards,footeracc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

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
        footeracc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFooterRewards();
            }
        });

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
        Intent intent = new Intent(this, Rewards_Activity.class);
        startActivity(intent);
        finish();
    }
}