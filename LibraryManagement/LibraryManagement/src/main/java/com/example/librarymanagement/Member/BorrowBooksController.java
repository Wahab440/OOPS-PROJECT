package com.example.librarymanagement.Member;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowBooksController {

    @FXML
    private TableView<Book> BooksTable;
    @FXML
    private TableColumn<Book, String> ISBNCol;
    @FXML
    private TableColumn<Book, String> BookCol;
    @FXML
    private TableColumn<Book, String> AuthorCol;
    @FXML
    private TableColumn<Book, String> GenreCol;
    @FXML
    private ComboBox<String> Filtercb;
    @FXML
    private Button Borrowbtn;
    @FXML
    private Button filterbtn;
    @FXML
    private Button Backbtn;

    @FXML
    protected void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/MemeberDashboard.fxml"));
        Scene scene = new Scene(loader.load(),322,283);
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setTitle("Member Dashboard!");
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Book> books = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up table columns
        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        BookCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        AuthorCol.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        GenreCol.setCellValueFactory(new PropertyValueFactory<>("Genre"));

        // Add filter options to ComboBox
        Filtercb.setItems(FXCollections.observableArrayList("Author", "Genre"));

        // Load all books initially
        loadBooks();
    }

    private void loadBooks() {
        String query = "SELECT * FROM Books";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            books.clear();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("ISBN"),
                        rs.getString("Book"),
                        rs.getString("Author"),
                        rs.getString("Genre")
                ));
            }
            BooksTable.setItems(books);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load books!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFilter() {
        String selectedFilter = Filtercb.getSelectionModel().getSelectedItem();
        if (selectedFilter == null) {
            showAlert(Alert.AlertType.WARNING, "Filter Error", "Please select a filter option!");
            return;
        }

        // Create a dynamic pop-up stage
        Stage filterStage = new Stage(StageStyle.UTILITY);
        filterStage.initModality(Modality.APPLICATION_MODAL);
        filterStage.setTitle("Enter " + selectedFilter);

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        Label promptLabel = new Label("Enter " + selectedFilter + ":");
        TextField inputField = new TextField();
        Button applyFilterBtn = new Button("Apply");
        layout.getChildren().addAll(promptLabel, inputField, applyFilterBtn);

        applyFilterBtn.setOnAction(e -> {
            String filterValue = inputField.getText().trim();
            if (filterValue.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Filter Error", "Please enter a value!");
            } else {
                applyFilter(selectedFilter, filterValue);
                filterStage.close();
            }
        });

        filterStage.setScene(new Scene(layout));
        filterStage.showAndWait();
    }

    private void applyFilter(String filterBy, String filterValue) {
        String query = "SELECT * FROM Books WHERE " + filterBy + " LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, "%" + filterValue + "%");
            try (ResultSet rs = preparedStatement.executeQuery()) {
                books.clear();
                while (rs.next()) {
                    books.add(new Book(
                            rs.getString("ISBN"),
                            rs.getString("Book"),
                            rs.getString("Author"),
                            rs.getString("Genre")
                    ));
                }
                BooksTable.setItems(books);
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to filter books!");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBorrow() {
        Book selectedBook = BooksTable.getSelectionModel().getSelectedItem();
        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to borrow!");
            return;
        }

        // Create a dynamic pop-up to confirm user credentials
        Stage credentialsStage = new Stage(StageStyle.UTILITY);
        credentialsStage.initModality(Modality.APPLICATION_MODAL);
        credentialsStage.setTitle("Confirm Credentials");

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20;");
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button confirmBtn = new Button("Confirm");
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, confirmBtn);

        confirmBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Input Error", "Please enter both username and password!");
            } else {
                verifyCredentialsAndBorrow(selectedBook, username, password);
                credentialsStage.close();
            }
        });

        credentialsStage.setScene(new Scene(layout));
        credentialsStage.showAndWait();
    }

    private void verifyCredentialsAndBorrow(Book selectedBook, String username, String password) {
        String query = "SELECT * FROM NewUsers WHERE Username = ? AND Password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    // Insert into BorrowRequests table
                    insertBorrowRequest(selectedBook.getISBN(), username);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Authentication Error", "Invalid credentials!");
                }
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to verify credentials!");
            e.printStackTrace();
        }
    }

    private void insertBorrowRequest(String isbn, String username) {
        String query = "INSERT INTO BorrowRequests (BookISBN, Username, Status) VALUES (?, ?, 'Pending')";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, username);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Borrow request submitted successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to submit borrow request!");
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to submit borrow request!");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
