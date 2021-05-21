package umn.ac.id.uas_mobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PickupFormActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView tvDateResult;
    private Button btDatePicker, btnSubmit, btnPlastic, btnCan, btnPaper, btnTextile;
    private TextView Username;
    private EditText berat;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID, choice, date;
    ArrayList<String> actname, actdate, actcredit;
    int credits, kg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup_form);

        dateFormatter = new SimpleDateFormat("EEEE, dd-MMM-yyyy", Locale.US);
        Username = findViewById(R.id.recash2);
        btnSubmit = findViewById(R.id.btn_submitPickup);
        btnPlastic = findViewById(R.id.btn_plastic);
        btnCan = findViewById(R.id.btn_can);
        btnPaper = findViewById(R.id.btn_paper);
        btnTextile = findViewById(R.id.btn_textile);
        berat = findViewById(R.id.berat);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //Firesbase fetch data and display//
        DocumentReference documentReference = fStore.collection("Users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                Username.setText("Hello, "+value.getString("Username"));
                actname = (ArrayList<String>) value.get("Activity_name");
                actcredit = (ArrayList<String>) value.get("Activity_credit");
                actdate = (ArrayList<String>) value.get("Activity_date");
                credits = value.getLong("Credit").intValue();
            }
        });

        tvDateResult = (TextView) findViewById(R.id.tv_dateresult);
        btDatePicker = (Button) findViewById(R.id.bt_datepicker);
        btDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(date))
                {
                    btDatePicker.setError("PLease pick a date");
                    return;
                }
                if (TextUtils.isEmpty(choice))
                {
                    btnSubmit.setError("Please Choose type");
                    return;
                }
                if (TextUtils.isEmpty(berat.getText().toString()))
                {
                    btnSubmit.setError("Please insert weight");
                    return;
                }

                kg = Integer.parseInt(berat.getText().toString().trim());

                actdate.add(date);
                actname.add("Submit "+choice);
                actcredit.add("+ "+ kg*1000);
                credits += kg*1000;

                Intent intent = new Intent(getApplicationContext(), Request_submit.class);
                intent.putExtra("actcredit", actcredit);
                intent.putExtra("actname",actname);
                intent.putExtra("actdate",actdate);
                intent.putExtra("credit", credits);
                intent.putExtra("choice",choice);
                intent.putExtra("berat",kg);
                startActivity(intent);
            }
        });

        btnPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPaper.setBackgroundResource(R.color.birujanda);
                btnCan.setBackgroundResource(R.color.birumuda);
                btnPlastic.setBackgroundResource(R.color.birumuda);
                btnTextile.setBackgroundResource(R.color.birumuda);
                choice = btnPaper.getText().toString().trim();
            }
        });

        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPaper.setBackgroundResource(R.color.birumuda);
                btnCan.setBackgroundResource(R.color.birujanda);
                btnPlastic.setBackgroundResource(R.color.birumuda);
                btnTextile.setBackgroundResource(R.color.birumuda);
                choice = btnCan.getText().toString().trim();
            }
        });

        btnTextile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPaper.setBackgroundResource(R.color.birumuda);
                btnCan.setBackgroundResource(R.color.birumuda);
                btnPlastic.setBackgroundResource(R.color.birumuda);
                btnTextile.setBackgroundResource(R.color.birujanda);
                choice = btnTextile.getText().toString().trim();
            }
        });

        btnPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPaper.setBackgroundResource(R.color.birumuda);
                btnCan.setBackgroundResource(R.color.birumuda);
                btnPlastic.setBackgroundResource(R.color.birujanda);
                btnTextile.setBackgroundResource(R.color.birumuda);
                choice = btnPlastic.getText().toString().trim();
            }
        });

    }//onCreate

    private void showDateDialog(){

//        ambil tanggal sekarang
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tvDateResult.setText("Jadwal Pickup : "+dateFormatter.format(newDate.getTime()));
                date = dateFormatter.format(newDate.getTime());
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }//Showdatedialog



} /*End*/
