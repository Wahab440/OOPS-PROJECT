package com.example.librarymanagementapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LibrarianDashboardController{

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField bookAuthorField;

    @FXML
    private ListView<String> bookListView;

    // Method to add a book
    @FXML
    public void handleAddBook(ActionEvent event) {
        String title = bookTitleField.getText();
        String author = bookAuthorField.getText();

        if (title.isEmpty() || author.isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please enter both book title and author.");
            alert.showAndWait();
        } else {
            String book = title + " by " + author;
            bookListView.getItems().add(book);
            bookTitleField.clear();
            bookAuthorField.clear();
        }
    }

    // Method to handle logout (goes back to the main menu)
    @FXML
    public void handleLogout(ActionEvent event) {
        // Transition to the main screen
        System.out.println("Logging out as Librarian.");
        // You can load the main menu or login screen here
        // Example: loadPage("LibraryManagementUI.fxml", "Library Management");
    }

    // You can add other librarian-specific methods like removing books, etc.
}
