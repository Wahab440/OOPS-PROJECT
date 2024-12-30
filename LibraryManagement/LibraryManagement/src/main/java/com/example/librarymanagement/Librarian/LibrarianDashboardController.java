package com.example.librarymanagement.Librarian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianDashboardController {

    @FXML
    private Button ReturnRequestbtn;

    @FXML
    private Button BorrowRequestsbtn;

    @FXML
    private Button ManageFinebtn;

    @FXML
    private Button Logoutbtn;

    @FXML
    private void goToReturnRequests() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/LibViewRequestBooks.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);  // Adjust size as needed
        Stage stage = (Stage) ReturnRequestbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Librarian - View Request Books");
        stage.show();
    }

    @FXML
    private void goToBorrowBooks() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/ManageBorrowRequests.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);  // Adjust size as needed
        Stage stage = (Stage) BorrowRequestsbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Librarian - View Request Books");
        stage.show();
    }

    @FXML
    private void goToManageFine() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/LibManageFine.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);  // Adjust size as needed
        Stage stage = (Stage) ManageFinebtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Librarian - Manage Fine");
        stage.show();
    }

    @FXML
    private void logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/Library.fxml"));
        Scene scene = new Scene(loader.load(), 489, 400);  // Adjust size as needed
        Stage stage = (Stage) Logoutbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
}
