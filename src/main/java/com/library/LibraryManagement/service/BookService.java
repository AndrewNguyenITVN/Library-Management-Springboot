package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.service.imp.BookServiceImp;
import com.library.LibraryManagement.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements BookServiceImp {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    FileServiceImp fileServiceImp;

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (Book book : bookList) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setBookSeri(book.getBookSeri());
            bookDTO.setNameBook(book.getNameBook());
            bookDTO.setCategoryId(book.getCategoryId().getId());
            bookDTO.setCategoryName(book.getCategoryId().getNameCate());
            bookDTO.setStockQuantity(book.getStockQuantity());
            bookDTO.setImageUrl(book.getImageUrl());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setPublisher(book.getPublisher());
            bookDTO.setPublishYear(book.getPublishYear());
            bookDTO.setIsbn(book.getIsbn());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setLanguage(book.getLanguage());
            bookDTO.setEdition(book.getEdition());
            bookDTO.setPageCount(book.getPageCount());
            bookDTO.setRating(book.getRating());
            bookDTO.setTotalRatings(book.getTotalRatings());
            bookDTO.setCreatedAt(book.getCreatedAt());
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }

    @Override
    public boolean addBook(MultipartFile file, String bookSeri, String nameBook, int categoryId, int stockQuantity,
                          String author, String publisher, Integer publishYear, String isbn, String description,
                          String language, String edition, Integer pageCount) {
        boolean isInsertSuccess = false;
        try {
            boolean isSave = fileServiceImp.saveFile(file);
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
                    boolean isSaved = fileServiceImp.saveFile(file);
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
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setBookSeri(book.getBookSeri());
            bookDTO.setNameBook(book.getNameBook());
            bookDTO.setCategoryId(book.getCategoryId().getId());
            bookDTO.setCategoryName(book.getCategoryId().getNameCate());
            bookDTO.setStockQuantity(book.getStockQuantity());
            bookDTO.setImageUrl(book.getImageUrl());
            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setPublisher(book.getPublisher());
            bookDTO.setPublishYear(book.getPublishYear());
            bookDTO.setIsbn(book.getIsbn());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setLanguage(book.getLanguage());
            bookDTO.setEdition(book.getEdition());
            bookDTO.setPageCount(book.getPageCount());
            bookDTO.setRating(book.getRating());
            bookDTO.setTotalRatings(book.getTotalRatings());
            bookDTO.setCreatedAt(book.getCreatedAt());
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }

    @Override
    public BookDTO searchBookByBookSeri(String bookSeri) {
        Book book = bookRepository.findByBookSeri(bookSeri);
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(book.getId());
        bookDTO.setBookSeri(book.getBookSeri());
        bookDTO.setNameBook(book.getNameBook());
        bookDTO.setCategoryId(book.getCategoryId().getId());
        bookDTO.setCategoryName(book.getCategoryId().getNameCate());
        bookDTO.setStockQuantity(book.getStockQuantity());
        bookDTO.setImageUrl(book.getImageUrl());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPublishYear(book.getPublishYear());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setLanguage(book.getLanguage());
        bookDTO.setEdition(book.getEdition());
        bookDTO.setPageCount(book.getPageCount());
        bookDTO.setRating(book.getRating());
        bookDTO.setTotalRatings(book.getTotalRatings());
        bookDTO.setCreatedAt(book.getCreatedAt());

        return bookDTO;
    }
}
