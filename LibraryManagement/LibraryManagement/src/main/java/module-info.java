module com.example.librarymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.librarymanagement to javafx.fxml;
    exports com.example.librarymanagement;
    opens com.example.librarymanagement.Admin to javafx.fxml;
    exports com.example.librarymanagement.Admin;
    opens com.example.librarymanagement.Librarian to javafx.fxml;
    exports com.example.librarymanagement.Librarian;
    opens com.example.librarymanagement.Member to javafx.fxml;
    exports com.example.librarymanagement.Member;
    opens com.example.librarymanagement.Model to javafx.fxml;
    exports com.example.librarymanagement.Model;
}