package com.example.retailbusinessmanagementsystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.fragments.POS;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.ArrayList;

public class POSAdapter extends RecyclerView.Adapter<POSAdapter.ProductViewHolder>{
    private ArrayList<Products> mainList;
    private Products product;
    private DatabaseHelper databaseHelper;
    public POSAdapter(ArrayList<Products> mainList){
        this.mainList = mainList;
    }

    @NonNull
    @Override
    public POSAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pos_item_list, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull POSAdapter.ProductViewHolder holder, int position) {
        Products products = mainList.get(position);
        holder.barcode.setText(products.getBarcode());
        holder.item.setText(products.getName());
        holder.price.setText(products.getSprice());
        holder.date.setText(products.getDate());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainList.remove(position);
                notifyItemRemoved(position);
            }
        });
        holder.insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Products();
                product.setBarcode(holder.barcode.getText().toString().trim());
                product.setName(holder.item.getText().toString().trim());
                product.setSprice(holder.price.getText().toString().trim());
                product.setDate(holder.date.getText().toString().trim());
                databaseHelper.addSale(product);
                mainList.remove(position);
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView item, price, date, barcode;
        private AppCompatButton remove, insert;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            date = itemView.findViewById(R.id.itemDate);
            barcode = itemView.findViewById(R.id.itemBarcode);
            remove = itemView.findViewById(R.id.remove);
            insert = itemView.findViewById(R.id.btnInsert);
        }
    }
}
