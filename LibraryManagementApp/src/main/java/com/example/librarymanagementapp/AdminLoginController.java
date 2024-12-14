package com.example.librarymanagementapp;

import com.example.librarymanagementapp.Models.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginController{


    @FXML private Label loginbtn;
    @FXML private TextField Usernametf;
    @FXML private PasswordField Passwordpf;

    private Admin admin;

    @FXML
    protected void loginadmin(){
        String username = Usernametf.getText();
        String password = Passwordpf.getText();

        if(username.isEmpty() || password.isEmpty() || username.isBlank() || password.isBlank()){
            showAlert("Empty Fields!", "Please Enter Username and Password.", Alert.AlertType.ERROR);
            return;
        }

        String query = "SELECT Password FROM admintable WHERE UserName = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);){

            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String storedPassword = rs.getString("Password");
                if(password.equals(storedPassword)){
                    showAlert("Success","Welcome " + username, Alert.AlertType.INFORMATION);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/AdminDashboard.fxml"));
                    Scene scene = new Scene(loader.load(),600,400);
                    AdminDashboardController adminDashboardController = loader.getController();
                    admin = new Admin(username,password);
                    adminDashboardController.setAdmin(admin);
                    Stage stage = (Stage) loginbtn.getScene().getWindow();
                    stage.setTitle("MEMBER DASHBOARD!");
                    stage.setScene(scene);
                    stage.show();
                }else{
                    showAlert("Failure","Invalid Password!", Alert.AlertType.ERROR);
                    return;
                }
            }else{
                showAlert("Failure","Invalid Username!", Alert.AlertType.ERROR);
            }

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
