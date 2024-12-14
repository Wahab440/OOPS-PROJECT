package com.example.librarymanagementapp;

import com.example.librarymanagementapp.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibrarianRegisterController {

    @FXML private Button Registerbtn;
    @FXML private TextField Usernametf;
    @FXML private PasswordField Passwordpf;

    @FXML
    protected void RegisterUser() {
        String username = Usernametf.getText();
        String password = Passwordpf.getText();

        if(username.isBlank() || password.isBlank()){
            showAlert("ERROR!","Please Fill out all the Fields", Alert.AlertType.ERROR);
            return;
        }

        String insertQuery = "INSERT INTO librarian (UserName, Password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert("Success", "Librarian Registered Successfully!", Alert.AlertType.INFORMATION);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/AdminDashboard.fxml"));
                Scene scene = new Scene(loader.load(), 600,400);
                Stage stage = (Stage) Registerbtn.getScene().getWindow();
                stage.setTitle("ADMIN DASHBOARD!");
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Error", "Failed to Register the Librarian.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while Registering: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException e) {
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
