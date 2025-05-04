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
    @JoinColumn(name = "reader_id")
    private Reader readerId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    @Column(name = "borrowed_at")
    private Date borrowedAt;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "returned_at")
    private Date returnedAt;

    @Column(name = "status")
    private Boolean status; // true: đã trả, false: đang mượn
}
