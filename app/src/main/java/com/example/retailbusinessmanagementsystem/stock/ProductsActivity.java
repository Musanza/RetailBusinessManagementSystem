package com.example.retailbusinessmanagementsystem.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.HomeActivity;
import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.adapters.GetProdouctsAdapter;
import com.example.retailbusinessmanagementsystem.adapters.POSAdapter;
import com.example.retailbusinessmanagementsystem.cash.CashFlowActivity;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.reports.ReportsActivity;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewItems;
    private ArrayList<Products> mainList;
    private DatabaseHelper databaseHelper;
    private TextView ProductName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        getSupportActionBar().setTitle(getIntent().getStringExtra("product_name"));
        ProductName = findViewById(R.id.product_name);
        ProductName.setText(getIntent().getStringExtra("product_name"));

        recyclerViewItems =findViewById(R.id.update_product_recyclerview);
        databaseHelper = new DatabaseHelper(this, "rbms.db", this, 10);
        mainList = new ArrayList<>();

        GetProdouctsAdapter adapter = new GetProdouctsAdapter(mainList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewItems.setLayoutManager(layoutManager);
        recyclerViewItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewItems.setAdapter(adapter);

        Cursor cursor = databaseHelper.getProducts(ProductName.getText().toString().trim());
        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String barcode = cursor.getString(1);
                String name = cursor.getString(2);
                String sprice = cursor.getString(3);
                String date = cursor.getString(4);
                mainList.add(new Products(Integer.parseInt(id),1, barcode, name, sprice, "oprice", date));
            } while(cursor.moveToNext());
        }else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_LONG).show();
        }
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
                Intent refreshIntent = new Intent(this, ProductsActivity.class);
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