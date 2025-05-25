package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.dto.BorrowingStatsDTO;

import java.util.List;

public interface BorrowBookServiceImp {
    Boolean borrowBook(String identityCard, String bookSeri );
    Boolean returnBook(int borrowingId);
    List<BorrowingDTO> getAllBorrowings();
    List<BorrowingDTO> getBorrowingsByIdentityCard(String identityCard);
    List<BorrowingDTO> getOverdueBorrowings();
    long countBorrowingsInWeek(int year, int week);
    long countBorrowingsInMonth(int year, int month);
    List<BorrowingDTO> getBorrowingsInWeek(int year, int week);
    List<BorrowingDTO> getBorrowingsInMonth(int year, int month);

}
