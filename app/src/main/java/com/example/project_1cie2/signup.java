package com.example.project_1cie2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

       private DatabaseHelper DatabaseHelper;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            this.setTitle("Registration Form");

            DatabaseHelper = new DatabaseHelper(this);



        }

        public void saveRecord (View view){

            EditText edtUsername = findViewById(R.id.edtUsername);
            EditText edtPassword = findViewById(R.id.edtPassword);

            String ValUsername, ValPassword;

            ValUsername = edtUsername.getText().toString();
            ValPassword = edtPassword.getText().toString();

            /*------------------- Validation Start ---------------------*/

            if (TextUtils.isEmpty(ValUsername)) {
                edtUsername.setError("Please Enter Email Address");
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(ValUsername).matches()) {
                Toast.makeText(signup.this, "Email address format is not valid", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(ValPassword)) {
                edtPassword.setError("Password is Required.");
                return;
            }

            if (ValPassword.length() < 6) {
                edtPassword.setError("Password Must be >= 6 Characters");
                return;
            }

            /*------------------- Validation End ---------------------*/

            if (DatabaseHelper.insertData(ValUsername, ValPassword)) {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(signup.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }


        }

        public void btnLogin (View view){ onBackPressed();}
    }