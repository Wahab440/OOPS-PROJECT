package com.example.librarymanagement.Admin;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import com.example.librarymanagement.Model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageBookController {

    @FXML
    private TextField ISBNtf;

    @FXML
    private TextField BookNametf;

    @FXML
    private TextField Authortf;

    @FXML
    private ComboBox<String> Genrecb;

    @FXML
    private TextField RentPricetf;

    @FXML
    private TextField Copiestf;

    @FXML
    private TableView<Book> BooksTable;

    @FXML
    private TableColumn<Book, String> ISBNCol;

    @FXML
    private TableColumn<Book, String> BookNameCol;

    @FXML
    private TableColumn<Book, String> AuthorCol;

    @FXML
    private TableColumn<Book, String> GenreCol;

    @FXML
    private TableColumn<Book, Double> RentPriceCol;

    @FXML
    private TableColumn<Book, Integer> CopiesCol;

    @FXML
    private Button Backbtn;

    private ObservableList<Book> booksList = FXCollections.observableArrayList();

    private Connection connection;

    public void initialize() throws SQLException {
        // Initialize database connection
        connection = DatabaseConnection.getConnection();

        // Set up combo box options for genres
        Genrecb.setItems(FXCollections.observableArrayList("Fiction", "Non-Fiction", "Science", "Biography", "Fantasy", "Mystery"));

        // Configure table columns
        ISBNCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        BookNameCol.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        AuthorCol.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        GenreCol.setCellValueFactory(new PropertyValueFactory<>("genre"));
        RentPriceCol.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        CopiesCol.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));

        loadBooksFromDatabase();
    }

    private void loadBooksFromDatabase() {
        booksList.clear();
        String query = "SELECT * FROM Books";

        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                booksList.add(new Book(
                        resultSet.getString("ISBN"),
                        resultSet.getString("Book"),
                        resultSet.getString("Author"),
                        resultSet.getString("Genre"),
                        resultSet.getFloat("RentPrice"),
                        resultSet.getInt("CopiesAvailable"),
                        resultSet.getInt("CopiesAvailable") > 0
                ));
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load books from the database.", e.getMessage());
        }

        BooksTable.setItems(booksList);
    }

    @FXML
    void AddBook(ActionEvent event) {
        if (ISBNtf.getText().isBlank() || BookNametf.getText().isBlank() || Authortf.getText().isBlank() || Genrecb.getValue() == null || RentPricetf.getText().isBlank() || Copiestf.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!", null, "Please fill out all the fields!");
            return;
        }

        String query = "INSERT INTO Books (ISBN, Book, Author, Genre, RentPrice, CopiesAvailable) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ISBNtf.getText());
            statement.setString(2, BookNametf.getText());
            statement.setString(3, Authortf.getText());
            statement.setString(4, Genrecb.getValue());
            statement.setDouble(5, Double.parseDouble(RentPricetf.getText()));
            statement.setInt(6, Integer.parseInt(Copiestf.getText()));
            statement.executeUpdate();
            clearFields();
            loadBooksFromDatabase();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully.", null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the book.", e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter valid numeric values for Rent Price and Copies.", null);
        }
    }

    @FXML
    void EditBook(ActionEvent event) {
        if (ISBNtf.getText().isBlank() || BookNametf.getText().isBlank() || Authortf.getText().isBlank() || Genrecb.getValue() == null || RentPricetf.getText().isBlank() || Copiestf.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!", null, "Please fill out all the fields!");
            return;
        }

        String query = "UPDATE Books SET Book = ?, Author = ?, Genre = ?, RentPrice = ?, CopiesAvailable = ? WHERE ISBN = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, BookNametf.getText());
            statement.setString(2, Authortf.getText());
            statement.setString(3, Genrecb.getValue());
            statement.setDouble(4, Double.parseDouble(RentPricetf.getText()));
            statement.setInt(5, Integer.parseInt(Copiestf.getText()));
            statement.setString(6, ISBNtf.getText());
            statement.executeUpdate();
            clearFields();
            loadBooksFromDatabase();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully.", null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update the book.", e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter valid numeric values for Rent Price and Copies.", null);
        }
    }

    @FXML
    void DeleteBook(ActionEvent event) {
        if (ISBNtf.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "ERROR!", null, "Please select a book first!");
            return;
        }

        String query = "DELETE FROM Books WHERE ISBN = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ISBNtf.getText());
            statement.executeUpdate();
            clearFields();
            loadBooksFromDatabase();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully.", null);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete the book.", e.getMessage());
        }
    }

    @FXML
    private void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/AdminDashboard.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    @FXML
    void handleTableClick(MouseEvent event) {
        Book selectedBook = BooksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            ISBNtf.setText(selectedBook.getISBN());
            BookNametf.setText(selectedBook.getBookName());
            Authortf.setText(selectedBook.getAuthorName());
            Genrecb.setValue(selectedBook.getGenre());
            RentPricetf.setText(String.valueOf(selectedBook.getRentPrice()));
            Copiestf.setText(String.valueOf(selectedBook.getCopiesAvailable()));
        }
    }

    private void clearFields() {
        ISBNtf.clear();
        BookNametf.clear();
        Authortf.clear();
        Genrecb.setValue(null);
        RentPricetf.clear();
        Copiestf.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
