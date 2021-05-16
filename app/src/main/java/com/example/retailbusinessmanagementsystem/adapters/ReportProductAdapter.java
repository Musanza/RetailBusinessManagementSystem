package com.example.retailbusinessmanagementsystem.adapters;

import android.graphics.Color;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportProductAdapter extends RecyclerView.Adapter<ReportProductAdapter.ProductViewHolder>{
    private List<Products> listProducts;
    private DatabaseHelper databaseHelper;
    public ReportProductAdapter(List<Products> listProducts) {
        this.listProducts= listProducts;
    }
    @NonNull
    @NotNull
    @Override
    public ReportProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_products_list, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReportProductAdapter.ProductViewHolder holder, int position) {
        Products product = listProducts.get(position);
        holder.tDate.setText(product.getDate());
        holder.tName.setText(product.getName());
        holder.tPrice.setText("K"+String.valueOf(product.getSprice()));
        holder.tAction.setText("X");
        holder.id = product.getId();
        holder.tAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteProduct(product.getId());
                listProducts.remove(position);
                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        String myFormat = "yyyy-MM-dd";
        String expiryDate = holder.tDate.getText().toString();
        String current_date = new SimpleDateFormat(myFormat).format(Calendar.getInstance().getTime());
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(myFormat);
        try {
            Date date1 = simpleDateFormat.parse(current_date);
            Date date2 = simpleDateFormat.parse(expiryDate);
            if (date1.after(date2)) {
                holder.tDate.setTextColor(Color.RED);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
