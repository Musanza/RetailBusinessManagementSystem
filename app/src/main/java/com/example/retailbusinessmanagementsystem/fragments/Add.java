package com.example.retailbusinessmanagementsystem.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.retailbusinessmanagementsystem.account.RegisterActivity;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;
import com.example.retailbusinessmanagementsystem.stock.Capture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Add extends Fragment {

    final Calendar mCalendar = Calendar.getInstance();
    public Add(){}
    private AppCompatButton mScan, mAdd;
    private AppCompatEditText mBarCode, xpDate, mName, mOrder, mSale;
    private DatabaseHelper databaseHelper;
    Products product;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add, container, false);

        mScan = view.findViewById(R.id.btnScan);
        mBarCode = view.findViewById(R.id.barCode);
        xpDate = view.findViewById(R.id.xDate);
        mAdd = view.findViewById(R.id.btnAdd);
        mName = view.findViewById(R.id.pName);
        mSale = view.findViewById(R.id.sPrice);
        databaseHelper = new DatabaseHelper(getContext(), "rbms.db", getContext(), 10);
        product = new Products();

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(Add.this).
                setPrompt("For flash use volume up key").
                setBeepEnabled(true).
                setOrientationLocked(false).
                setCaptureActivity(Capture.class).
                initiateScan();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBarCode.getText().toString().length()<1
                        || mName.getText().toString().length()<1
                        || mSale.getText().toString().length()<1
                        || xpDate.getText().toString().length()<1){
                    Toast.makeText(getActivity(), "Please fill all inputs.", Toast.LENGTH_SHORT).show();
                } else{
                    if (!databaseHelper.checkProduct(mBarCode.getText().toString().trim())) {
                        product.setBarcode(mBarCode.getText().toString().trim());
                        product.setName(mName.getText().toString().trim());
                        product.setSprice(mSale.getText().toString().trim());
                        product.setDate(xpDate.getText().toString().trim());
                        databaseHelper.addProduct(product);
                        Toast.makeText(getActivity(), "Product added Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Product not added. Try again", Toast.LENGTH_LONG).show();
                    }
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

        xpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null){
            mBarCode.setText(intentResult.getContents());
        } else {
            Toast.makeText(getActivity(), "You did not scan anything. Please try again", Toast.LENGTH_LONG).show();
        }
    }

    public void updateLabel(){
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        xpDate.setText(simpleDateFormat.format(mCalendar.getTime()));
    }
}