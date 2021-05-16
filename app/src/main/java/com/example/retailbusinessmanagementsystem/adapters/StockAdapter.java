package com.example.retailbusinessmanagementsystem.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.retailbusinessmanagementsystem.fragments.Add;
import com.example.retailbusinessmanagementsystem.fragments.POS;
import com.example.retailbusinessmanagementsystem.fragments.ViewProducts;

public class StockAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public StockAdapter (Context c, FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);
        context = c;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                POS posFragment = new POS();
                return posFragment;
            case 1:
                ViewProducts viewProductsFragment = new ViewProducts();
                return viewProductsFragment;
            case 2:
                Add addFragment = new Add();
                return addFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
