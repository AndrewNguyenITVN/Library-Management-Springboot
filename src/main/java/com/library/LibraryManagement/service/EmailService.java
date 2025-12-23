package com.library.LibraryManagement.service;

import com.library.LibraryManagement.entity.Borrowing;

import java.math.BigDecimal;

public interface EmailService {
    void sendBorrowingConfirmationEmail(Borrowing borrowing);
    void sendOverdueNoticeEmail(Borrowing borrowing, long overdueDays, BigDecimal fineAmount);
}
