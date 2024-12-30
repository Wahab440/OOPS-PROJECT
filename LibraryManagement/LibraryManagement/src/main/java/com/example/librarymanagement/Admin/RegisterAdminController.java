package com.example.librarymanagement.Admin;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Library;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class RegisterAdminController {
    private static final String SYSTEM_ADMIN_KEY = "021089100";
    @FXML
    public Button Backbtn;
    @FXML
    public Button Registerbtn;
    @FXML
    public PasswordField Passwordpf;
    @FXML
    public TextField Usernametf;


    @FXML
    public void RegisterNewAdmin() {
        if(!ValidateUser()){
            System.out.println("Access Denied!");
            return;
        }

        String Username = Usernametf.getText();
        String Password = Passwordpf.getText();

        if(Username.isBlank() || Password.isBlank()){
            showAlert(Alert.AlertType.ERROR,"Error!","Empty Fields!");
            return;
        }

        String query = "INSERT INTO Users (Username, Password, Role) VALUES (?, ?, ?)";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)){

            preparedStatement.setString(1,Username);
            preparedStatement.setString(2,Password);
            String role = "ADMIN";
            preparedStatement.setString(3, role);

            int addedRows = preparedStatement.executeUpdate();
            if(addedRows > 0){
                showAlert(Alert.AlertType.INFORMATION,"Success!","New Admin " + Username + " Added!");
                GoBack();
            }

        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR,"Error!", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void GoBack() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Library.class.getResource("/com/example/librarymanagement/Library.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 489, 400);
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setTitle("Welcome Page!");
        stage.setScene(scene);
        stage.show();
    }

    private Boolean ValidateUser(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Admin Validation");
        dialog.setHeaderText("Enter Admin Key");
        dialog.setContentText("Please enter the system's admin key:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String enteredKey = result.get();
            if (SYSTEM_ADMIN_KEY.equals(enteredKey)) {
                showAlert(Alert.AlertType.INFORMATION, "Access Granted", "The admin key is correct. You may proceed.");
                System.out.println("Registration logic can be implemented here.");
                return true;
            } else {
                showAlert(Alert.AlertType.ERROR, "Access Denied", "The admin key is incorrect.");
                return false;
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Action Cancelled", "No admin key entered.");
            return false;
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
