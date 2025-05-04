package com.library.LibraryManagement.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_cate")
    private String nameCate;

    @OneToMany(mappedBy = "categoryId")
    private Set<Book> bookSet;
}
