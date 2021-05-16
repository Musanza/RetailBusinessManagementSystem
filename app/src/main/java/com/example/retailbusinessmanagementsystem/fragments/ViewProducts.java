package com.example.retailbusinessmanagementsystem.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.adapters.ProductsAdapter;
import com.example.retailbusinessmanagementsystem.cash.PurchaseActivity;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.example.retailbusinessmanagementsystem.models.Products;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewProducts extends Fragment {
    private RecyclerView recyclerViewProducts;
    private List<Products> listProducts;
    private ProductsAdapter productsAdapter;
    private DatabaseHelper databaseHelper;
    View view;
    public ViewProducts(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_products, container, false);
        recyclerViewProducts = this.view.findViewById(R.id.recyclerViewProducts);
        initObjects();
        return this.view;
    }

    private void initObjects() {
        listProducts = new ArrayList<>();
        productsAdapter = new ProductsAdapter(listProducts, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProducts.setLayoutManager(mLayoutManager);
        recyclerViewProducts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProducts.setHasFixedSize(true);
        recyclerViewProducts.setAdapter(productsAdapter);
        databaseHelper = new DatabaseHelper(getContext(), "rbms.db", getActivity(), 10);
        getDataFromSQLite();
    }

    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listProducts.clear();
                listProducts.addAll(databaseHelper.getAllProducts());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                productsAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}