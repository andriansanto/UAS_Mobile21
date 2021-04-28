package umn.ac.id.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login_activity extends AppCompatActivity {
    EditText Username, Password;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);
        btLogin = findViewById(R.id.login);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Username.getText().toString().equals("uasmobile") && Password.getText().toString().equals("uasmobilegenap")){
                    openListActivity();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Username / Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void openListActivity()
    {
        Intent intent = new Intent(this, Home_activity.class);
        startActivity(intent);
    }
}