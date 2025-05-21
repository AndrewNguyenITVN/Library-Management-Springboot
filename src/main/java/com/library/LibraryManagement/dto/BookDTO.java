package com.library.LibraryManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.util.Date;

public class BookDTO {

    private int id;

    private String bookSeri;

    private String nameBook;

    private int stockQuantity;

    private int categoryId;

    private String categoryName;

    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
    private Date createdAt;

    public String getBookSeri() {
        return bookSeri;
    }

    public void setBookSeri(String bookSeri) {
        this.bookSeri = bookSeri;
    }

    public String getNameBook() {
        return nameBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
