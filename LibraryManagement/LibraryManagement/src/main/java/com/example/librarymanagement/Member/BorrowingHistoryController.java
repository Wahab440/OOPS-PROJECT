package com.example.librarymanagement.Member;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Model.BorrowedBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowingHistoryController {

    @FXML
    private TableView<BorrowedBook> BorrowedTable;
    @FXML
    private TableColumn<BorrowedBook, String> ISBNCol;
    @FXML
    private TableColumn<BorrowedBook, String> IssueDateCol;
    @FXML
    private TableColumn<BorrowedBook, String> ReturnDateCol;

    @FXML
    private Button Backbtn;

    private String Username;

    // ObservableList to store borrowed books
    private ObservableList<BorrowedBook> borrowedBooksList = FXCollections.observableArrayList();

    public void setUsername(String username) {
        this.Username = username;
        Initialize();
    }

    private void Initialize() {
        System.out.println("Logged-in user: " + Username);

        // Set up the TableView columns
        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        IssueDateCol.setCellValueFactory(new PropertyValueFactory<>("IssueDate"));
        ReturnDateCol.setCellValueFactory(new PropertyValueFactory<>("ReturnDate"));

        // Load borrowed books for the user
        loadBorrowedBooks();

        // Bind the list to the TableView
        BorrowedTable.setItems(borrowedBooksList);
    }

    private void loadBorrowedBooks() {
        String query = "SELECT ISBN, IssueDate, ReturnDate FROM BorrowedBooks WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, Username);
            ResultSet rs = ps.executeQuery();

            // Clear the list before adding new data
            borrowedBooksList.clear();

            // Populate the list with data from the ResultSet
            while (rs.next()) {
                String isbn = rs.getString("ISBN");
                String issueDate = rs.getDate("IssueDate").toString();
                String returnDate = rs.getDate("ReturnDate").toString();

                BorrowedBook book = new BorrowedBook(isbn, issueDate, returnDate);
                borrowedBooksList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/MemeberDashboard.fxml"));
        Scene scene = new Scene(loader.load(), 322, 283);
        MemberDashboardController memberDashboardController = loader.getController();
        memberDashboardController.Username = this.Username;
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setTitle("Member Dashboard!");
        stage.setScene(scene);
        stage.show();
    }
}
