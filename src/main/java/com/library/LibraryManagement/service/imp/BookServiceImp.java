package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface BookServiceImp {
    List<BookDTO> getAllBook();
    boolean addBook(MultipartFile file, String nameBook, int categoryId, int stockQuantity);
    boolean delBook(int id);
    boolean editBook(int id, MultipartFile file, String nameBook, int stockQuantity, int categoryId);
    List<BookDTO> searchBook(String keyword);
}
