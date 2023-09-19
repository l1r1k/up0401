package com.example.lab3.models;

import java.util.ArrayList;

public class cart {

    private int id;

    private products product;

    private int count;

    public cart(products product, int count){
        this.product = product;
        this.count = count;
    }


    public products getProduct() {
        return product;
    }

    public void setProduct(products product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
