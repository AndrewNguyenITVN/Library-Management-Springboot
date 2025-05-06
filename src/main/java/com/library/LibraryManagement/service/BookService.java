package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.service.imp.BookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService implements BookServiceImp {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDTO> getAllBook() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for(Book book: bookList){
            BookDTO bookDTO = new BookDTO();
            bookDTO.setNameBook(book.getNameBook());
            bookDTO.setStockQuantity(book.getStockQuantity());
            bookDTO.setImageUrl(book.getImageUrl());
            bookDTOList.add(bookDTO);
        }
        return bookDTOList;
    }


}
