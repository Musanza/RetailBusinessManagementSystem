package com.example.retailbusinessmanagementsystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReportSalesAdapter extends RecyclerView.Adapter<ReportSalesAdapter.ProductViewHolder>{
    private List<Products> listProducts;
    private DatabaseHelper databaseHelper;
    public ReportSalesAdapter(List<Products> listProducts) {
        this.listProducts= listProducts;
    }
    @NonNull
    @NotNull
    @Override
    public ReportSalesAdapter.ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_products_list, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReportSalesAdapter.ProductViewHolder holder, int position) {
        Products product = listProducts.get(position);
        holder.tDate.setText(product.getDate());
        holder.tName.setText(product.getName());
        holder.tPrice.setText("K"+String.valueOf(product.getSprice()));
        holder.tAction.setText("X");
        holder.id = product.getId();
        holder.tAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteSale(product.getId());
                listProducts.remove(position);
                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView tDate, tName, tPrice, tAction;
        private int id;
        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tDate = itemView.findViewById(R.id.tabDate);
            tName = itemView.findViewById(R.id.tabName);
            tPrice = itemView.findViewById(R.id.tabPrice);
            tAction = itemView.findViewById(R.id.tabAction);
        }
    }
}
