package com.example.project_1cie2;

import static com.example.project_1cie2.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText edtUsername, edtPassword;
    Button btnLogin;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        this.setTitle("Login Form");

        databaseHelper = new DatabaseHelper(this);

        edtUsername = findViewById(id.edtusername);
        edtPassword = findViewById(id.edtpassword);
        btnLogin    = findViewById(id.btnlogin);

        }


        public void vvvv(View view) {

        String ValUsername = edtUsername.getText().toString();
        String ValPassword = edtPassword.getText().toString();




        if (!Patterns.EMAIL_ADDRESS.matcher(ValUsername).matches()) {
            edtUsername.setError("Email address format is not valid");
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

        Boolean res = databaseHelper.checkUser(ValUsername, ValPassword);

        if (res == true) {


            Intent a1 = new Intent(MainActivity.this, MainActivity2.class);
            a1.putExtra("username", ValUsername);
            startActivity(a1);
            finish();

            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Username or Password is wrong.", Toast.LENGTH_SHORT).show();
        }
    }


    public void btnSignUp(View view) {
        Intent intent = new Intent(MainActivity.this, signup.class);
        startActivity(intent);


    }
    }