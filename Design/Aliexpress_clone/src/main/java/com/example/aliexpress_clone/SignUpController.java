package com.example.aliexpress_clone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;


public class SignUpController implements Initializable {

    @FXML
    private Button GobackButton;

    @FXML
    private AnchorPane SignupPane;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label passwordMatchLabel;

    @FXML
    private Button signupFrameButton;

    @FXML
    private Label signupmessageLabel;

    @FXML
    private PasswordField tf_confirmpass;

    @FXML
    private TextField tf_firstname;

    @FXML
    private TextField tf_lastname;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;



    public void goBackCancel(ActionEvent event) {
        Stage stage = (Stage) GobackButton.getScene().getWindow();
        stage.close();
    }

    public void mainsignButton(ActionEvent event) {
        if (tf_password.getText().equals(tf_confirmpass.getText())) {
            registerUser();
            passwordMatchLabel.setText("");
        } else {
            passwordMatchLabel.setText("Password does not match");
        }

    }

    public void registerUser() {
        DBUtils connectNow = new DBUtils();
        Connection connectionDB = connectNow.getConnection();

        String firstname = tf_firstname.getText();
        String secondname = tf_lastname.getText();
        String username = tf_username.getText();
        String password = tf_password.getText();

        String insertFields = "INSERT INTO users(secondname, firstname, username, password) VALUES('";
        String insertValues = firstname + "','" + secondname + "','" + username + "','" + password + "')";
        String insertToRegister = insertFields + insertValues;


        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(insertToRegister);
            passwordLabel.setText("User successfully added!");
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
