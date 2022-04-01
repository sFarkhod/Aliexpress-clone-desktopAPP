module com.example.aliexpress_clone {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aliexpress_clone to javafx.fxml;
    exports com.example.aliexpress_clone;
}