package com.example.retailbusinessmanagementsystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ProductViewHolder>{
    private List<Cash> listCash;
    public PurchaseAdapter(List<Cash> listCash) {
        this.listCash= listCash;
    }
    private DatabaseHelper databaseHelper;
    @NonNull
    @NotNull
    @Override
    public PurchaseAdapter.ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_list_items, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PurchaseAdapter.ProductViewHolder holder, int position) {
        Cash cash = listCash.get(position);
        holder.name.setText(cash.getName());
        holder.cost.setText("K"+cash.getCost());
        holder.remove.setText("X");
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deletePurchase(listCash.get(position).getId());
                listCash.remove(position);
                notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Item deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCash.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView name, cost, remove;
        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            cost = itemView.findViewById(R.id.itemPrice);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
