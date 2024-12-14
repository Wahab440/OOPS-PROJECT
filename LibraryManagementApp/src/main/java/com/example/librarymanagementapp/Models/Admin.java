package com.example.librarymanagementapp.Models;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{
    public Admin(String Username, String Password) {
        super(Username, Password);
        List<Book> bookList = new ArrayList<>();
    }

    public void RegisterLibrarian(Button Registerbtn) throws IOException {
        OpenRegisterLibrarian(Registerbtn);
    }

    public void RegisterMember(Button Registerbtn) throws IOException {
        OpenRegisterPage(Registerbtn);
    }

    public void AddBook(){

    }

    public void ViewBook(){

    }

    public void RemoveBook(){

    }

    protected void OpenRegisterPage(Button Registerbtn) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/MemberRegister.fxml"));
        Scene scene = new Scene(loader.load(), 600,400);
        Stage stage = (Stage) Registerbtn.getScene().getWindow();
        stage.setTitle("MEMBER REGISTER!");
        stage.setScene(scene);
        stage.show();
    }

    protected void OpenRegisterLibrarian(Button Registerbtn) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/librarymanagementapp/LibrarianRegister.fxml"));
        Scene scene = new Scene(loader.load(), 600,400);
        Stage stage = (Stage) Registerbtn.getScene().getWindow();
        stage.setTitle("LIBRARIAN REGISTER!");
        stage.setScene(scene);
        stage.show();
    }
}
