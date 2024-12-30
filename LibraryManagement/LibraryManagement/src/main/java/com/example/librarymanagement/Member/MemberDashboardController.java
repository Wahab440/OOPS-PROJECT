package com.example.librarymanagement.Member;

import com.example.librarymanagement.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MemberDashboardController {

    public String Username;
    @FXML
    private Label ReturnBookbtn;
    @FXML
    private Button BorrowBooksbtn;

    @FXML
    private Button ViewBorrowingHistorybtn;

    @FXML
    private Button Logoutbtn;

    @FXML
    private void goToBorrowBooks() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/BorrowBooks.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);  // Adjust size as needed
        Stage stage = (Stage) BorrowBooksbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Borrow Books");
        stage.show();
    }

    @FXML
    private void goToReturnBooks() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/ReturnBooks.fxml"));
        Scene scene = new Scene(loader.load(), 300, 475);  // Adjust size as needed
        ReturnBooksController returnBooksController = loader.getController();
        returnBooksController.setUsername(this.Username);
        Stage stage = (Stage) ReturnBookbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Return Books!");
        stage.show();
    }

    @FXML
    private void goToViewBorrowingHistory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/BorrowingHistory.fxml"));
        Scene scene = new Scene(loader.load(), 400, 400);
        BorrowingHistoryController borrowingHistoryController = loader.getController();
        borrowingHistoryController.setUsername(Username);
        Stage stage = (Stage) ViewBorrowingHistorybtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("View Borrowing History");
        stage.show();
    }

    @FXML
    private void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/Library.fxml"));
        Scene scene = new Scene(loader.load(), 489, 400);
        Stage stage = (Stage) Logoutbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Welcome Page!");
        stage.show();
    }
}
