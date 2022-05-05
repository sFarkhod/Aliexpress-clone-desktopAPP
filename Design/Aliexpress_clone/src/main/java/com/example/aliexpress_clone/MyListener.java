package com.example.aliexpress_clone;

import model.Product;

import java.io.IOException;

public interface MyListener {
    public void onClickListener(Product product) throws IOException;
}
