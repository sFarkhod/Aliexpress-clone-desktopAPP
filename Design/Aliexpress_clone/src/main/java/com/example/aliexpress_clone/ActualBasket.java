package com.example.aliexpress_clone;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Product;

public class ActualBasket {
    @FXML
    private ImageView imgBasket;

    @FXML
    private Label priceBasket;

    @FXML
    private Label quantityBasket;

    @FXML
    private Label nameBasket;

    private Product product;

    public void setBasketData(Product product) {
        this.product = product;
        nameBasket.setText(product.getName());
        priceBasket.setText(Aliexpress.CURRENCY + product.getPrice());
        Image image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        imgBasket.setImage(image);
    }
}
