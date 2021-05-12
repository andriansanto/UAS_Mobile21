package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_activity extends AppCompatActivity {
    EditText Username, Password, Nama, Email_user, Domisili, Umur;
    Button btnSubmit;
    FirebaseAuth fAuth;

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

        if (fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

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
                int umur = Integer.parseInt(String.valueOf(Umur.getText()));

                if(TextUtils.isEmpty(email))
                {
                    Email_user.setError("Email is Required !");
                    return;
                }
                if(TextUtils.isEmpty(password) || password.length() < 8)
                {
                    Password.setError("Password must be at least 8 Characters !");
                    return;
                }
                if(TextUtils.isEmpty(username))
                {
                    Username.setError("Username is Required !");
                    return;
                }
                if (TextUtils.isEmpty(domisili))
                {
                    Domisili.setError("Domisili is Required !");
                }
                if (umur == 0)
                {
                    Umur.setError("Umur is Reqquired !");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register_activity.this, "User Created", Toast.LENGTH_SHORT).show();
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

