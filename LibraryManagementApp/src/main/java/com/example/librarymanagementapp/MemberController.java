package com.example.librarymanagementapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

public class MemberController {

    @FXML
    private ListView<String> availableBooksListView;

    // Method to borrow a book
    @FXML
    public void handleBorrowBook(ActionEvent event) {
        String selectedBook = availableBooksListView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {
            availableBooksListView.getItems().remove(selectedBook);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Book Borrowed");
            alert.setHeaderText("You have successfully borrowed the book: " + selectedBook);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setHeaderText("Please select a book to borrow.");
            alert.showAndWait();
        }
    }

    // Method to handle logout (goes back to the main menu)
    @FXML
    public void handleLogout(ActionEvent event) {
        // Transition to the main screen
        System.out.println("Logging out as Member.");
    }

    // Method to view all available books (set the list from main app or librarian)
    public void setAvailableBooks(ListView<String> books) {
        availableBooksListView.setItems(books.getItems());
    }
}
