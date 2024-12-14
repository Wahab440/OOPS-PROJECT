package com.example.librarymanagementapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import java.util.Optional;

public class AdminController {

    // Handler for registering a new member
    @FXML
    private void registerMember() {
        // Get member name and email through input dialogs
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Register Member");
        nameDialog.setHeaderText("Enter member's name:");
        Optional<String> memberName = nameDialog.showAndWait();

        if (memberName.isPresent()) {
            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setTitle("Register Member");
            emailDialog.setHeaderText("Enter member's email:");
            Optional<String> memberEmail = emailDialog.showAndWait();

            if (memberEmail.isPresent()) {
                // Logic to add the member to database or list
                System.out.println("Member Registered: " + memberName.get() + ", Email: " + memberEmail.get());
                showAlert("Success", "Member registered successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Email is required.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Name is required.", Alert.AlertType.ERROR);
        }
    }

    // Handler to view all books
    @FXML
    private void viewBooks() {
        // Example of displaying a simple book list in a message dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Available Books");
        alert.setHeaderText("Here is the list of available books:");

        StringBuilder booksList = new StringBuilder();
        booksList.append("1. Java Programming\n");
        booksList.append("2. Data Structures\n");
        booksList.append("3. Algorithms\n");
        booksList.append("4. Web Development\n");

        alert.setContentText(booksList.toString());
        alert.showAndWait();
    }

    // Handler for adding a new book
    @FXML
    private void addBook() {
        TextInputDialog titleDialog = new TextInputDialog();
        titleDialog.setTitle("Add New Book");
        titleDialog.setHeaderText("Enter book title:");
        Optional<String> bookTitle = titleDialog.showAndWait();

        if (bookTitle.isPresent()) {
            TextInputDialog authorDialog = new TextInputDialog();
            authorDialog.setTitle("Add New Book");
            authorDialog.setHeaderText("Enter book author:");
            Optional<String> bookAuthor = authorDialog.showAndWait();

            if (bookAuthor.isPresent()) {
                // Logic to add the book to database or list
                System.out.println("Book Added: " + bookTitle.get() + " by " + bookAuthor.get());
                showAlert("Success", "Book added successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Author is required.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Book title is required.", Alert.AlertType.ERROR);
        }
    }

    // Handler to notify all members
    @FXML
    private void notifyMembers() {
        TextInputDialog notificationDialog = new TextInputDialog();
        notificationDialog.setTitle("Notify Members");
        notificationDialog.setHeaderText("Enter your notification message:");
        Optional<String> notificationMessage = notificationDialog.showAndWait();

        if (notificationMessage.isPresent()) {
            // Logic to send notification to all members
            System.out.println("Notification Sent: " + notificationMessage.get());
            showAlert("Success", "Notification sent successfully to all members.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "Notification message is required.", Alert.AlertType.ERROR);
        }
    }

    // Utility method to show alerts
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
