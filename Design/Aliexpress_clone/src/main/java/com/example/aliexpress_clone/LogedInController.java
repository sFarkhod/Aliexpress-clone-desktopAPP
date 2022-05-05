package com.example.aliexpress_clone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LogedInController implements Initializable {

    @FXML
    private Button CancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField tf_username;

    @FXML
    private TextField tf_password;

    // some importing for stage

    private Stage stage;
    private Scene scene;
    private Parent root;


//    islomabdulloyev01
//    1234islom

    public void loginButtonOnAction(ActionEvent event) throws IOException {

        if (tf_username.getText().isBlank() == false && tf_password.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please fill all the inputs");
        }
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() throws IOException {
        DBUtils connectNow = new DBUtils();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM users WHERE username = '" + tf_username.getText() + "' AND password = '" + tf_password.getText() +  "'";

        try {
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("Happy Shopping");
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Aliexpress_main.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    String css = this.getClass().getResource("style.css").toExternalForm();
                    root1.getStylesheets().add(css);
                    stage.setTitle("Aliexpress-clone");
                    stage.setScene(new Scene(root1));
                    stage.show();
                }else {
                    loginMessageLabel.setText("Invalid login please try again");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


    }


    public void gotoSignUp() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Signup.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            String css = this.getClass().getResource("style.css").toExternalForm();
            root1.getStylesheets().add(css);
            stage.setTitle("Register");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
