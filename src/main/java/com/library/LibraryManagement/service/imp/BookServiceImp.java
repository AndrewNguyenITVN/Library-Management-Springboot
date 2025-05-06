package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Category;

import java.util.Date;
import java.util.List;

public interface BookServiceImp {
    List<BookDTO> getAllBook();
//    void addBook( String nameBook, Category categoryId, int stockQuantity, String imageUrl);
//    void delBook(int id);
//    void editBook(int id);
}
