package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

public class Request_submit extends AppCompatActivity {

    ImageView footerhome,footeract,footerrewards,footeracc;
    Button submit;
    TextView date, type, berat, user;

    FirebaseFirestore fstore;
    FirebaseAuth fauth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_submit);
        ArrayList<String> actname = (ArrayList<String>)getIntent().getExtras().getSerializable("actname");
        ArrayList<String> actcredit = (ArrayList<String>)getIntent().getExtras().getSerializable("actcredit");
        ArrayList<String> actdate = (ArrayList<String>)getIntent().getExtras().getSerializable("actdate");
        int credits = getIntent().getIntExtra("credit",0);


        date = findViewById(R.id.date_pickup);
        type = findViewById(R.id.jenis);
        berat = findViewById(R.id.nominalkg);
        submit = findViewById(R.id.request_submit);
        user = findViewById(R.id.recash2);

        fstore = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();
        userID = fauth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user.setText("Hello "+value.getString("Username"));
            }
        });

        date.setText(actdate.get(actdate.size()-1));
        type.setText(getIntent().getExtras().getString("choice"));
        berat.setText(String.valueOf(getIntent().getExtras().getInt("berat",0))+" Kg");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackingNotif_activity.class);
                intent.putExtra("actcredit", actcredit);
                intent.putExtra("actname",actname);
                intent.putExtra("actdate",actdate);
                intent.putExtra("credit", credits);
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
    };
    public void openFooterAcc() {
        Intent intent = new Intent(this, Account_activity.class);
        startActivity(intent);
    }
    public void openFooterAct() {
        Intent intent = new Intent(this, My_activity.class);
        startActivity(intent);
    }
    public void openFooterRewards() {
        Intent intent = new Intent(this, Rewards_Activity.class);
        startActivity(intent);
    }
}