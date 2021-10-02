package com.example.project_1cie2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Entry extends AppCompatActivity {

    EditText date, volume, price, odometer;
    Button submit,report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        initView();

        report = findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Report.class);
                startActivity(intent);

            }
        });

        date.setOnClickListener(v -> {
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            };
            new DatePickerDialog(Entry.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        submit.setOnClickListener(v -> {
            boolean val = validateFields();
            if (val) {
                String dateStr = date.getText().toString();
                float volumeF = Float.parseFloat(volume.getText().toString());
                float priceF = Float.parseFloat(price.getText().toString());
                float odometerF = Float.parseFloat(odometer.getText().toString());
                Fuel f = new Fuel(0, dateStr, volumeF, priceF, odometerF);
                if (getDatabaseObject(this).addFuel(f)) {
                    Toast.makeText(this, "Success.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        String dateStr = date.getText().toString();
        String volumeStr = volume.getText().toString();
        String priceStr = price.getText().toString();
        String odometerStr = odometer.getText().toString();
        boolean dateAllow = true;
        Fuel lastFuel = null;
        try {
            lastFuel = getDatabaseObject(this).getLastFuelRecord();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date selected = sdf.parse(dateStr);
            Date lastDate = sdf.parse(lastFuel.getDate());
            if (lastDate.after(selected)) {
                dateAllow = false;
            }
        } catch (Exception e) {
            Log.e("Date format >>", e.getMessage());
        }
        if (dateStr.isEmpty() || volumeStr.isEmpty() || priceStr.isEmpty() || odometerStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (lastFuel != null) {
            if (!dateAllow) {
                Toast.makeText(this, "You can't enter previous date.", Toast.LENGTH_SHORT).show();
                return false;
            } else if (lastFuel.getOdoMeter() > Float.parseFloat(odometerStr)) {
                Toast.makeText(this, "You can't enter previous odometer.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (volumeStr.equals("0")) {
            Toast.makeText(this, "Volume must be greater then 0.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (priceStr.equals("0")) {
            Toast.makeText(this, "Price must be greater then 0.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void initView() {
        date = findViewById(R.id.date);
        volume = findViewById(R.id.volume);
        price = findViewById(R.id.price);
        odometer = findViewById(R.id.odometer);
        submit = findViewById(R.id.submit);
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }

    public static DatabaseHelper getDatabaseObject(Context context){
        return new DatabaseHelper(context);

    }
}