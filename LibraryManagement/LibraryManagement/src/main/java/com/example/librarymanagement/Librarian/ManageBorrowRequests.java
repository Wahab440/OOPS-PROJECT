package com.example.librarymanagement.Librarian;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Model.BorrowRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageBorrowRequests {

    @FXML
    private TableView<BorrowRequest> BorrowRequestsTable;
    @FXML
    private TableColumn<BorrowRequest, String> ISBNCol;
    @FXML
    private TableColumn<BorrowRequest, String> UsernameCol;
    @FXML
    private TableColumn<BorrowRequest, String> StatusCol;

    @FXML
    private Button Backbtn;

    private ObservableList<BorrowRequest> borrowRequests = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns
        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        UsernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        StatusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));

        // Load data from the database
        loadBorrowRequests();
    }

    private void loadBorrowRequests() {
        String query = "SELECT BookISBN, Username, Status FROM BorrowRequests";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                borrowRequests.add(new BorrowRequest(
                        rs.getString("BookISBN"),
                        rs.getString("Username"),
                        rs.getString("Status")
                ));
            }

            BorrowRequestsTable.setItems(borrowRequests);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load borrow requests!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleTableClick() {
        BorrowRequest selectedRequest = BorrowRequestsTable.getSelectionModel().getSelectedItem();

        if (selectedRequest == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a borrow request!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Manage Borrow Request");
        alert.setHeaderText("Borrow Request for ISBN: " + selectedRequest.getISBN());
        alert.setContentText("Do you want to approve or deny this request?");

        ButtonType approveButton = new ButtonType("Approve");
        ButtonType denyButton = new ButtonType("Deny");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(approveButton, denyButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == approveButton) {
                updateRequestStatus(selectedRequest, "Approved");
            } else if (response == denyButton) {
                updateRequestStatus(selectedRequest, "Denied");
            }
        });
    }

    private void updateRequestStatus(BorrowRequest request, String status) {
        String checkCopiesQuery = "SELECT CopiesAvailable FROM Books WHERE ISBN = ?";
        String updateRequestQuery = "UPDATE BorrowRequests SET Status = ? WHERE BookISBN = ? AND Username = ?";
        String updateCopiesQuery = "UPDATE Books SET CopiesAvailable = CopiesAvailable - 1 WHERE ISBN = ?";
        String insertBorrowedBookQuery = "INSERT INTO BorrowedBooks (ISBN, Username, IssueDate, ReturnDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkCopiesStmt = conn.prepareStatement(checkCopiesQuery)) {

            // Check if there are available copies of the book
            checkCopiesStmt.setString(1, request.getISBN());
            try (ResultSet rs = checkCopiesStmt.executeQuery()) {
                if (rs.next()) {
                    int availableCopies = rs.getInt("CopiesAvailable");
                    if (availableCopies <= 0) {
                        showAlert(Alert.AlertType.WARNING, "No Copies Available",
                                "The book with ISBN: " + request.getISBN() + " has no available copies!");
                        return;
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Book Not Found",
                            "The book with ISBN: " + request.getISBN() + " does not exist in the database!");
                    return;
                }
            }

            // Proceed to update the borrow request status
            try (PreparedStatement updateRequestStmt = conn.prepareStatement(updateRequestQuery)) {
                updateRequestStmt.setString(1, status);
                updateRequestStmt.setString(2, request.getISBN());
                updateRequestStmt.setString(3, request.getUsername());

                int rowsAffected = updateRequestStmt.executeUpdate();
                if (rowsAffected > 0) {
                    // If approved, decrement the available copies and add to BorrowedBooks
                    if (status.equals("Approved")) {
                        try (PreparedStatement updateCopiesStmt = conn.prepareStatement(updateCopiesQuery)) {
                            updateCopiesStmt.setString(1, request.getISBN());
                            updateCopiesStmt.executeUpdate();
                        }

                        // Add a record to BorrowedBooks with IssueDate and ReturnDate
                        try (PreparedStatement insertBorrowedBookStmt = conn.prepareStatement(insertBorrowedBookQuery)) {
                            java.sql.Date issueDate = new java.sql.Date(System.currentTimeMillis());
                            java.sql.Date returnDate = new java.sql.Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // Add 7 days

                            insertBorrowedBookStmt.setString(1, request.getISBN());
                            insertBorrowedBookStmt.setString(2, request.getUsername());
                            insertBorrowedBookStmt.setDate(3, issueDate);
                            insertBorrowedBookStmt.setDate(4, returnDate);
                            insertBorrowedBookStmt.executeUpdate();
                        }
                    }

                    request.SetStatus(status); // Update the status in the ObservableList
                    BorrowRequestsTable.refresh(); // Refresh the table view
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Request status updated to " + status + "!");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to update request status!");
                }
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error",
                    "An error occurred while processing the request!");
            e.printStackTrace();
        }
    }

    @FXML
    public void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/LibrarianDashboard.fxml"));
        Scene scene = new Scene(loader.load(),324,334);
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setTitle("Librarian Dashboard!");
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
