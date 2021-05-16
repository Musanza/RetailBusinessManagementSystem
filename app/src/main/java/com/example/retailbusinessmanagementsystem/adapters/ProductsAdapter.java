package com.example.retailbusinessmanagementsystem.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.models.Products;
import com.example.retailbusinessmanagementsystem.stock.ProductsActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private List<Products> listProducts;
    private Context context;

    public ProductsAdapter(List<Products> listProducts, Context context) {
        this.listProducts= listProducts;
        this.context = context;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products product = listProducts.get(position);
        holder.name.setText(product.getName());
        holder.total.setText(">");

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductsActivity.class);
                intent.putExtra("product_name", product.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView name, total;
        private MaterialCardView parent_layout;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            total = itemView.findViewById(R.id.available);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
