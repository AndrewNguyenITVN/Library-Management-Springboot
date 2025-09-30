package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.entity.Borrowing;

public interface EmailServiceImp {
    void sendBorrowingConfirmationEmail(Borrowing borrowing);
}

