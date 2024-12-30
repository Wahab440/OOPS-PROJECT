//package com.example.librarymanagement.Admin;
//
//import com.example.librarymanagement.Model.User;
//import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class ManageUserController {
//
//    @FXML
//    private TextField Usernametf;
//    @FXML
//    private PasswordField Passwordpf;
//    @FXML
//    private PasswordField CPasswordpf;
//    @FXML
//    private TextField IDtf;
//    @FXML
//    private ComboBox<String> UserTypecb;
//    @FXML
//    private TableView<User> UserTable;
//    @FXML
//    private TableColumn<User, String> idCol;
//    @FXML
//    private TableColumn<User, String> UsernameCol;
//    @FXML
//    private TableColumn<User, String> PasswordCol;
//    @FXML
//    private TableColumn<User, String> UserTypeCol;
//    @FXML
//    private Button Backbtn;
//
//    private ObservableList<User> userList = FXCollections.observableArrayList();
//
//    @FXML
//    public void initialize() {
//        UserTypecb.setItems(FXCollections.observableArrayList("MEMBER", "LIBRARIAN"));
//
//        idCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
//        UsernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
//        PasswordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
//        UserTypeCol.setCellValueFactory(new PropertyValueFactory<>("UserType"));
//
//        UserTable.setItems(userList);
//
//        loadUsersFromDatabase();
//    }
//
//    @FXML
//    private void AddUser() {
//        String username = Usernametf.getText();
//        String password = Passwordpf.getText();
//        String confirmPassword = CPasswordpf.getText();
//        String userType = UserTypecb.getValue();
//        String enrollment = IDtf.getText();
//
//        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userType == null) {
//            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String query = "INSERT INTO NewUsers (Username, UserID, Password, UserType) VALUES (?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, username);
//            statement.setString(2, enrollment);
//            statement.setString(3, password);
//            statement.setString(4, userType);
//
//            statement.executeUpdate();
//
//            if (userType.equals("MEMBER")) {
//                User member = new User();
//                member.setUsername(username);
//                member.setPassword(password);
//                member.setUserID(enrollment);
//                member.setUserType("MEMBER");
//                userList.add(member);
//            } else if (userType.equals("LIBRARIAN")) {
//                User librarian = new User();
//                librarian.setUsername(username);
//                librarian.setPassword(password);
//                librarian.setUserID(enrollment);
//                librarian.setUserType("LIBRARIAN");
//                userList.add(librarian);
//            }
//
//            clearFields();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Error", "Failed to add user to database.", Alert.AlertType.ERROR);
//        }
//    }
//
//    @FXML
//    private void EditUser() {
//        User selectedUser = UserTable.getSelectionModel().getSelectedItem();
//        if (selectedUser == null) {
//            showAlert("Error", "No user selected!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        String newUsername = Usernametf.getText();
//        String newPassword = Passwordpf.getText();
//        String confirmPassword = CPasswordpf.getText();
//        String newUserType = UserTypecb.getValue();
//        String newUserID = IDtf.getText();
//
//        // Input validation
//        if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() || newUserType == null || newUserID.isEmpty()) {
//            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String query = "UPDATE NewUsers SET Username = ?, Password = ?, UserType = ?, UserID = ? WHERE Username = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, newUsername);
//            statement.setString(2, newPassword);
//            statement.setString(3, newUserType);
//            statement.setString(4, newUserID);
//            statement.setString(5, selectedUser.getUsername());
//
//            int rowsUpdated = statement.executeUpdate();
//            if (rowsUpdated > 0) {
//                // Update selected user in the observable list
//                selectedUser.setUsername(newUsername);
//                selectedUser.setPassword(newPassword);
//                selectedUser.setUserType(newUserType);
//                selectedUser.setUserID(newUserID);
//
//                UserTable.refresh();
//                showAlert("Success", "User updated successfully!", Alert.AlertType.INFORMATION);
//                clearFields();
//            } else {
//                showAlert("Error", "Failed to update user. Please try again.", Alert.AlertType.ERROR);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Error", "An error occurred while updating the user in the database.", Alert.AlertType.ERROR);
//        }
//    }
//
//
//    @FXML
//    private void DeleteUser() {
//        User selectedUser = UserTable.getSelectionModel().getSelectedItem();
//        if (selectedUser == null) {
//            showAlert("Error", "No user selected!", Alert.AlertType.ERROR);
//            return;
//        }
//
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String query = "DELETE FROM NewUsers WHERE Username = ?";
//            PreparedStatement statement = connection.prepareStatement(query);
//            statement.setString(1, selectedUser.getUsername());
//
//            statement.executeUpdate();
//
//            userList.remove(selectedUser);
//            clearFields();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Error", "Failed to delete user from database.", Alert.AlertType.ERROR);
//        }
//    }
//
//    private void loadUsersFromDatabase() {
//        userList.clear();
//        try (Connection connection = DatabaseConnection.getConnection()) {
//            String query = "SELECT * FROM NewUsers";
//            PreparedStatement statement = connection.prepareStatement(query);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String username = resultSet.getString("Username");
//                String userId = resultSet.getString("UserID");
//                String password = resultSet.getString("Password");
//                String userType = resultSet.getString("UserType");
//
//                if (userType.equals("MEMBER")) {
//                    User member = new User();
//                    member.setUsername(username);
//                    member.setPassword(password);
//                    member.setUserID(userId);
//                    member.setUserType("MEMBER");
//                    userList.add(member);
//                } else if (userType.equals("LIBRARIAN")) {
//                    User librarian = new User();
//                    librarian.setUsername(username);
//                    librarian.setPassword(password);
//                    librarian.setUserID(userId);
//                    librarian.setUserType("LIBRARIAN");
//                    userList.add(librarian);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert("Error", "Failed to load users from database.", Alert.AlertType.ERROR);
//        }
//    }
//
//    @FXML
//    private void GoBack() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagement/AdminDashboard.fxml"));
//        Scene scene = new Scene(loader.load(),320,265);
//        Stage stage = (Stage) Backbtn.getScene().getWindow();
//        stage.setScene(scene);
//        stage.setTitle("Admin Dashboard!");
//        stage.show();
//    }
//
//    private void clearFields() {
//        Usernametf.clear();
//        Passwordpf.clear();
//        CPasswordpf.clear();
//        IDtf.clear();
//        UserTypecb.setValue(null);
//    }
//
//    private void showAlert(String title, String message, Alert.AlertType alertType) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}

package com.example.librarymanagement.Admin;

import com.example.librarymanagement.Model.User;
import com.example.librarymanagement.DatabaseConnection.DatabaseConnection;
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

public class ManageUserController {

    @FXML
    private TextField Usernametf;
    @FXML
    private PasswordField Passwordpf;
    @FXML
    private PasswordField CPasswordpf;
    @FXML
    private TextField IDtf;
    @FXML
    private ComboBox<String> UserTypecb;
    @FXML
    private TableView<User> UserTable;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> UsernameCol;
    @FXML
    private TableColumn<User, String> PasswordCol;
    @FXML
    private TableColumn<User, String> UserTypeCol;
    @FXML
    private Button Backbtn;

    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        UserTypecb.setItems(FXCollections.observableArrayList("MEMBER", "LIBRARIAN"));

        idCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        UsernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        PasswordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
        UserTypeCol.setCellValueFactory(new PropertyValueFactory<>("UserType"));

        UserTable.setItems(userList);

        loadUsersFromDatabase();
    }

    @FXML
    private void AddUser() {
        String username = Usernametf.getText();
        String password = Passwordpf.getText();
        String confirmPassword = CPasswordpf.getText();
        String userType = UserTypecb.getValue();
        String enrollment = IDtf.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || userType == null || enrollment.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO NewUsers (Username, UserID, Password, UserType) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, enrollment);
            statement.setString(3, password);
            statement.setString(4, userType);

            statement.executeUpdate();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setUserID(enrollment);
            user.setUserType(userType);
            userList.add(user);

            showAlert("Success", "User added successfully!", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add user to database.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void EditUser() {
        User selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "No user selected!", Alert.AlertType.ERROR);
            return;
        }

        String newUsername = Usernametf.getText();
        String newPassword = Passwordpf.getText();
        String confirmPassword = CPasswordpf.getText();
        String newUserType = UserTypecb.getValue();
        String newUserID = IDtf.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() || newUserType == null || newUserID.isEmpty()) {
            showAlert("Error", "All fields must be filled!", Alert.AlertType.ERROR);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "UPDATE NewUsers SET Username = ?, Password = ?, UserType = ?, UserID = ? WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newUsername);
            statement.setString(2, newPassword);
            statement.setString(3, newUserType);
            statement.setString(4, newUserID);
            statement.setString(5, selectedUser.getUsername());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                selectedUser.setUsername(newUsername);
                selectedUser.setPassword(newPassword);
                selectedUser.setUserType(newUserType);
                selectedUser.setUserID(newUserID);

                UserTable.refresh();
                showAlert("Success", "User updated successfully!", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                showAlert("Error", "Failed to update user. Please try again.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the user in the database.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void DeleteUser() {
        User selectedUser = UserTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "No user selected!", Alert.AlertType.ERROR);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM NewUsers WHERE Username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedUser.getUsername());

            statement.executeUpdate();

            userList.remove(selectedUser);
            showAlert("Success", "User deleted successfully!", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete user from database.", Alert.AlertType.ERROR);
        }
    }

    private void loadUsersFromDatabase() {
        userList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM NewUsers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            boolean hasData = false;

            while (resultSet.next()) {
                hasData = true;

                String username = resultSet.getString("Username");
                String userId = resultSet.getString("UserID");
                String password = resultSet.getString("Password");
                String userType = resultSet.getString("UserType");

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setUserID(userId);
                user.setUserType(userType);

                userList.add(user);
            }

            if (!hasData) {
                showAlert("Info", "No users found in the database.", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load users from the database. Please check your connection or query.", Alert.AlertType.ERROR);
        } finally {
            UserTable.refresh();
        }
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

    private void clearFields() {
        Usernametf.clear();
        Passwordpf.clear();
        CPasswordpf.clear();
        IDtf.clear();
        UserTypecb.setValue(null);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
