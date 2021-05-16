package com.example.retailbusinessmanagementsystem.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.adapters.PurchaseAdapter;
import com.example.retailbusinessmanagementsystem.adapters.ReportPurchasesAdapter;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportPurchaseActivity extends AppCompatActivity {
    private RecyclerView purRecyclerview;
    private DatabaseHelper databaseHelper;
    private Cash cash;
    private List<Cash> listCash;
    private ReportPurchasesAdapter purchaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_purchase);
        getSupportActionBar().setTitle("Reports > Purchases");
        purRecyclerview = findViewById(R.id.recyclerview_report_purchases);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        cash = new Cash();
        initObjects();
    }

    private void initObjects() {
        listCash = new ArrayList<>();
        purchaseAdapter = new ReportPurchasesAdapter(listCash);
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
}