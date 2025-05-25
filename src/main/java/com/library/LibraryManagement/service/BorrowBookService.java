package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.dto.BorrowingStatsDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.imp.BorrowBookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowBookService implements BorrowBookServiceImp {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Boolean borrowBook(String identityCard, String bookSeri) {
        // 1. Lấy Reader và Book, ném exception nếu không tồn tại
        Reader reader = readerRepository.findByIdentityCard(identityCard);
        Book book = bookRepository.findByBookSeri(bookSeri);

        Borrowing borrowing = new Borrowing();

        borrowing.setIdentityCard(reader);

        borrowing.setBookSeri(book);

        Date now = new Date();
        borrowing.setBorrowedAt(now);

        long sevenDaysMs = 7L * 24 * 60 * 60 * 1000;
        Date due = new Date(now.getTime() + sevenDaysMs);
        borrowing.setDueDate(due);

        borrowing.setReturnedAt(null);
        borrowing.setStatus(false);

        if (book.getStockQuantity() <= 0) {
            throw new RuntimeException("Sách đã hết hàng");
        }
        book.setStockQuantity(book.getStockQuantity() - 1);
        bookRepository.save(book);

        try {
            borrowingRepository.save(borrowing);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi ghi borrowing: " + e.getMessage());
            return false;
        }

    }

    @Override
    public Boolean returnBook(int borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Mượn trả không tồn tại"));
        borrowing.setReturnedAt(new Date());
        borrowing.setStatus(true);
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
            if(borrowing.getStatus() == false){
                BorrowingDTO borrowingDTO = new BorrowingDTO();
                borrowingDTO.setIdBorrow(borrowing.getId());
                borrowingDTO.setBookSeri(borrowing.getBookSeri().getBookSeri());
                borrowingDTO.setIdentityCard(borrowing.getIdentityCard().getIdentityCard());
                borrowingDTO.setBookName(borrowing.getBookSeri().getNameBook());
                borrowingDTO.setReaderName(borrowing.getIdentityCard().getNameReader());
                borrowingDTO.setBorrowedAt(borrowing.getBorrowedAt());
                borrowingDTO.setReturnedAt(borrowing.getReturnedAt());
                borrowingDTO.setDueDate(borrowing.getDueDate());
                borrowingDTO.setStatus(borrowing.getStatus());
                borrowingDTOList.add(borrowingDTO);
            }


        }
        return borrowingDTOList;
    }

    @Override
    public List<BorrowingDTO> getBorrowingsByIdentityCard(String identityCard) {
        List<Borrowing> borrowingList = borrowingRepository.findByIdentityCardIdentityCard(identityCard);
        List<BorrowingDTO> borrowingDTOList = new ArrayList<>();
        for (Borrowing borrowing : borrowingList) {
            if(borrowing.getStatus() == false){
                BorrowingDTO borrowingDTO = new BorrowingDTO();
                borrowingDTO.setIdBorrow(borrowing.getId());
                borrowingDTO.setBookSeri(borrowing.getBookSeri().getBookSeri());
                borrowingDTO.setIdentityCard(borrowing.getIdentityCard().getIdentityCard());
                borrowingDTO.setBookName(borrowing.getBookSeri().getNameBook());
                borrowingDTO.setReaderName(borrowing.getIdentityCard().getNameReader());
                borrowingDTO.setBorrowedAt(borrowing.getBorrowedAt());
                borrowingDTO.setReturnedAt(borrowing.getReturnedAt());
                borrowingDTO.setDueDate(borrowing.getDueDate());
                borrowingDTO.setStatus(borrowing.getStatus());
                borrowingDTOList.add(borrowingDTO);
            }


        }
        return borrowingDTOList;

    }

    private BorrowingDTO toDTO(Borrowing b) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setReaderName(b.getIdentityCard().getNameReader());
        dto.setIdentityCard(b.getIdentityCard().getIdentityCard());
        dto.setPhone(b.getIdentityCard().getPhone());
        dto.setBookSeri(b.getBookSeri().getBookSeri());
        dto.setBookName(b.getBookSeri().getNameBook());
        dto.setBorrowedAt(b.getBorrowedAt());
        dto.setReturnedAt(b.getReturnedAt());
        dto.setDueDate(b.getDueDate());
        dto.setStatus(b.getStatus());
        return dto;
    }


    @Override
    public List<BorrowingDTO> getOverdueBorrowings() {
        Date today = new Date();
        List<Borrowing> overdue = borrowingRepository.findByDueDateBeforeAndStatusFalse(today);
        return overdue.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public long countBorrowingsInWeek(int year, int week) {
        Date weekStart = getWeekStart(year, week);
        Date weekEnd   = getWeekEnd(weekStart);
        return borrowingRepository.countByBorrowedAtBetween(weekStart, weekEnd);
    }

    @Override
    public long countBorrowingsInMonth(int year, int month) {
        Date monthStart = getMonthStart(year, month);
        Date monthEnd   = getMonthEnd(monthStart);
        return borrowingRepository.countByBorrowedAtBetween(monthStart, monthEnd);
    }

    @Override
    public List<BorrowingDTO> getBorrowingsInWeek(int year, int week) {
        Date weekStart = getWeekStart(year, week);
        Date weekEnd   = getWeekEnd(weekStart);
        return borrowingRepository.findByBorrowedAtBetween(weekStart, weekEnd)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getBorrowingsInMonth(int year, int month) {
        Date monthStart = getMonthStart(year, month);
        Date monthEnd   = getMonthEnd(monthStart);
        return borrowingRepository.findByBorrowedAtBetween(monthStart, monthEnd)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // helper tính tuần
    private Date getWeekStart(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        setStartOfDay(cal);
        return cal.getTime();
    }

    private Date getWeekEnd(Date weekStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(weekStart);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        setEndOfDay(cal);
        return cal.getTime();
    }

    // helper tính tháng
    private Date getMonthStart(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        setStartOfDay(cal);
        return cal.getTime();
    }

    private Date getMonthEnd(Date monthStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(monthStart);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        setEndOfDay(cal);
        return cal.getTime();
    }

    private void setStartOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private void setEndOfDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    }
}
