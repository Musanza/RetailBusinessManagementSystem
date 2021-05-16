package com.example.retailbusinessmanagementsystem.adapters;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GetProdouctsAdapter extends RecyclerView.Adapter<GetProdouctsAdapter.ProductViewHolder>{
    private ArrayList<Products> mainList;
    private Products product;
    private DatabaseHelper databaseHelper;
    final Calendar mCalendar = Calendar.getInstance();
    public GetProdouctsAdapter(ArrayList<Products> mainList){
        this.mainList = mainList;
    }
    @NonNull
    @NotNull
    @Override
    public GetProdouctsAdapter.ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_product, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GetProdouctsAdapter.ProductViewHolder holder, int position) {
        Products products = mainList.get(position);
        holder.id.setText(String.valueOf(products.getId()));
        holder.name.setText(products.getName());
        holder.barcode.setText(products.getBarcode());
        holder.price.setText(products.getSprice());
        holder.date.setText(products.getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteProduct(products.getId());
                mainList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Products();
                product.setBarcode(holder.barcode.getText().toString().trim());
                product.setName(holder.name.getText().toString().trim());
                product.setSprice(holder.price.getText().toString().trim());
                product.setDate(holder.date.getText().toString().trim());
                databaseHelper.updateProducts(product, products.getId());
                Toast.makeText(v.getContext(), "Product updated successfully", Toast.LENGTH_SHORT).show();
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

            private void updateLabel() {
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
                holder.date.setText(simpleDateFormat.format(mCalendar.getTime()));
            }
        };

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private AppCompatEditText name, barcode, price, date;
        private AppCompatButton update, delete;
        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.productId);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            barcode = itemView.findViewById(R.id.productBarcode);
            date = itemView.findViewById(R.id.productDate);
            update = itemView.findViewById(R.id.btn_update);
            delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
