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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register_activity extends AppCompatActivity {
    EditText Username, Password, Nama, Email_user, Domisili, Umur;
    Button btnSubmit;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        btnSubmit = findViewById(R.id.submit_register);
        Nama = findViewById(R.id.nama);
        Email_user = findViewById(R.id.email_user);
        Domisili = findViewById(R.id.domisili);
        Umur = findViewById(R.id.umur);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        Register Button On Click broh
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Lempar ke DB bro
                String email = Email_user.getText().toString().trim();
                String password = Password.getText().toString().trim();
                String username = Username.getText().toString().trim();
                String domisili = Domisili.getText().toString().trim();
                String name = Nama.getText().toString().trim();
                ArrayList<String> activity_name = new ArrayList<String>();
                ArrayList<String> activity_credit = new ArrayList<String>();
                ArrayList<String> activity_date = new ArrayList<String>();
                Date c = Calendar.getInstance().getTime();

                SimpleDateFormat df = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                activity_name.add("New User Promo");
                activity_credit.add("+ 10000");
                activity_date.add(formattedDate);

                int umur = Integer.parseInt(String.valueOf(Umur.getText()));

                if (TextUtils.isEmpty(name))
                {
                    Nama.setError("Nama Lengkap is Required !");
                    return;
                }
                if(TextUtils.isEmpty(email))
                {
                    Email_user.setError("Email is Required !");
                    return;
                }
                if (umur <= 0)
                {
                    Umur.setError("Umur is Reqquired !");
                    return;
                }
                if (TextUtils.isEmpty(domisili))
                {
                    Domisili.setError("Domisili is Required !");
                    return;
                }
                if(TextUtils.isEmpty(username))
                {
                    Username.setError("Username is Required !");
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 8)
                {
                    Password.setError("Password must be at least 8 Characters !");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register_activity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("NamaLengkap", name);
                            user.put("Umur", umur);
                            user.put("Username", username);
                            user.put("Credit", 10000);
                            user.put("Activity_name", activity_name);
                            user.put("Activity_credit", activity_credit);
                            user.put("Activity_date", activity_date);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "user profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","Onfailure : "+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),Login_activity.class));
                        }
                        else
                        {
                            Toast.makeText(Register_activity.this,"Error !"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }// End public on click view
        });//btn submit on click
    }// on create

    public void openListActivity()
    {
        Intent intent = new Intent(this, Login_activity.class);
        startActivity(intent);
    }//openlist
}//end

