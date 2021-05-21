package umn.ac.id.uas_mobile;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
        LinearLayout btn_activity, btn_profile, btn_rewards, btn_pick;
        TextView Username, Credits;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        String userID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Username = (TextView) findViewById(R.id.username);
            Credits = (TextView) findViewById(R.id.credits);
            Toolbar toolbar = (Toolbar)findViewById(R.id.menuimage);
            setSupportActionBar(toolbar);

            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            userID = fAuth.getCurrentUser().getUid();

            DocumentReference documentReference = fStore.collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    Username.setText(value.getString("Username"));
                    Credits.setText(value.getLong("Credit").toString() + " Points");
                }
            });

            btn_activity = (LinearLayout) findViewById(R.id.activity_dash);
            btn_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMyActivity();
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
                    openProfileActivity();
                }
            });

            btn_rewards = (LinearLayout) findViewById(R.id.reward_dash);
            btn_rewards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { openRewardActivity(); }
            });

            btn_pick = (LinearLayout) findViewById(R.id.pickup_dash);
            btn_pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openPickFormActivity();
                }
            });
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.listmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugan:
                Intent gotoProfile = new Intent(MainActivity.this, Setting_activity.class);
                startActivityForResult(gotoProfile, 1);
                break;

            case R.id.logoutmas:
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "Logged Out",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Login_activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openProfileActivity() {
        Intent intent = new Intent(this, Account_activity.class);
        startActivity(intent);
    }
    public void openMyActivity() {
        Intent intent = new Intent(this, My_activity.class);
        startActivity(intent);
    }
    public void openRewardActivity(){
        Intent intent = new Intent(this, Rewards_Activity.class);
        startActivity(intent);
    }
    public void openPickFormActivity(){
        Intent intent = new Intent(this, PickupFormActivity.class);
        startActivity(intent);
    }
}