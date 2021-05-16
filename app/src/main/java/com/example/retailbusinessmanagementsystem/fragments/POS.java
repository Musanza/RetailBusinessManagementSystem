package com.example.retailbusinessmanagementsystem.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.adapters.POSAdapter;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.example.retailbusinessmanagementsystem.stock.Capture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class POS extends Fragment {

    View view;
    private RecyclerView recyclerViewItems;
    private ArrayList<Products> mainList;
    private DatabaseHelper databaseHelper;
    private AppCompatButton btnAdd, btnScan;
    private AppCompatEditText mBarcode;
    private TextView total;
    public POS(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_p_o_s, container, false);
        btnAdd = view.findViewById(R.id.btnAddPOS);
        btnScan = view.findViewById(R.id.btnScanPOS);
        mBarcode = view.findViewById(R.id.barCodePOS);
        total = view.findViewById(R.id.txtTotal);
        recyclerViewItems =view.findViewById(R.id.recyclerview_purchased_items);
        databaseHelper = new DatabaseHelper(getContext(), "rbms.db", getActivity(), 10);
        mainList = new ArrayList<>();
        setAdapter();



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            double u_price = 0;
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.getProduct(mBarcode.getText().toString());
                if (cursor.moveToFirst()){
                    do {
                        mBarcode.setText("");
                        Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                        String id = cursor.getString(0);
                        String barcode = cursor.getString(1);
                        String name = cursor.getString(2);
                        String sprice = cursor.getString(3);
                        String date = cursor.getString(4);
                        double converter = Double.parseDouble(String.valueOf(sprice));
                        u_price += converter;
                        total.setText("Total K"+String.valueOf(u_price));
                        mainList.add(new Products(Integer.parseInt(id),1, barcode, name, "K"+sprice, "oprice", date));
                    } while(cursor.moveToNext());
                }else {
                    Toast.makeText(getContext(), "Item not found", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(POS.this).
                        setPrompt("For flash use volume up key").
                        setBeepEnabled(true).
                        setOrientationLocked(false).
                        setCaptureActivity(Capture.class).
                        initiateScan();
            }
        });
        return view;
    }

    private void setAdapter() {
        POSAdapter adapter = new POSAdapter(mainList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewItems.setLayoutManager(layoutManager);
        recyclerViewItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewItems.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null){
            mBarcode.setText(intentResult.getContents());
        } else {
            Toast.makeText(getActivity(), "You did not scan anything. Please try again", Toast.LENGTH_LONG).show();
        }
    }
}