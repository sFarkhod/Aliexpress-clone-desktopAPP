package com.example.aliexpress_clone;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.LinkedList;
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

//    for search part
    @FXML
    private TextField searchTf;
//    for search part


//    Global List of products
    private List<Product> products = new ArrayList<>();
//    Global List of products


    // for changing image
    private Image image;
    private MyListener myListener;
    // for changing image



    private List<Product> getData() throws IOException {
        List<Product> products = new LinkedList<>();

        OkHttpClient client = new OkHttpClient();

        String requestURL = "https://magic-aliexpress1.p.rapidapi.com/api/bestSales/products?page=1&priceMax=20&priceMin=5&sort=EVALUATE_RATE_ASC&searchName=shoes";
        Request request = new Request.Builder()
                .url(requestURL)
                .get()
                .addHeader("X-RapidAPI-Host", "magic-aliexpress1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "d4cff52ce8msh3828a37b56e73dep161941jsn8f48abfe9b56")
                .build();

        Response response = client.newCall(request).execute();
        //here you can check status code

        //jsonData - the whole data as a string
        String jsonData = response.body().string();

        //jsonObj - the whole data as a JSONObject
        JSONObject jsonObj = new JSONObject(jsonData);

        //ja_data - "docs" part, e.g. here are all the products as a JSONArray
        JSONArray ja_data = jsonObj.getJSONArray("docs");


        for(int i = 0; i < ja_data.length(); i++)
        {
            //so for each product I need to retrieve information
            //object is a product as a JSONObject
            JSONObject object = ja_data.getJSONObject(i);
            Object price = object.get("app_sale_price");
            //System.out.println(price);

            Object title = object.get("product_title");


            //"product_main_image_url" not working, so we'll take a URL from metadata->image->imgURL
//            JSONObject metadata = object.getJSONObject("product_main_image_url");
//
//            JSONObject image = metadata.getJSONObject("image");
            Object imageURL = object.get("product_main_image_url");

            //Object imageURL = object.getJSONObject("metadata").getJSONObject("image").get("imgUrl");


            Product product = new Product();
            product.setName(title.toString());
            product.setImgSrc(imageURL.toString());

            //casting Object to Double
            if (price instanceof Double)
                product.setPrice((Double) price);
            else if (price instanceof Integer)
                product.setPrice((Integer) price);
            else
                product.setPrice(-1.0);


            products.add(product);
        }
        return products;
    }


//    for search
    public void searchProduct() throws IOException {
        List<Product> searchedProducts = requestProducts(searchTf.getText());
        try {
            searchedProducts.addAll(getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (searchedProducts.size() > 0) {
            setChosenProduct(products.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) throws IOException {
                    setChosenProduct(product);
                }
            };
        }

        int column = 0;
        int row = 1;

        try {
            for (int i=0; i<searchedProducts.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("product.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ProductController productController = fxmlLoader.getController();
                productController.setData(searchedProducts.get(i),myListener);

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
//    for search

    //    for search API
    public List<Product> requestProducts(String textFieldInput) throws IOException{

        List<Product> searchedProducts = new LinkedList<>();

        OkHttpClient client = new OkHttpClient();

        String requestURL = "https://magic-aliexpress1.p.rapidapi.com/api/products/search?name=" + textFieldInput + "&minSalePrice=5&shipToCountry=FR&sort=NEWEST_DESC&page=1&shipFromCountry=CN&fastDelivery=true&getShopInformation=false";
        Request request = new Request.Builder()
                .url(requestURL)
                .get()
                .addHeader("X-RapidAPI-Host", "magic-aliexpress1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "d4cff52ce8msh3828a37b56e73dep161941jsn8f48abfe9b56")
                .build();

        Response response = client.newCall(request).execute();
        //here you can check status code

        //jsonData - the whole data as a string
        String jsonData = response.body().string();

        //jsonObj - the whole data as a JSONObject
        JSONObject jsonObj = new JSONObject(jsonData);

        //ja_data - "docs" part, e.g. here are all the products as a JSONArray
        JSONArray ja_data = jsonObj.getJSONArray("docs");


        for(int i = 0; i < ja_data.length(); i++)
        {
            //so for each product I need to retrieve information
            //object is a product as a JSONObject
            JSONObject object = ja_data.getJSONObject(i);
            Object price = object.get("app_sale_price");
            //System.out.println(price);

            Object title = object.get("product_title");


//            //"product_main_image_url" not working, so we'll take a URL from metadata->image->imgURL
            JSONObject metadata = object.getJSONObject("metadata");

            JSONObject image = metadata.getJSONObject("image");
            Object imageURL = image.get("imgUrl");

            //Object imageURL = object.getJSONObject("metadata").getJSONObject("image").get("imgUrl");


            Product product = new Product();
            product.setName(title.toString());
            product.setImgSrc(imageURL.toString());

            //casting Object to Double
            if (price instanceof Double)
                product.setPrice((Double) price);
            else if (price instanceof Integer)
                product.setPrice((Integer) price);
            else
                product.setPrice(-1.0);


            searchedProducts.add(product);
        }
        return searchedProducts;
    }
//    for search API



//    global current Products variable
    private Product currentProduct;


    public void setChosenProduct(Product product) throws IOException {
        currentProduct=product;
        productName.setText(product.getName());
        productPrice.setText(Aliexpress.CURRENCY + product.getPrice());

        URL url = new URL(product.getImgSrc());

        try (InputStream in = url.openStream();
             ReadableByteChannel rbc = Channels.newChannel(in);
             FileOutputStream fos = new FileOutputStream("output.jpg")) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }

        File file = new File("output.jpg");
        Image image = new Image(file.toURI().toString());
        productImageView.setImage(image);
//        image = new Image(getClass().getResourceAsStream(product.getImgSrc()));
//        productImageView.setImage(image);
        choosenProductCard.setStyle("    -fx-background-color: #"+ product.getColor() +";\n" +
                "    -fx-background-radius: 30;");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            products.addAll(getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (products.size() > 0) {
            try {
                setChosenProduct(products.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) throws IOException {
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



    public void  setNewData() {
        VBox vbox = new VBox();
        scroll.setContent(vbox);

        try {
            for (int i=0; i<products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("basketProduct.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                basketProductController basketproductcontroller = fxmlLoader.getController();
                basketproductcontroller.setBrandData(currentProduct);


                vbox.getChildren().clear();
                vbox.getChildren().setAll(anchorPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void cartSystem() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Aliexpress_main.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            String css = this.getClass().getResource("style.css").toExternalForm();
            root2.getStylesheets().add(css);
            stage.setTitle("My Account");
            stage.setScene(new Scene(root2));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void myAccountGo() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyAcoount.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            String css = this.getClass().getResource("style.css").toExternalForm();
            root2.getStylesheets().add(css);
            stage.setTitle("My Account");
            stage.setScene(new Scene(root2));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void labelGoTo() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Basket.fxml"));
            Parent root2 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            String css = this.getClass().getResource("style.css").toExternalForm();
            root2.getStylesheets().add(css);
            stage.setTitle("My Account");
            stage.setScene(new Scene(root2));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

//    @FXML
//    void button_send_data() {
//        static_label.setText("BOOMBOX");
//    }





    }