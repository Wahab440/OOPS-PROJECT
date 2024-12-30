package com.example.librarymanagement;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Member.MemberDashboardController;
import com.example.librarymanagement.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class LibraryController {

    @FXML
    private TextField Usernametf;
    @FXML
    private PasswordField Passwordpf;
    @FXML
    private Button Loginbtn;
    @FXML
    private Button Exitbtn;
    @FXML
    private ComboBox<String> Rolecb;
    @FXML
    private Hyperlink RegisterAdminHL;

    @FXML
    protected void initialize() {
        Rolecb.getItems().addAll("ADMIN", "LIBRARIAN", "MEMBER");
        Rolecb.setValue("ADMIN");
    }

    @FXML
    private void RegisterAdmin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/RegisterAdmin.fxml"));
        Scene scene = new Scene(loader.load(), 489, 311);
        Stage stage = (Stage) RegisterAdminHL.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Registration!");
        stage.show();
    }

    @FXML
    private void Login() {
        String Username = Usernametf.getText();
        String Password = Passwordpf.getText();
        String Role = Rolecb.getValue().toUpperCase();

        if (Username.isBlank() || Password.isBlank()) {
            ShowAlert(Alert.AlertType.ERROR, "Error!", "Empty Fields!");
            return;
        }

        String tableName = Role.equals("ADMIN") ? "Users" : "NewUsers";
        String ColumnName = Role.equals("ADMIN") ? "Role" : "UserType";
        String query = "SELECT Password," + ColumnName + " FROM " + tableName + " WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, Username);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    String dbPassword = rs.getString("Password");
                    String dbRole;
                    if(tableName.equals("Users")){
                        dbRole = rs.getString("Role");
                    }else{
                        dbRole = rs.getString("UserType");
                    }

                    if (dbPassword.equals(Password) && dbRole.equals(Role)) {
                        ShowAlert(Alert.AlertType.INFORMATION, "Success", "Welcome Back " + Username + "!");
                        navigateToDashboard(Role);
                    } else {
                        ShowAlert(Alert.AlertType.ERROR, "Error!", "Invalid Role or Password");
                    }
                } else {
                    ShowAlert(Alert.AlertType.ERROR, "Error!", "Invalid Username");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void navigateToDashboard(String Role) throws IOException {
        String fxmlPath = "";
        String title = "";
        int Width;
        int Height;

        switch (Role) {
            case "ADMIN":
                fxmlPath = "/com/example/librarymanagement/AdminDashboard.fxml";
                title = "Admin Dashboard!";
                Width = 320;
                Height = 265;
                break;
            case "LIBRARIAN":
                fxmlPath = "/com/example/librarymanagement/LibrarianDashboard.fxml";
                title = "Librarian Dashboard!";
                Width = 324;
                Height = 334;
                break;
            case "MEMBER":
                fxmlPath = "/com/example/librarymanagement/MemeberDashboard.fxml";
                title = "Member Dashboard!";
                Width = 322;
                Height = 283;
                break;
            default:
                ShowAlert(Alert.AlertType.ERROR, "Error!", "Invalid Role!");
                return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Scene scene = new Scene(loader.load(), Width,Height);
        if(Role.equals("MEMBER")){
            MemberDashboardController memberDashboardController = loader.getController();
            memberDashboardController.Username = Usernametf.getText();
        }
        Stage stage = (Stage) Loginbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    @FXML
    private void Exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("Click OK to exit or Cancel to stay.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) Exitbtn.getScene().getWindow();
            stage.close();
        }
    }

    private void ShowAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
