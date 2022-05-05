module com.example.aliexpress_clone {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires okhttp3;
    requires json;


    opens com.example.aliexpress_clone to javafx.fxml;
    exports com.example.aliexpress_clone;
}