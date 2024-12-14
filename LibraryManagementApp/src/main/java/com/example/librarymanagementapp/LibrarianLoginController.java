package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibrarianLoginController{

    @FXML private Button loginbtn;
    @FXML private TextField usernametf;
    @FXML private PasswordField passwordpf;


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void LoginLibrarian() {
        String username = usernametf.getText();
        String password = passwordpf.getText();

        if(username.isEmpty() || password.isEmpty() || username.isBlank() || password.isBlank()){
            showAlert("Empty Fields!", "Please Enter Username and Password.", Alert.AlertType.ERROR);
            return;
        }

        String query = "SELECT Password FROM librarian WHERE UserName = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement statement = conn.prepareStatement(query);){

            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                String storedPassword = rs.getString("Password");
                if(password.equals(storedPassword)){
                    showAlert("Success","Welcome " + username, Alert.AlertType.INFORMATION);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/LibrarianDashboard.fxml"));
                    Scene scene = new Scene(loader.load(),600,400);
                    Stage stage = (Stage) loginbtn.getScene().getWindow();
                    stage.setTitle("LIBRARIAN DASHBOARD!");
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
}
