package umn.ac.id.uas_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register_activity extends AppCompatActivity {
    EditText Username, Password, Nama, Email_user, Domisili, Umur;
    Button btnSubmit;

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

//        Register Button On Click broh
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                Lempar ke DB bro
                if(Username.getText().toString().equals("uasmobile") && Password.getText().toString().equals("uasmobilegenap")){
                    openListActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Username / Password",Toast.LENGTH_SHORT).show();
                }
            }// End public on click view
        });//btn submit on click
    }// on create

    public void openListActivity()
    {
        Intent intent = new Intent(this, Login_activity.class);
        startActivity(intent);
    }//openlist
}//end

