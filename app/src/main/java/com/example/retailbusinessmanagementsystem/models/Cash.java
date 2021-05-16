package com.example.retailbusinessmanagementsystem.models;

public class Cash {

    private int id;
    private String name;
    private String cost;
    private String date;
    private int count;
    private String qty;

    public Cash(){}

    public Cash(int id, String name, String cost, String date, int count, String qty) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.count = count;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
