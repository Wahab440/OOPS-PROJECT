package com.example.librarymanagementapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LibraryManagementApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(LibraryManagementApp.class.getResource("/com/example/librarymanagementapp/Library.fxml"));
        Scene scene = new Scene(loader.load(),600,400);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
