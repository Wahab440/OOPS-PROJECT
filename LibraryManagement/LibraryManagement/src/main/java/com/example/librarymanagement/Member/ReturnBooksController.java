//package com.example.librarymanagement.Member;
//
//import com.example.librarymanagement.Model.Book;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class ReturnBooksController {
//
//    public String Username;
//
//    @FXML
//    private Button ReturnBookbtn;
//    @FXML
//    private Button Backbtn;
//
//    @FXML
//    private ListView<Book> BorrowedBooksListView;
//
//    @FXML
//    protected void GoBack() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/MemeberDashboard.fxml"));
//        Scene scene = new Scene(loader.load(),322,283);
//        Stage stage = (Stage) Backbtn.getScene().getWindow();
//        stage.setTitle("Member Dashboard!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//}

package com.example.librarymanagement.Member;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Model.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReturnBooksController {

    private String Username;

    public void setUsername(String username){
        this.Username = username;
        Initialize();
    }

    @FXML
    private Button ReturnBookbtn;
    @FXML
    private Button Backbtn;
    @FXML
    private ListView<String> BorrowedBooksListView;


    public void Initialize() {
        loadBorrowedBooks();
        System.out.println(Username);
    }

    private void loadBorrowedBooks() {
        String query = "SELECT ISBN, IssueDate, ReturnDate FROM BorrowedBooks WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bookEntry = rs.getString("ISBN") + " | Issued: " + rs.getDate("IssueDate") + " | Return: " + rs.getDate("ReturnDate");
                BorrowedBooksListView.getItems().add(bookEntry);
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load borrowed books.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleReturnBook() {
        String selectedBook = BorrowedBooksListView.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to return.");
            return;
        }

        String isbn = selectedBook.split(" \\| ")[0]; // Extract ISBN
        String query = "SELECT ReturnDate FROM BorrowedBooks WHERE ISBN = ? AND Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, isbn);
            ps.setString(2, Username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate returnDate = rs.getDate("ReturnDate").toLocalDate();
                LocalDate currentDate = LocalDate.now();

                if (currentDate.isAfter(returnDate)) {
                    long daysLate = currentDate.toEpochDay() - returnDate.toEpochDay();
                    double fine = daysLate * 2.0; // Assuming fine is $2 per day
                    showAlert(Alert.AlertType.INFORMATION, "Fine", "Book is overdue! Fine: $" + fine);
                }
            }

            // Remove book from BorrowedBooks
            String deleteQuery = "DELETE FROM BorrowedBooks WHERE ISBN = ? AND Username = ?";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, isbn);
                deleteStmt.setString(2, Username);
                deleteStmt.executeUpdate();
            }

            // Increment available copies in Books table
            String updateQuery = "UPDATE Books SET CopiesAvailable = CopiesAvailable + 1 WHERE ISBN = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                updateStmt.setString(1, isbn);
                updateStmt.executeUpdate();
            }

            // Refresh ListView
            BorrowedBooksListView.getItems().remove(selectedBook);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book returned successfully!");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to return the book.");
            e.printStackTrace();
        }
    }

    @FXML
    protected void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/MemeberDashboard.fxml"));
        Scene scene = new Scene(loader.load(), 322, 283);
        MemberDashboardController memberDashboardController = loader.getController();
        memberDashboardController.Username = this.Username;
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setTitle("Member Dashboard!");
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
