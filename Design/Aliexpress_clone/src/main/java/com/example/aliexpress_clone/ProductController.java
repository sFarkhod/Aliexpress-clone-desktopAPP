package com.example.aliexpress_clone;

import com.example.aliexpress_clone.Aliexpress;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class ProductController {

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;

    @FXML
    public void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(product);
    }

    private Product product;

    private MyListener myListener;

    public void setData(Product product, MyListener myListener) throws IOException {
        this.product = product;
        this.myListener = myListener;
        nameLabel.setText(product.getName());
        priceLabel.setText(Aliexpress.CURRENCY + product.getPrice());
//        Image image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
//        img.setImage(image);


//        For image if it is not working we can delete it :)
        URL url = new URL(product.getImgSrc());

        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream("output.jpg")) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }

        File file = new File("output.jpg");
        Image image = new Image(file.toURI().toString());
        img.setImage(image);
    }

}
