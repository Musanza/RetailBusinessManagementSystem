package com.example.retailbusinessmanagementsystem.cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.account.RegisterActivity;
import com.example.retailbusinessmanagementsystem.adapters.ExpenseAdapter;
import com.example.retailbusinessmanagementsystem.adapters.PurchaseAdapter;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PurchaseActivity extends AppCompatActivity {
    private AppCompatButton btnAdd;
    private AppCompatEditText edPurName, edPurCost, edPurDate, edPurQty;
    private RecyclerView purRecyclerview;
    private DatabaseHelper databaseHelper;
    final Calendar mCalendar = Calendar.getInstance();
    private Cash cash;
    private List<Cash> listCash;
    private PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        getSupportActionBar().setTitle("Purchases");

        edPurName = findViewById(R.id.edtPurchaseName);
        edPurCost = findViewById(R.id.edtPurchaseCost);
        edPurDate = findViewById(R.id.edtPurchaseDate);
        edPurQty = findViewById(R.id.edtPurchaseQty);
        btnAdd = findViewById(R.id.btnPurchase);
        purRecyclerview = findViewById(R.id.recyclerview_purchases);

        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        cash = new Cash();
        initObjects();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPurName.getText().toString().length() < 1
                        || edPurCost.getText().toString().length() < 1
                        || edPurQty.getText().toString().length() < 1
                        || edPurDate.getText().toString().length() < 1) {
                    Toast.makeText(PurchaseActivity.this, "Please fill all inputs.", Toast.LENGTH_SHORT).show();
                } else {
                    cash.setName(edPurName.getText().toString().trim());
                    cash.setCost(edPurCost.getText().toString().trim());
                    cash.setQty(edPurQty.getText().toString().trim());
                    cash.setDate(edPurDate.getText().toString().trim());
                    databaseHelper.addPuchase(cash);
                    Toast.makeText(PurchaseActivity.this, "Purchase added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        edPurDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PurchaseActivity.this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void updateLabel(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edPurDate.setText(simpleDateFormat.format(mCalendar.getTime()));
    }

    private void initObjects() {
        listCash = new ArrayList<>();
        purchaseAdapter = new PurchaseAdapter(listCash);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        purRecyclerview.setLayoutManager(mLayoutManager);
        purRecyclerview.setItemAnimator(new DefaultItemAnimator());
        purRecyclerview.setHasFixedSize(true);
        purRecyclerview.setAdapter(purchaseAdapter);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listCash.clear();
                listCash.addAll(databaseHelper.getAllPurchases());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                purchaseAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_expense:
                Toast.makeText(this, "Add expense", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_purchase:
                Intent purchaseIntent = new Intent(this, PurchaseActivity.class);
                startActivity(purchaseIntent);
                return true;
            case R.id.action_logout:
                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}