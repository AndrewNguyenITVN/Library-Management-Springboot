package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.mapper.BookMapper;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.service.BookService;
import com.library.LibraryManagement.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    FileService fileService;

    @Autowired
    BookMapper bookMapper;

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : bookList) {
            bookDTOList.add(bookMapper.toDTO(book));
        }
        return bookDTOList;
    }

    @Override
    public boolean addBook(MultipartFile file, String bookSeri, String nameBook, int categoryId, int stockQuantity,
                          String author, String publisher, Integer publishYear, String isbn, String description,
                          String language, String edition, Integer pageCount) {
        boolean isInsertSuccess = false;
        try {
            boolean isSave = fileService.saveFile(file);
            if (isSave) {
                Book book = new Book();
                book.setImageUrl(file.getOriginalFilename());
                book.setBookSeri(bookSeri);
                book.setNameBook(nameBook);
                book.setStockQuantity(stockQuantity);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setPublishYear(publishYear);
                book.setIsbn(isbn);
                book.setDescription(description);
                book.setLanguage(language);
                book.setEdition(edition);
                book.setPageCount(pageCount);
                book.setRating(BigDecimal.ZERO);
                book.setTotalRatings(0);
                
                Category category = new Category();
                category.setId(categoryId);
                book.setCategoryId(category);
                bookRepository.save(book);
                isInsertSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi tạo sách: " + e.getMessage());
        }
        return isInsertSuccess;
    }

    @Override
    public boolean editBook(int id, MultipartFile file, String bookSeri, String nameBook, int stockQuantity,
                          int categoryId, String author, String publisher, Integer publishYear, String isbn,
                          String description, String language, String edition, Integer pageCount) {
        boolean isEditSuccess = false;
        try {
            Book book = bookRepository.findById(id).orElse(null);
            if (book != null) {
                if (file != null && !file.isEmpty()) {
                    boolean isSaved = fileService.saveFile(file);
                    if (isSaved) {
                        book.setImageUrl(file.getOriginalFilename());
                    }
                }
                book.setNameBook(nameBook);
                book.setStockQuantity(stockQuantity);
                book.setBookSeri(bookSeri);
                book.setAuthor(author);
                book.setPublisher(publisher);
                book.setPublishYear(publishYear);
                book.setIsbn(isbn);
                book.setDescription(description);
                book.setLanguage(language);
                book.setEdition(edition);
                book.setPageCount(pageCount);
                
                Category category = new Category();
                category.setId(categoryId);
                book.setCategoryId(category);

                bookRepository.save(book);
                isEditSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật sách: " + e.getMessage());
        }
        return isEditSuccess;
    }

    @Override
    public boolean delBook(int id) {
        boolean isDelSuccess = false;
        try {
            bookRepository.deleteById(id);
            isDelSuccess = true;
        } catch (Exception e) {
            System.out.println("Lỗi xóa sách: " + e.getMessage());
        }
        return isDelSuccess;
    }

    @Override
    public List<BookDTO> searchBook(String bookName) {
        List<Book> bookList = bookRepository.findByNameBookContainingIgnoreCase(bookName);
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (Book book : bookList) {
            bookDTOList.add(bookMapper.toDTO(book));
        }
        return bookDTOList;
    }

    @Override
    public BookDTO searchBookByBookSeri(String bookSeri) {
        Book book = bookRepository.findByBookSeri(bookSeri);
        return bookMapper.toDTO(book);
    }
}
