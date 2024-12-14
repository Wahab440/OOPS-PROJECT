package com.example.librarymanagementapp.Models;

import java.util.Currency;

public class Book {

    private String bookTitle;
    private String author;
    private String bookID;
    private Integer availableCopies;
    private String bookGenre;
    private Currency bookPrice;

    public Book() {
    }

    public Book(String bookTitle, String author, String bookID, Integer availableCopies, String bookGenre, Currency bookPrice) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.bookID = bookID;
        this.availableCopies = availableCopies;
        this.bookGenre = bookGenre;
        this.bookPrice = bookPrice;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public Integer getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(Integer availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

    public Currency getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Currency bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookTitle='" + bookTitle + '\'' +
                ", author='" + author + '\'' +
                ", bookID='" + bookID + '\'' +
                ", availableCopies=" + availableCopies +
                ", bookGenre='" + bookGenre + '\'' +
                ", bookPrice=" + bookPrice +
                '}';
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void borrowBook() {
        if (availableCopies > 0) {
            availableCopies--;
        } else {
            throw new IllegalStateException("No copies available to borrow.");
        }
    }

    public void returnBook() {
        availableCopies++;
    }
}

