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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Mượn trả không tồn tại"));
        borrowing.setReturnedAt(new Date());
        borrowing.setStatus(false);
        try{
            borrowingRepository.save(borrowing);
            return true;
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    @Override
    public List<BorrowingDTO> getAllBorrowings() {
        List<Borrowing> borrowingList= borrowingRepository.findAll();
        List<BorrowingDTO> borrowingDTOList = new ArrayList<>();
        for (Borrowing borrowing : borrowingList) {
            BorrowingDTO borrowingDTO = new BorrowingDTO();
            borrowingDTO.setBookId(borrowing.getBookId().getId());
            borrowingDTO.setReaderId(borrowing.getReaderId().getId());
            borrowingDTO.setBorrowedAt(borrowing.getBorrowedAt());
            borrowingDTO.setDueDate(borrowing.getDueDate());
            borrowingDTO.setStatus(borrowing.getStatus());

            borrowingDTOList.add(borrowingDTO);
        }
        return borrowingDTOList;
    }

    @Override
    public List<BorrowingDTO> getBorrowingsByReaderId(int readerId) {
        return borrowingRepository.findByReaderId(readerId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private BorrowingDTO toDTO(Borrowing b) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setReaderId(b.getReaderId().getId());
        dto.setBookId(b.getBookId().getId());
        dto.setBookTitle(b.getBookId().getNameBook());
        dto.setBorrowedAt(b.getBorrowedAt());
        dto.setReturnedAt(b.getReturnedAt());
        dto.setStatus(b.getStatus());
        return dto;
    }

}
