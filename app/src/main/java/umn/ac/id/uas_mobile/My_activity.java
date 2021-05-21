package umn.ac.id.uas_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class My_activity extends AppCompatActivity {
    private Button backtohome;
    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ImageView footerhome,footeract,footerrewards,footeracc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        recyclerView = findViewById(R.id.kotakinfo);

        DocumentReference documentReference = fStore.collection("Users").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<String> actname, actcredit, actdate;
                actname = (ArrayList<String>) value.get("Activity_name");
                actcredit = (ArrayList<String>) value.get("Activity_credit");
                actdate = (ArrayList<String>) value.get("Activity_date");
                Re_adapter re_adapter = new Re_adapter(getApplicationContext(), actname, actcredit, actdate);
                recyclerView.setAdapter(re_adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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

//        backtohome = findViewById(R.id.btn_backhome);

//       backtohome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                back_to_home();
//            }
//        });
//    }

//    protected void back_to_home() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//    }
