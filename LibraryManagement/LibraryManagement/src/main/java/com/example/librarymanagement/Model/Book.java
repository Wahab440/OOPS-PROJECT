package com.example.librarymanagement.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Currency;

public class Book {
    private String ISBN;
    private String bookName;
    private String authorName;
    private String genre;
    private float rentPrice;
    private Integer copiesAvailable;
    private boolean canBeIssued;

    // Default Constructor
    public Book() {
    }

    // Parameterized Constructor
    public Book(String ISBN, String bookName, String authorName, String genre, float rentPrice, Integer copiesAvailable, boolean canBeIssued) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.authorName = authorName;
        this.genre = genre;
        this.rentPrice = rentPrice;
        this.copiesAvailable = copiesAvailable;
        this.canBeIssued = canBeIssued;
    }

    public Book(String isbn, String bookName, String author, String genre) {
        this.ISBN = isbn;
        this.bookName = bookName;
        this.authorName = author;
        this.genre = genre;
    }

    // Getters and Setters
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(float rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Integer getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(Integer copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public boolean isCanBeIssued() {
        return canBeIssued;
    }

    public void setCanBeIssued(boolean canBeIssued) {
        this.canBeIssued = canBeIssued;
    }

    // Check if the book is available
    public boolean isAvailable() {
        return copiesAvailable != null && copiesAvailable > 0;
    }

    // Issue a book
    public boolean issueBook() {
        if (isAvailable() && canBeIssued) {
            copiesAvailable--;
            return true;
        }
        return false;
    }

    // Return a book
    public void returnBook() {
        copiesAvailable++;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN='" + ISBN + '\'' +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", genre='" + genre + '\'' +
                ", rentPrice=" + rentPrice +
                ", copiesAvailable=" + copiesAvailable +
                ", canBeIssued=" + canBeIssued +
                '}';
    }
}
