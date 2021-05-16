package com.example.retailbusinessmanagementsystem.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.HomeActivity;
import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText mUsername, mPassword;
    private AppCompatButton mLogin, mRegister;
    private TextView mForgot;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.btnLogin);
        mRegister = findViewById(R.id.btnReister);
        mForgot = findViewById(R.id.txtForgot);
        databaseHelper = new DatabaseHelper(MainActivity.this, "rbms.db", this, 10);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsername.getText().toString().length()<1){
                    Toast.makeText(MainActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (mPassword.getText().toString().length()<1){
                    Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
                } else {
                    if (databaseHelper.checkUser(mUsername.getText().toString().trim(), mPassword.getText().toString().trim())) {
                        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                        loginIntent.putExtra("USER", mUsername.getText().toString().trim());
                        startActivity(loginIntent);
                    } else {
                        // Snack Bar to show success message that record is wrong
                        Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        System.exit(1);
    }
}