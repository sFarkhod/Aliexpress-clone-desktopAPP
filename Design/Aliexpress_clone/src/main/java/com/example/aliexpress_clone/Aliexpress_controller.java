package com.example.aliexpress_clone;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Aliexpress_controller implements Initializable {

    @FXML
    private VBox choosenProductCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView productImageView;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private ScrollPane scroll;

    private List<Product> products = new ArrayList<>();

    // for changing image
    private Image image;
    private MyListener myListener;

    private List<Product> getData() {
        List<Product> products = new ArrayList<>();
        Product product;


        product = new Product();
        product.setName("JBL Boombox 2");
        product.setPrice(7.52);
        product.setImgSrc("JBL-Flip-5-Bluetooth.jpg_640x640-removebg-preview.png");
        product.setColor("1c394d");
        products.add(product);



        product = new Product();
        product.setName("Bluetooth headphones");
        product.setPrice(3.23);
        product.setImgSrc("Bluetooth-5-0.jpg_640x640-removebg-preview.png");
        product.setColor("090d0f");
        products.add(product);



        product = new Product();
        product.setName("Watch");
        product.setPrice(2.99);
        product.setImgSrc("CURREN-removebg-preview.png");
        product.setColor("0f3a4f");
        products.add(product);


        product = new Product();
        product.setName("Robo cleaner");
        product.setPrice(16.93);
        product.setImgSrc("RUVIP999-ABIR-X8.jpg_640x640-removebg-preview.png");
        product.setColor("090d0f");
        products.add(product);


        product = new Product();
        product.setName("Poco M4");
        product.setPrice(16.93);
        product.setImgSrc("POCO-M4-PRO-5G-4-64.jpg_640x640-removebg-preview.png");
        product.setColor("797d2c");
        products.add(product);

        product = new Product();
        product.setName("Vans");
        product.setPrice(16.93);
        product.setImgSrc("Vans-VA3WM7VNF-removebg-preview.png");
        product.setColor("7d2c3a");
        products.add(product);

        product = new Product();
        product.setName("Pc");
        product.setPrice(16.93);
        product.setImgSrc("X99-DDR4-2DDR4-DIMM-Xeon-E5-2620-V3-LGA2011-removebg-preview.png");
        product.setColor("161a1c");
        products.add(product);

        product = new Product();
        product.setName("Lock");
        product.setPrice(16.93);
        product.setImgSrc("Xiaomi-Mijia.jpg_640x640-removebg-preview.png");
        product.setColor("161a1c");
        products.add(product);

        product = new Product();
        product.setName("Smart Watch");
        product.setPrice(16.93);
        product.setImgSrc("Xiaomi-Redmi-Watch-2-lite-Bluetooth-Mi-Band-1-55.jpg_640x640-removebg-preview.png");
        product.setColor("636f75");
        products.add(product);

        product = new Product();
        product.setName("Blender");
        product.setPrice(16.93);
        product.setImgSrc("Homgeek-2000-8-33000-removebg-preview.png");
        product.setColor("42594c");
        products.add(product);


        return products;
    }

    private void setChosenProduct(Product product) {
        productName.setText(product.getName());
        productPrice.setText(Aliexpress.CURRENCY + product.getPrice());
        image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
        productImageView.setImage(image);
        choosenProductCard.setStyle("    -fx-background-color: #"+ product.getColor() +";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        products.addAll(getData());
        if (products.size() > 0) {
            setChosenProduct(products.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    setChosenProduct(product);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
        for (int i=0; i<products.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("product.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            ProductController productController = fxmlLoader.getController();
            productController.setData(products.get(i),myListener);

            if (column == 3) {
                column = 0;
                row++;
            }

            grid.add(anchorPane,column++,row);
            // for width grid
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
            // for height grid
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));

        }
        } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }