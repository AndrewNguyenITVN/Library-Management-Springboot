package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.imp.BorrowBookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class BorrowBookService implements BorrowBookServiceImp {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Boolean borrowBook(int readerId, int bookId) {
        Reader reader = readerRepository.findById(readerId)
                .orElseThrow(() -> new RuntimeException("Reader không tồn tại"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book không tồn tại"));

        Borrowing borrowing = new Borrowing();
        borrowing.setReaderId(reader);
        borrowing.setBookId(book);
        borrowing.setBorrowedAt(new Date());
        borrowing.setDueDate(new Date());
        borrowing.setStatus(true);
        try{
            borrowingRepository.save(borrowing);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }

    @Override
    public Boolean returnBook(int borrowingId) {
        return null;
    }

    @Override
    public List<BorrowingDTO> getAllBorrowings() {
        return List.of();
    }

    @Override
    public List<BorrowingDTO> getBorrowingsByReaderId(int readerId) {
        return List.of();
    }
}
