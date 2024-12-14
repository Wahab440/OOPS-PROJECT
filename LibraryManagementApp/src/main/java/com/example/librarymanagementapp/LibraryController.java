package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LibraryController {

    @FXML private Button adminButton;
    @FXML private Button librarianButton;
    @FXML private Button memberButton;

    @FXML
    private void handleAdminRole() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/AdminLogin.fxml"));
        Scene scene = new Scene(loader.load(), 600,400);
        Stage stage = (Stage) adminButton.getScene().getWindow();
        stage.setTitle("ADMIN LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleLibrarianRole() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/LibrarianLogin.fxml"));
        Scene scene = new Scene(loader.load(), 510,460);
        Stage stage = (Stage) librarianButton.getScene().getWindow();
        stage.setTitle("LIBRARIAN LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleMemberRole() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/MemberLogin.fxml"));
        Scene scene = new Scene(loader.load(), 600,400);
        Stage stage = (Stage) memberButton.getScene().getWindow();
        stage.setTitle("MEMBER LOGIN!");
        stage.setScene(scene);
        stage.show();
    }

}
