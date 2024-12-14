package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MemberRegisterController {

    @FXML
    private TextField Usernametf;

    @FXML
    private PasswordField Passwordpf;

    @FXML
    private DatePicker DOBDP;

    @FXML
    private TextField Addresstf;

    @FXML
    private Button Registerbtn;


    @FXML
    public void RegisterUser() {

        String username = Usernametf.getText();
        String password = Passwordpf.getText();
        LocalDate dob = DOBDP.getValue();
        String address = Addresstf.getText();

        if (username.isEmpty() || password.isEmpty() || dob == null || address.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        String insertQuery = "INSERT INTO members (UserName, Password, DateOfBirth, Address) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection(); // Assume DatabaseUtils handles connection
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dob));
            preparedStatement.setString(4, address);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                showAlert("Success", "Member registered successfully!", Alert.AlertType.INFORMATION);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/MemberLogin.fxml"));
                Scene scene = new Scene(loader.load(), 600,400);
                Stage stage = (Stage) Registerbtn.getScene().getWindow();
                stage.setTitle("MEMBER LOGIN!");
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert("Error", "Failed to register the member.", Alert.AlertType.ERROR);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while registering: " + e.getMessage(), Alert.AlertType.ERROR);
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
