//package com.example.librarymanagement.Admin;
//
//import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class AdminHomeController {
//
//    @FXML
//    private Label TotalBooksl;
//
//    @FXML
//    private Label TotalStudentsl;
//
//    @FXML
//    private Label TotalStaffl;
//
//    @FXML
//    private ListView<String> Bookslv;
//
//    @FXML
//    private ListView<String> Studentslv;
//
//    @FXML
//    private ListView<String> Librarianslv;
//
//    @FXML
//    private Button Backbtn;
//
//    private Connection connection;
//
//    @FXML
//    public void initialize() throws SQLException {
//        connection = DatabaseConnection.getConnection();
//        updateData();
//    }
//
//    private void updateData() {
//        updateTotalBooks();
//        updateTotalStudents();
//        updateTotalStaff();
//        populateBooksListView();
//        populateStudentsListView();
//        populateLibrariansListView();
//    }
//
//    private void updateTotalBooks() {
//        String query = "SELECT COUNT(*) AS TotalBooks FROM Books";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                TotalBooksl.setText(String.valueOf(resultSet.getInt("TotalBooks")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//    }
//
//    private void updateTotalStudents() {
//        String query = "SELECT COUNT(*) AS TotalStudents FROM NewUsers WHERE UserType = 'MEMBER'";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                TotalStudentsl.setText(String.valueOf(resultSet.getInt("TotalStudents")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//    }
//
//    private void updateTotalStaff() {
//        String query = "SELECT COUNT(*) AS TotalStaff FROM NewUsers WHERE UserType = 'LIBRARIAN'";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                TotalStaffl.setText(String.valueOf(resultSet.getInt("TotalStaff")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//    }
//
//    private void populateBooksListView() {
//        ObservableList<String> books = FXCollections.observableArrayList();
//        String query = "SELECT Book FROM Books";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                books.add(resultSet.getString("Book"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//        Bookslv.setItems(books);
//    }
//
//    private void populateStudentsListView() {
//        ObservableList<String> students = FXCollections.observableArrayList();
//        String query = "SELECT Username FROM NewUsers WHERE UserType = 'MEMBER'";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                students.add(resultSet.getString("Username"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//        Studentslv.setItems(students);
//    }
//
//    private void populateLibrariansListView() {
//        ObservableList<String> librarians = FXCollections.observableArrayList();
//        String query = "SELECT Username FROM NewUsers WHERE UserType = 'LIBRARIAN'";
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             ResultSet resultSet = statement.executeQuery()) {
//            while (resultSet.next()) {
//                librarians.add(resultSet.getString("Username"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle database error
//        }
//        Librarianslv.setItems(librarians);
//    }
//
//    @FXML
//    private void GoBack() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/AdminDashboard.fxml"));
//        Scene scene = new Scene(loader.load(), 320, 265);
//        Stage stage = (Stage) Backbtn.getScene().getWindow();
//        stage.setScene(scene);
//        stage.setTitle("Admin Dashboard!");
//        stage.show();
//    }
//}

package com.example.librarymanagement.Admin;

import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminHomeController {

    @FXML
    private Label TotalBooksl;

    @FXML
    private Label TotalStudentsl;

    @FXML
    private Label TotalStaffl;

    @FXML
    private ListView<String> Bookslv;

    @FXML
    private ListView<String> Studentslv;

    @FXML
    private ListView<String> Librarianslv;

    @FXML
    private Button Backbtn;

    private Connection connection;

    @FXML
    public void initialize() throws SQLException {
        connection = DatabaseConnection.getConnection();
        updateData();
        setupListViewListeners();
    }

    private void updateData() {
        updateTotalBooks();
        updateTotalStudents();
        updateTotalStaff();
        populateBooksListView();
        populateStudentsListView();
        populateLibrariansListView();
    }

    private void updateTotalBooks() {
        String query = "SELECT COUNT(*) AS TotalBooks FROM Books";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                TotalBooksl.setText(String.valueOf(resultSet.getInt("TotalBooks")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void updateTotalStudents() {
        String query = "SELECT COUNT(*) AS TotalStudents FROM NewUsers WHERE UserType = 'MEMBER'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                TotalStudentsl.setText(String.valueOf(resultSet.getInt("TotalStudents")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void updateTotalStaff() {
        String query = "SELECT COUNT(*) AS TotalStaff FROM NewUsers WHERE UserType = 'LIBRARIAN'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                TotalStaffl.setText(String.valueOf(resultSet.getInt("TotalStaff")));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void populateBooksListView() {
        ObservableList<String> books = FXCollections.observableArrayList();
        String query = "SELECT Book FROM Books";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                books.add(resultSet.getString("Book"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
        Bookslv.setItems(books);
    }

    private void populateStudentsListView() {
        ObservableList<String> students = FXCollections.observableArrayList();
        String query = "SELECT Username FROM NewUsers WHERE UserType = 'MEMBER'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(resultSet.getString("Username"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
        Studentslv.setItems(students);
    }

    private void populateLibrariansListView() {
        ObservableList<String> librarians = FXCollections.observableArrayList();
        String query = "SELECT Username FROM NewUsers WHERE UserType = 'LIBRARIAN'";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                librarians.add(resultSet.getString("Username"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
        Librarianslv.setItems(librarians);
    }

    private void setupListViewListeners() {
        Studentslv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showStudentDetails(newValue);
            }
        });

        Librarianslv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showLibrarianDetails(newValue);
            }
        });

        Bookslv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showBookDetails(newValue);
            }
        });
    }

    private void showStudentDetails(String studentUsername) {
        String query = "SELECT * FROM NewUsers WHERE Username = ? AND UserType = 'MEMBER'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, studentUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String details = "Username: " + resultSet.getString("Username") +
                            "\nUser ID: " + resultSet.getString("UserID") +
                            "\nPassword: " + resultSet.getString("Password") ;
                    showAlert("Student Details", details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void showLibrarianDetails(String librarianUsername) {
        String query = "SELECT * FROM NewUsers WHERE Username = ? AND UserType = 'LIBRARIAN'";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, librarianUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String details = "Username: " + resultSet.getString("Username") +
                            "\nUser ID: " + resultSet.getString("UserID") +
                            "\nPassword: " + resultSet.getString("Password") ;
                    showAlert("Librarian Details", details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void showBookDetails(String bookTitle) {
        String query = "SELECT * FROM Books WHERE Book = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bookTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String details = "Book Title: " + resultSet.getString("Book") +
                            "\nAuthor: " + resultSet.getString("Author") +
                            "\nISBN: " + resultSet.getString("ISBN") +
                            "\nGenre: " + resultSet.getString("Genre") +
                            "\nRent Price: " + resultSet.getString("RentPrice") +
                            "\nCopies Available: " + resultSet.getString("CopiesAvailable");
                    showAlert("Book Details", details);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle database error
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void GoBack() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/AdminDashboard.fxml"));
        Scene scene = new Scene(loader.load(), 320, 265);
        Stage stage = (Stage) Backbtn.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard!");
        stage.show();
    }
}

