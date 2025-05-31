package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface BookServiceImp {
    List<BookDTO> getAllBook();
    boolean addBook(MultipartFile file, String bookSeri, String nameBook, int categoryId, int stockQuantity,
                    String author, String publisher, Integer publishYear, String isbn, String description,
                    String language, String edition, Integer pageCount);
    boolean delBook(int id);
    boolean editBook(int id, MultipartFile file, String bookSeri, String nameBook, int stockQuantity,
                     int categoryId, String author, String publisher, Integer publishYear, String isbn,
                     String description, String language, String edition, Integer pageCount);
    List<BookDTO> searchBook(String keyword);
    BookDTO searchBookByBookSeri(String bookSeri);
}
