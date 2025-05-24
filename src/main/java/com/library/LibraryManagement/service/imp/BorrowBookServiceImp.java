package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BorrowingDTO;

import java.util.List;

public interface BorrowBookServiceImp {
    Boolean borrowBook(String identityCard, String bookSeri );
    Boolean returnBook(int borrowingId);
    List<BorrowingDTO> getAllBorrowings();
    List<BorrowingDTO> getBorrowingsByIdentityCard(String identityCard);
    List<BorrowingDTO> getOverdueBorrowings();
}
