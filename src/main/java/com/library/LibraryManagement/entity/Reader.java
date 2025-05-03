package com.library.LibraryManagement.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_reader")
    private String nameReader;

    @Column(name = "identity_card")
    private String identityCard;

    @Column(name = "created_at")
    private Date createdAt;
}
