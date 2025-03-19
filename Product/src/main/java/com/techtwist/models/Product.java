package com.techtwist.models;

import java.util.Objects;

public class Product {
    private String name;
    private double price;
    private String partitionKey;
    private String rowKey;

    public Product() {
    }

    public Product(String name, double price, String partitionKey, String rowKey) {
        this.name = name;
        this.price = price;
        this.partitionKey = partitionKey;
        this.rowKey = rowKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(partitionKey, product.partitionKey) &&
                Objects.equals(rowKey, product.rowKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, partitionKey, rowKey);
    }
}
