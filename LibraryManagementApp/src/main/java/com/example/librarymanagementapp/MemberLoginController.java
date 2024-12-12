package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class MemberLoginController {

    @FXML private Button Loginbtn;
    @FXML private Hyperlink RegisterLink;
    @FXML private TextField UserNametf;
    @FXML private PasswordField Passwordpf;


    @FXML
    protected void OpenRegisterPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/MemberRegister.fxml"));
        Scene scene = new Scene(loader.load(), 510,460);
        Stage stage = (Stage) RegisterLink.getScene().getWindow();
        stage.setTitle("MEMBER LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void LoginUser(){
        String username = UserNametf.getText();
        String password = Passwordpf.getText();

        if(username.isEmpty() || password.isEmpty() || username.isBlank() || password.isBlank()){
            showAlert("Empty Fields!", "Please Enter Username and Password.", Alert.AlertType.ERROR);
            return;
        }

        try(Connection conn = DriverManager.getConnection("")){

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
