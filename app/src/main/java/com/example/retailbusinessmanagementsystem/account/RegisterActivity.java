package com.example.retailbusinessmanagementsystem.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.models.Users;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    private AppCompatSpinner mSpinner;
    private AppCompatEditText mName, mUsername, mPassword, mAnswer;
    private AppCompatButton mLogin, mRegister;
    private DatabaseHelper databaseHelper;
    private Users user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mSpinner = findViewById(R.id.spinner);
        mName = findViewById(R.id.name);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.btnLogin);
        mRegister = findViewById(R.id.btnReister);
        mAnswer = findViewById(R.id.answer);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        user = new Users();

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsername.getText().toString().length()<1
                        || mName.getText().toString().length()<1
                        || mPassword.getText().toString().length()<1
                        || mAnswer.getText().toString().length()<1){
                    Toast.makeText(RegisterActivity.this, "Please fill all inputs.", Toast.LENGTH_SHORT).show();
                } else{
                    if (!databaseHelper.checkUser(mUsername.getText().toString().trim())) {
                        user.setName(mName.getText().toString().trim());
                        user.setUsername(mUsername.getText().toString().trim());
                        user.setPassword(mPassword.getText().toString().trim());
                        user.setQuestion(mSpinner.getSelectedItem().toString().trim());
                        user.setAnswer(mAnswer.getText().toString().trim());
                        databaseHelper.addUser(user);
                        Toast.makeText(RegisterActivity.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Snack Bar to show success message that record is wrong
                        Toast.makeText(RegisterActivity.this, "Account not created. Try again", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}