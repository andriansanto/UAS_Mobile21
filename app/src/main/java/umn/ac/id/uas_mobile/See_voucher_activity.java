package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class See_voucher_activity  extends AppCompatActivity {

    Button netplix, kfc, music, mole;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    ArrayList<String> actname, actdate, actcredit;
    TextView points,name;
    int credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seevoucher);

        netplix = findViewById(R.id.buy_netplix);
        kfc = findViewById(R.id.buy_kfc);
        music = findViewById(R.id.buy_musicfy);
        mole = findViewById(R.id.buy_dm);
        points = findViewById(R.id.points_user);
        name = findViewById(R.id.nama_user);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                actname = (ArrayList<String>) value.get("Activity_name");
                actcredit = (ArrayList<String>) value.get("Activity_credit");
                actdate = (ArrayList<String>) value.get("Activity_date");
                credits = value.getLong("Credit").intValue();
                points.setText(credits +" Points");
                name.setText(value.getString("Username"));
            }
        });

        netplix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (credits < 25000)
                {
                    Toast.makeText(See_voucher_activity.this, "Insufficient Points",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                actname.add("Netplix Premium");
                actcredit.add("- 25000");
                credits -= 25000;
                actdate.add(formattedDate);
                Map<String, Object> data = new HashMap<>();
                data.put("Activity_name", actname);
                data.put("Activity_credit", actcredit);
                data.put("Activity_date", actdate);
                data.put("Credit", credits);
                fStore.collection("Users").document(userID)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(See_voucher_activity.this, "Bought Voucher", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(See_voucher_activity.this, "Database Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        kfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (credits < 30000)
                {
                    Toast.makeText(See_voucher_activity.this, "Insufficient Points",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                actname.add("KaEfCi Super Besar");
                actcredit.add("- 30000");
                credits -= 30000;
                actdate.add(formattedDate);
                Map<String, Object> data = new HashMap<>();
                data.put("Activity_name", actname);
                data.put("Activity_credit", actcredit);
                data.put("Activity_date", actdate);
                data.put("Credit", credits);
                fStore.collection("Users").document(userID)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(See_voucher_activity.this, "Bought Voucher", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(See_voucher_activity.this, "Database Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (credits < 10000)
                {
                    Toast.makeText(See_voucher_activity.this, "Insufficient Points",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                actname.add("Musicfy Premium");
                actcredit.add("- 10000");
                credits -= 10000;
                actdate.add(formattedDate);
                Map<String, Object> data = new HashMap<>();
                data.put("Activity_name", actname);
                data.put("Activity_credit", actcredit);
                data.put("Activity_date", actdate);
                data.put("Credit", credits);
                fStore.collection("Users").document(userID)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(See_voucher_activity.this, "Bought Voucher", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(See_voucher_activity.this, "Database Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (credits < 15000)
                {
                    Toast.makeText(See_voucher_activity.this, "Insufficient Points",Toast.LENGTH_SHORT).show();
                    return;
                }
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                actname.add("Mobile Letjen");
                actcredit.add("- 15000");
                credits -= 15000;
                actdate.add(formattedDate);
                Map<String, Object> data = new HashMap<>();
                data.put("Activity_name", actname);
                data.put("Activity_credit", actcredit);
                data.put("Activity_date", actdate);
                data.put("Credit", credits);
                fStore.collection("Users").document(userID)
                        .set(data, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(See_voucher_activity.this, "Bought Voucher", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(See_voucher_activity.this, "Database Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });



    }

}
