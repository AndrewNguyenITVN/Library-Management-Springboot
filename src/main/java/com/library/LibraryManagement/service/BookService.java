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
        for(Book book: bookList){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setNameBook(book.getNameBook());
            bookDTO.setStockQuantity(book.getStockQuantity());
            bookDTO.setImageUrl(book.getImageUrl());
            bookDTOList.add(bookDTO);
            bookDTO.setCreatedAt(book.getCreatedAt());
        }
        return bookDTOList;
    }

    @Override
    public boolean addBook(MultipartFile file, String nameBook, int categoryId, int stockQuantity) {
        boolean isInsertSuccess = false;
        try{
            boolean isSave = fileServiceImp.saveFile(file);
            if(isSave){
                Book book = new Book();
                book.setImageUrl(file.getOriginalFilename());
                book.setNameBook(nameBook);
                book.setStockQuantity(stockQuantity);
                Category category = new Category();
                category.setId(categoryId);
                book.setCategoryId(category);
                bookRepository.save(book);
                isInsertSuccess = true;
            }
        }catch(Exception e){
            System.out.println("Loi crate book " + e.getMessage());
        }
        return isInsertSuccess;

    }

    @Override
    public boolean editBook(int id, MultipartFile file, String nameBook, int stockQuantity, int categoryId) {
        boolean isEditSuccess = false;
        try {
            Book book = bookRepository.findById(id).orElse(null);
            if (book != null) {
                // Nếu có file mới, lưu file và cập nhật URL hình ảnh
                if (file != null && !file.isEmpty()) {
                    boolean isSaved = fileServiceImp.saveFile(file);
                    if (isSaved) {
                        book.setImageUrl(file.getOriginalFilename());
                    }
                }
                // Cập nhật thông tin khác
                book.setNameBook(nameBook);
                book.setStockQuantity(stockQuantity);
                Category category = new Category();
                category.setId(categoryId);
                book.setCategoryId(category);

                // Lưu lại
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
        }catch (Exception e){
            System.out.println("Loi delete book " + e.getMessage());
        }
        return  isDelSuccess;
    }

    @Override
    public List<BookDTO> searchBook(String keyword) {
        List<Book> bookList = bookRepository.findByNameBook(keyword);
        List<BookDTO> bookDTOList = new ArrayList<>();

        for (Book book : bookList) {
            BookDTO bookDTO = new BookDTO();
            bookDTO.setId(book.getId());
            bookDTO.setNameBook(book.getNameBook());
            bookDTO.setStockQuantity(book.getStockQuantity());
            bookDTO.setImageUrl(book.getImageUrl());
            bookDTO.setCreatedAt(book.getCreatedAt());
            bookDTOList.add(bookDTO);
        }

        return bookDTOList;
    }
}
