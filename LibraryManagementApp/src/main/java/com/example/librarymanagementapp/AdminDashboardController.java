package com.example.librarymanagementapp;

import com.example.librarymanagementapp.Models.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController{

    Admin admin;

    public void setAdmin(Admin a){
        admin = a;
    }

    @FXML
    private Button Logoutbtn;

    @FXML
    private Button RegisterLibrarianbtn;

    @FXML
    private Button RegisterMemberbtn;

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    protected void RegisterLibrarianClicked() throws IOException {
        admin.RegisterLibrarian(RegisterLibrarianbtn);
    }

    @FXML
    protected void RegisterMemberClicked() throws IOException {
        admin.RegisterMember(RegisterMemberbtn);
    }

    @FXML
    protected void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(LibraryManagementApp.class.getResource("/com/example/librarymanagementapp/Library.fxml"));
        Scene scene = new Scene(loader.load(),600,400);
        Stage stage = (Stage) Logoutbtn.getScene().getWindow();
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }
}
