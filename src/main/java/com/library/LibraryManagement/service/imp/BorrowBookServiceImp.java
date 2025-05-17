package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BorrowingDTO;

import java.util.List;

public interface BorrowBookServiceImp {
    Boolean borrowBook(int readerId, int bookId);
    Boolean returnBook(int borrowingId);
    List<BorrowingDTO> getAllBorrowings();
    List<BorrowingDTO> getBorrowingsByReaderId(int readerId);
}
