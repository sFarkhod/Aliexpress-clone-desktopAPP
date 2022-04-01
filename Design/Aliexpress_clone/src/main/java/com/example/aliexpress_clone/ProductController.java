package com.example.aliexpress_clone;

import com.example.aliexpress_clone.Aliexpress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;


public class ProductController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }

    private Product product;

    private MyListener myListener;

    public void setData(Product product, MyListener myListener) {
        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(Aliexpress.CURRENCY + product.getPrice());
        Image image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        img.setImage(image);
    }

}
