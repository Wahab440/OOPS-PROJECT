module com.example.librarymanagementapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;

    exports com.example.librarymanagementapp;  // Export the package for access by JavaFX FXML loader
    opens com.example.librarymanagementapp to javafx.fxml;  // Allow javafx.fxml to access the package's contents
    exports com.example.librarymanagementapp.Models;
    opens com.example.librarymanagementapp.Models;
}
