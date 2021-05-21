package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Setting_activity extends AppCompatActivity {
    EditText nama,umur,domisili,username;
    Button submit;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        nama = findViewById(R.id.nama);
        umur = findViewById(R.id.umur);
        domisili = findViewById(R.id.domisili);
        username = findViewById(R.id.username);



        submit = findViewById(R.id.btn_save);

        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nama.setHint(value.getString("NamaLengkap"));
                umur.setHint(value.getLong("Umur").toString());
                domisili.setHint(value.getString("Domisili"));
                username.setHint(value.getString("Username"));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,age,dom,user;

                name = nama.getText().toString().trim();
                age = umur.getText().toString().trim();
                dom = domisili.getText().toString().trim();
                user = username.getText().toString().trim();

                if (TextUtils.isEmpty(name))
                {
                    name = nama.getHint().toString().trim();
                }
                if (TextUtils.isEmpty(age))
                {
                    age = umur.getHint().toString().trim();
                }
                if (TextUtils.isEmpty(dom))
                {
                    dom = domisili.getHint().toString().trim();
                }
                if (TextUtils.isEmpty(user))
                {
                    user = username.getHint().toString().trim();
                }

                DocumentReference documentReference = fStore.collection("Users").document(userID);
                Map<String,Object> data = new HashMap<>();
                data.put("NamaLengkap", name);
                data.put("Umur", Integer.parseInt(age));
                data.put("Username", user);
                data.put("Domisili", dom);
                fStore.collection("Users").document(userID)
                        .set(data, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Setting_activity.this, "Profile Updated",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Setting_activity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });
            }
        });


    }
}
