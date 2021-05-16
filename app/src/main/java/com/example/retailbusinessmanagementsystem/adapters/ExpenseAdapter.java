package com.example.retailbusinessmanagementsystem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retailbusinessmanagementsystem.R;
import com.example.retailbusinessmanagementsystem.account.MainActivity;
import com.example.retailbusinessmanagementsystem.models.Cash;
import com.example.retailbusinessmanagementsystem.sql.DatabaseHelper;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ProductViewHolder>{
    private List<Cash> listCash;
    public ExpenseAdapter(List<Cash> listCash) {
        this.listCash= listCash;
    }
    private DatabaseHelper databaseHelper;
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_list_items, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Cash cash = listCash.get(position);
        holder.name.setText(cash.getName());
        holder.cost.setText("K"+cash.getCost());
        holder.remove.setText("X");
        holder.id = cash.getId();
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deleteExpense(listCash.get(position).getId());
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
        private int id;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            cost = itemView.findViewById(R.id.itemPrice);
            remove = itemView.findViewById(R.id.remove);
        }
    }
}
