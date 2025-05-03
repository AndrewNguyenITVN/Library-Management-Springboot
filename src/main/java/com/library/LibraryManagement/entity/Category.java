package com.library.LibraryManagement.entity;

import jakarta.persistence.*;

@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_cate")
    private String nameCate;

}
