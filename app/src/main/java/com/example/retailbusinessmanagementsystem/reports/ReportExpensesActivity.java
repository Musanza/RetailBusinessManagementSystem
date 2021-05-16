package com.example.retailbusinessmanagementsystem.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.adapters.ExpenseAdapter;
import com.example.retailbusinessmanagementsystem.adapters.ReportExpenseAdapter;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportExpensesActivity extends AppCompatActivity {

    private RecyclerView expRecyclerview;
    private DatabaseHelper databaseHelper;
    private Cash cash;
    private List<Cash> listCash;
    private ReportExpenseAdapter expenseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_expenses);
        getSupportActionBar().setTitle("Reports > Expenses");
        expRecyclerview = findViewById(R.id.recyclerview_report_expenses);

        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        cash = new Cash();
        initObjects();
    }

    private void initObjects() {
        listCash = new ArrayList<>();
        expenseAdapter = new ReportExpenseAdapter(listCash);
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