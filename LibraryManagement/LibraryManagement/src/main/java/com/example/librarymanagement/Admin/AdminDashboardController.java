package com.example.librarymanagement.Admin;

import com.example.librarymanagement.Library;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML private Button Homebtn;
    @FXML private Button ManageUsersbtn;
    @FXML private Button ManageBooksbtn;
    @FXML private Button Logoutbtn;


    @FXML
    protected void Logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Library.class.getResource("/com/example/librarymanagement/Library.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 489, 400);
        Stage stage = (Stage) Logoutbtn.getScene().getWindow();
        stage.setTitle("Welcome Page!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void HomePage() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Library.class.getResource("/com/example/librarymanagement/AdminHome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 625, 465);
        Stage stage = (Stage) Homebtn.getScene().getWindow();
        stage.setTitle("ADMIN HOME PAGE!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void ManageBooks() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Library.class.getResource("/com/example/librarymanagement/ManageBooks.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 800);
        Stage stage = (Stage) ManageBooksbtn.getScene().getWindow();
        stage.setTitle("Manage Books!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void ManageUsers() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Library.class.getResource("/com/example/librarymanagement/ManageUsers.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 650);
        Stage stage = (Stage) ManageUsersbtn.getScene().getWindow();
        stage.setTitle("Manage Users");
        stage.setScene(scene);
        stage.show();
    }
}
