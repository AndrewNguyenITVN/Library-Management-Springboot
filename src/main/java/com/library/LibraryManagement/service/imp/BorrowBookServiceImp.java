package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Borrowing.DamageStatus;

import java.math.BigDecimal;
import java.util.List;

public interface BorrowBookServiceImp {
    Boolean borrowBook(String identityCard, String bookSeri, String notes);
    Boolean returnBook(int borrowingId, DamageStatus damageStatus, BigDecimal damageFine, String notes);
    List<BorrowingDTO> getAllBorrowings();
    List<BorrowingDTO> getBorrowingsByIdentityCard(String identityCard);
    List<BorrowingDTO> getOverdueBorrowings();
    long countBorrowingsInWeek(int year, int week);
    long countBorrowingsInMonth(int year, int month);
    List<BorrowingDTO> getBorrowingsInWeek(int year, int week);
    List<BorrowingDTO> getBorrowingsInMonth(int year, int month);
    Boolean payFine(int borrowingId, BigDecimal amount, String notes);
}
