package com.library.LibraryManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "identity_card")
    private Reader identityCard;

    @ManyToOne
    @JoinColumn(name = "book_seri")
    private Book bookSeri;

    @Column(name = "borrowed_at")
    private Date borrowedAt;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "returned_at")
    private Date returnedAt;

    @Column(name = "status")
    private Boolean status; // true: đã trả, false: đang mượn


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reader getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(Reader identityCard) {
        this.identityCard = identityCard;
    }

    public Book getBookSeri() {
        return bookSeri;
    }

    public void setBookSeri(Book bookSeri) {
        this.bookSeri = bookSeri;
    }

    public Date getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(Date borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(Date returnedAt) {
        this.returnedAt = returnedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
