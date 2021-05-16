package com.example.retailbusinessmanagementsystem.reports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;

public class ReportsActivity extends AppCompatActivity {
    private TextView tCost, tProfit, tExpense, tPurchase;
    private MaterialCardView pProducts, pExpenses, pPurchases, pSales;
    private DatabaseHelper databaseHelper;
    private static DecimalFormat df = new DecimalFormat("0.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        getSupportActionBar().setTitle("Reports");

        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);

        tCost = findViewById(R.id.txtCost);
        tProfit = findViewById(R.id.txtProfit);
        tExpense = findViewById(R.id.txtExpense);
        tPurchase = findViewById(R.id.txtPurchase);
        pProducts = findViewById(R.id.reportProducts);
        pExpenses = findViewById(R.id.reportExpenses);
        pPurchases = findViewById(R.id.reportPurchases);
        pSales = findViewById(R.id.reportSales);

        double costExpense = databaseHelper.expenseCost();
        double costPurchase = databaseHelper.purchaseCost();

        double cost = costExpense + costPurchase;

        double sellPrice = databaseHelper.sellPrice();
        //double orderPrice = databaseHelper.orderPrice();

        double profit = sellPrice - cost;

        tCost.setText("Cost: K"+String.valueOf(df.format(cost)));
        tProfit.setText("Net Profit: K"+String.valueOf(df.format(profit)));
        tExpense.setText("Expenses: K"+String.valueOf(df.format(costExpense)));
        tPurchase.setText("Purchases: K"+String.valueOf(df.format(costPurchase)));

        if (sellPrice < cost){
            tProfit.setTextColor(Color.RED);
        }

        pProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productsIntent = new Intent(ReportsActivity.this, ReportProductsActivity.class);
                startActivity(productsIntent);
            }
        });

        pExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expensesIntent = new Intent(ReportsActivity.this, ReportExpensesActivity.class);
                startActivity(expensesIntent);
            }
        });

        pPurchases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseIntent = new Intent(ReportsActivity.this, ReportPurchaseActivity.class);
                startActivity(purchaseIntent);
            }
        });

        pSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent salesIntent = new Intent(ReportsActivity.this, ReportSalesActivity.class);
                startActivity(salesIntent);
            }
        });
    }
}