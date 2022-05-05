package com.example.aliexpress_clone;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class basketProductController {
    @FXML
    private Label colorBrand;

    @FXML
    private ImageView imgBrand;

    @FXML
    private Label nameBrand;

    @FXML
    private Label pricaBrand;

    private Product product;

    public void setBrandData(Product product) throws IOException {
        this.product = product;
        nameBrand.setText(product.getName());
        pricaBrand.setText(Aliexpress.CURRENCY + product.getPrice());
//        Image image = new Image(getClass(). getResourceAsStream(product.getImgSrc()));
//        imgBrand.setImage(image);

        URL url = new URL(product.getImgSrc());

        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream("output.jpg")) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }

        File file = new File("output.jpg");
        Image image = new Image(file.toURI().toString());
        imgBrand.setImage(image);
    }

}
