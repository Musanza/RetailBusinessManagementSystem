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

import com.example.retailbusinessmanagementsystem.HomeActivity;
import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.account.RegisterActivity;
import com.example.retailbusinessmanagementsystem.adapters.ExpenseAdapter;
import com.example.retailbusinessmanagementsystem.adapters.ProductsAdapter;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.reports.ReportsActivity;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.example.retailbusinessmanagementsystem.stock.StockActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CashFlowActivity extends AppCompatActivity {
    private AppCompatButton btnAdd;
    private AppCompatEditText edExpName, edExpCost, edExpDate;
    private RecyclerView expRecyclerview;
    private DatabaseHelper databaseHelper;
    final Calendar mCalendar = Calendar.getInstance();
    private Cash cash;
    private List<Cash> listCash;
    private ExpenseAdapter expenseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_flow);
        getSupportActionBar().setTitle("Expenses");
        btnAdd = findViewById(R.id.btnExpense);
        edExpName = findViewById(R.id.edtExpenseName);
        edExpCost = findViewById(R.id.edtExpenseCost);
        edExpDate = findViewById(R.id.edtExpenseDate);
        expRecyclerview = findViewById(R.id.recyclerview_expenses);

        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        cash = new Cash();
        initObjects();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edExpName.getText().toString().length() < 1
                        || edExpCost.getText().toString().length() < 1
                        || edExpDate.getText().toString().length() < 1) {
                    Toast.makeText(CashFlowActivity.this, "Please fill all inputs.", Toast.LENGTH_SHORT).show();
                } else {
                    cash.setName(edExpName.getText().toString().trim());
                    cash.setCost(edExpCost.getText().toString().trim());
                    cash.setDate(edExpDate.getText().toString().trim());
                    databaseHelper.addExpense(cash);
                    Toast.makeText(CashFlowActivity.this, "Expense added successfully", Toast.LENGTH_SHORT).show();
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

        edExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CashFlowActivity.this, date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void updateLabel(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edExpDate.setText(simpleDateFormat.format(mCalendar.getTime()));
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
            case R.id.action_home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.action_report:
                Intent reportIntent = new Intent(this, ReportsActivity.class);
                startActivity(reportIntent);
                return true;
            case R.id.action_stock:
                Intent stockIntent = new Intent(this, StockActivity.class);
                startActivity(stockIntent);
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

    private void initObjects() {
        listCash = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(listCash);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        expRecyclerview.setLayoutManager(mLayoutManager);
        expRecyclerview.setItemAnimator(new DefaultItemAnimator());
        expRecyclerview.setHasFixedSize(true);
        expRecyclerview.setAdapter(expenseAdapter);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listCash.clear();
                listCash.addAll(databaseHelper.getAllExpenses());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                expenseAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

}