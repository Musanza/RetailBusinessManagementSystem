package com.example.retailbusinessmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.backup.LocalBackup;
import com.example.retailbusinessmanagementsystem.cash.CashFlowActivity;
import com.example.retailbusinessmanagementsystem.cash.PurchaseActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportExpensesActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportProductsActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportPurchaseActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportSalesActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportsActivity;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.example.retailbusinessmanagementsystem.stock.StockActivity;
import com.google.android.material.card.MaterialCardView;

import java.io.File;

public class HomeActivity extends AppCompatActivity {
    private MaterialCardView mStock, mCash, mReports;
    private TextView xpNow, xpThisWeek, xpThisMonth, xpThisYear;
    private TextView purNow, purThisWeek, purThisMonth, purThisYear;
    private DatabaseHelper databaseHelper;
    private static final String TAG = "Google Drive Activity";

    public static final int REQUEST_CODE_SIGN_IN = 0;
    public static final int REQUEST_CODE_OPENING = 1;
    public static final int REQUEST_CODE_CREATION = 2;
    public static final int REQUEST_CODE_PERMISSIONS = 2;
    private boolean isBackup = true;
    private HomeActivity activity;
    private LocalBackup localBackup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Home");

        mStock = findViewById(R.id.stockPage);
        mCash = findViewById(R.id.cashPage);
        mReports = findViewById(R.id.reportPage);

        purNow = findViewById(R.id.purToday);
        purThisWeek = findViewById(R.id.purWeek);
        purThisMonth = findViewById(R.id.purMonth);
        purThisYear = findViewById(R.id.purYear);

        xpNow = findViewById(R.id.xpToday);
        xpThisWeek = findViewById(R.id.xpWeek);
        xpThisMonth = findViewById(R.id.xpMonth);
        xpThisYear = findViewById(R.id.xpYear);

        activity = this;
        localBackup = new LocalBackup(this);

        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        setupUI(databaseHelper);

        purNow.setText(String.valueOf(databaseHelper.sumPurchasedD()));
        purThisWeek.setText(String.valueOf(databaseHelper.sumPurchasedW()));
        purThisMonth.setText(String.valueOf(databaseHelper.sumPurchasedM()));
        purThisYear.setText(String.valueOf(databaseHelper.sumPurchasedY()));

        xpNow.setText(String.valueOf(databaseHelper.countExpiredD().getCount()));
        xpThisWeek.setText(String.valueOf(databaseHelper.countExpiredW().getCount()));
        xpThisMonth.setText(String.valueOf(databaseHelper.countExpiredM().getCount()));
        xpThisYear.setText(String.valueOf(databaseHelper.countExpiredY().getCount()));

        mStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stockIntent = new Intent(HomeActivity.this, StockActivity.class);
                startActivity(stockIntent);
            }
        });

        mCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cashIntent = new Intent(HomeActivity.this, CashFlowActivity.class);
                startActivity(cashIntent);
            }
        });

        mReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reportIntent = new Intent(HomeActivity.this, ReportsActivity.class);
                startActivity(reportIntent);
            }
        });
    }

    private void setupUI(DatabaseHelper databaseHelper) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                String outFileName = Environment.getExternalStorageDirectory() + File.separator + getResources().getString(R.string.app_name) + File.separator;
                localBackup.performBackup(databaseHelper, outFileName);
                return true;
            case R.id.action_download:
                Toast.makeText(this, "Downlaod back up", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_stock:
                Intent stockIntent = new Intent(this, StockActivity.class);
                startActivity(stockIntent);
                return true;
            case R.id.action_expense:
                Intent expenseIntent = new Intent(this, CashFlowActivity.class);
                startActivity(expenseIntent);
                return true;
            case R.id.action_purchase:
                Intent purchaseIntent = new Intent(this, PurchaseActivity.class);
                startActivity(purchaseIntent);
                return true;
            case R.id.action_report_expense:
                Intent reportExpIntent = new Intent(this, ReportExpensesActivity.class);
                startActivity(reportExpIntent);
                return true;
            case R.id.action_report_purchase:
                Intent reportPurIntent = new Intent(this, ReportPurchaseActivity.class);
                startActivity(reportPurIntent);
                return true;
            case R.id.action_report_sale:
                Intent reportSalIntent = new Intent(this, ReportSalesActivity.class);
                startActivity(reportSalIntent);
                return true;
            case R.id.action_report_product:
                Intent reportProntent = new Intent(this, ReportProductsActivity.class);
                startActivity(reportProntent);
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