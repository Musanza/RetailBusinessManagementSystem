package com.example.retailbusinessmanagementsystem.stock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.HomeActivity;
import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.adapters.StockAdapter;
import com.example.retailbusinessmanagementsystem.cash.CashFlowActivity;
import com.example.retailbusinessmanagementsystem.cash.PurchaseActivity;
import com.example.retailbusinessmanagementsystem.reports.ReportsActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StockActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    //Button mScan;
    //EditText mBarCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        getSupportActionBar().setTitle("Stock");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        //mScan = findViewById(R.id.btnScan);
        //mBarCode = findViewById(R.id.barCode);
        tabLayout.addTab(tabLayout.newTab().setText("POS"));
        tabLayout.addTab(tabLayout.newTab().setText("All Products"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Product"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final StockAdapter adapter = new StockAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_stock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Intent refreshIntent = new Intent(this, StockActivity.class);
                startActivity(refreshIntent);
                return true;
            case R.id.action_home:
                Intent homeIntent = new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.action_cash:
                Intent cashIntent = new Intent(this, CashFlowActivity.class);
                startActivity(cashIntent);
                return true;
            case R.id.action_report:
                Intent reportIntent = new Intent(this, ReportsActivity.class);
                startActivity(reportIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}