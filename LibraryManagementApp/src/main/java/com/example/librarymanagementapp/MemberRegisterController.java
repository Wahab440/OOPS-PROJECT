package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class MemberRegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextArea addressField;


    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        LocalDate dob = dobPicker.getValue();
        String address = addressField.getText();

        if (username.isEmpty() || password.isEmpty() || dob == null || address.isEmpty()) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        // Simulate saving to a database
        System.out.println("Registration successful:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Address: " + address);

        // Display success message
        showAlert("Success", "Member registered successfully!", Alert.AlertType.INFORMATION);

        // Clear form fields
        usernameField.clear();
        passwordField.clear();
        dobPicker.setValue(null);
        addressField.clear();

    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
