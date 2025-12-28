package com.library.LibraryManagement.mapper;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setBookSeri(book.getBookSeri());
        bookDTO.setNameBook(book.getNameBook());
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

        if (book.getCategoryId() != null) {
            bookDTO.setCategoryId(book.getCategoryId().getId());
            bookDTO.setCategoryName(book.getCategoryId().getNameCate());
        }

        return bookDTO;
    }
}


