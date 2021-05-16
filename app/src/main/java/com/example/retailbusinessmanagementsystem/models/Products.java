package com.example.retailbusinessmanagementsystem.models;

public class Products {

    private int id;
    private int count;
    private String barcode;
    private String name;
    private String sprice;
    private String oprice;
    private String date;

    public Products() { }

    public Products(int id, int count, String barcode, String name, String sprice, String oprice, String date) {
        this.id = id;
        this.count = count;
        this.barcode = barcode;
        this.name = name;
        this.sprice = sprice;
        this.oprice = oprice;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSprice() {
        return sprice;
    }

    public void setSprice(String sprice) {
        this.sprice = sprice;
    }

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
