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

public class ReportPurchasesAdapter extends RecyclerView.Adapter<ReportPurchasesAdapter.ProductViewHolder>{
    private List<Cash> listCash;
    public ReportPurchasesAdapter(List<Cash> listCash) {
        this.listCash= listCash;
    }
    private DatabaseHelper databaseHelper;
    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_products_list, parent, false);
        databaseHelper = new DatabaseHelper(itemView.getContext(), "rbms.db", itemView.getContext(), 10);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        Cash cash = listCash.get(position);
        holder.tDate.setText(cash.getDate());
        holder.tName.setText(cash.getName());
        holder.tPrice.setText("K"+cash.getCost());
        holder.tAction.setText("X");
        holder.id = cash.getId();
        holder.tAction.setOnClickListener(new View.OnClickListener() {
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
