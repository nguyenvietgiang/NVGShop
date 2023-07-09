package com.example.nvgshop.models;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String type;

    public Product(int id, String name, String description, double price, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


