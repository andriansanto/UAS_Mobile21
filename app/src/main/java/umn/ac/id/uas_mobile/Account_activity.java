package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class Account_activity extends AppCompatActivity {

    ImageView footerhome,footeract,footerrewards,footeracc;
    TextView name, point;
    RelativeLayout activity, rewards, setting, logout;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        name = findViewById(R.id.name_user);
        point = findViewById(R.id.total_point_user);

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText(value.getString("NamaLengkap"));
                point.setText(value.getLong("Credit").toString()+" Points");
            }
        });

        activity = findViewById(R.id.relativeLayout1);
        rewards = findViewById(R.id.relativeLayout2);
        setting = findViewById(R.id.relativeLayout3);
        logout = findViewById(R.id. relativeLayout4);

        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_activity.this, My_activity.class);
                startActivity(intent);
                finish();
            }
        });

        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_activity.this, Rewards_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_activity.this, Setting_activity.class);
                startActivity(intent);
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Account_activity.this, "Logged Out",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Account_activity.this, Login_activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

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