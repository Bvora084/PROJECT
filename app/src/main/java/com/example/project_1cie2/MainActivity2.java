package com.example.project_1cie2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void click(View view) {

        EditText et1 = (EditText) findViewById(R.id.etfname);
        EditText et2 = (EditText) findViewById(R.id.etlname);
        EditText et3 = (EditText) findViewById(R.id.etMobno);
        RadioButton rb1 = (RadioButton) findViewById(R.id.rbmale);
        RadioButton rb2 = (RadioButton) findViewById(R.id.rbfemale);
        Switch Shift = (Switch) findViewById(R.id.swShift);
        Spinner sp1 = (Spinner) findViewById(R.id.spcity);


        String s1 = et1.getText().toString();
        String s2 = et2.getText().toString();
        String s3 = et3.getText().toString();

        String s4= " ";
        if (rb1.isChecked()){
            s4 = "Male";
        }
        else{
            s4 = "Female";
        }
        String s5 = " ";
        if (Shift.isChecked()){
            s5 = "Day";
        }
        else{
            s5 = "Night";
        }
        String s6 = sp1.getSelectedItem().toString();

        Intent a1 = new Intent(this , Entry.class);
        a1.putExtra("fname",s1);
        a1.putExtra("lname",s2);
        a1.putExtra("Mobno",s3);
        a1.putExtra("gender",s4);
        a1.putExtra("Shift",s5);
        a1.putExtra("age",s6);
        startActivity(a1);
    }
}

