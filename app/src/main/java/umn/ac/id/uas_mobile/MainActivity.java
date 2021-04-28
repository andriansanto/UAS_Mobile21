package umn.ac.id.uas_mobile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();

            }
        });
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
    }
    public void openProfileActivity() {
        Intent intent = new Intent(this, credit_activity.class);
        startActivity(intent);
    }
    public void openLoginActivity(){
        Intent intent = new Intent(this, Login_activity.class);
        startActivity(intent);
    }
}