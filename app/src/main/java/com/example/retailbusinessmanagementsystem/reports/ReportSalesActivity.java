package com.example.retailbusinessmanagementsystem.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.adapters.ReportProductAdapter;
import com.example.retailbusinessmanagementsystem.adapters.ReportSalesAdapter;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportSalesActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private RecyclerView productRecyclerview;
    private Products products;
    private List<Products> listProducts;
    private ReportSalesAdapter reportProductAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales);
        getSupportActionBar().setTitle("Reports > Sales");
        productRecyclerview = findViewById(R.id.recyclerview_report_sales);

        products = new Products();
        initObjects();
    }

    private void initObjects() {
        listProducts = new ArrayList<>();
        reportProductAdapter = new ReportSalesAdapter(listProducts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        productRecyclerview.setLayoutManager(mLayoutManager);
        productRecyclerview.setItemAnimator(new DefaultItemAnimator());
        productRecyclerview.setHasFixedSize(true);
        productRecyclerview.setAdapter(reportProductAdapter);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listProducts.clear();
                listProducts.addAll(databaseHelper.getAllSales());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                reportProductAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}