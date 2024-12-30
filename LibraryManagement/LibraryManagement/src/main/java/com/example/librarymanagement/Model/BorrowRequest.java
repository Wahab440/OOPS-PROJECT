package com.example.librarymanagement.Model;

public class BorrowRequest {
    private String BookISBN;
    private String Username;
    private String Status;

    public BorrowRequest(){}
    public BorrowRequest(String ISBN, String Username, String Status){
        this.BookISBN = ISBN;
        this.Username = Username;
        this.Status = Status;
    }

    public void SetStatus(String Status){
        this.Status = Status;
    }

    public String getStatus(){
        return this.Status;
    }

    public String getISBN() {
        return this.BookISBN;
    }

    public String getUsername(){
        return this.Username;
    }
}
