package umn.ac.id.uas_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class credit_activity extends AppCompatActivity {
    private Button backtohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);
        backtohome = findViewById(R.id.btn_backhome);

       /* backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_home();
            }
        });
    }

    protected void back_to_home() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

        */
    }
}