package com.example.librarymanagement.Model;

public class BorrowedBook {

    private String ISBN;
    private String IssueDate;
    private String ReturnDate;

    public BorrowedBook(String ISBN, String issueDate, String returnDate) {
        this.ISBN = ISBN;
        this.IssueDate = issueDate;
        this.ReturnDate = returnDate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getIssueDate() {
        return IssueDate;
    }

    public void setIssueDate(String issueDate) {
        IssueDate = issueDate;
    }

    public String getReturnDate() {
        return ReturnDate;
    }

    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
}
