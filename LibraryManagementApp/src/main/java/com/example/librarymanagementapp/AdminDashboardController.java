package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class AdminDashboardController{

    public Button addBookButton;
    public Button viewBooksButton;
    public Button removeBookButton;
    @FXML
    private TableView<?> booksTableView;

    @FXML
    public void handleAddBook() {
        showAlert("Add Book", "Add Book functionality is under development.");
    }

    @FXML
    public void handleViewBooks() {
        showAlert("View Books", "View Books functionality is under development.");
    }

    @FXML
    public void handleRemoveBook() {
        showAlert("Remove Book", "Remove Book functionality is under development.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
