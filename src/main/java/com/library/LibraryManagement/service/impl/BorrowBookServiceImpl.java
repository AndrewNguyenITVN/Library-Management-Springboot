package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Book;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.entity.Borrowing.DamageStatus;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.repository.BookRepository;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.BorrowBookService;
import com.library.LibraryManagement.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowBookServiceImpl implements BorrowBookService {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Boolean borrowBook(String identityCard, String bookSeri, String notes) {
        try {
            Reader reader = readerRepository.findByIdentityCard(identityCard);
            if (reader == null) {
                throw new RuntimeException("Độc giả không tồn tại");
            }

            Book book = bookRepository.findByBookSeri(bookSeri);
            if (book == null) {
                throw new RuntimeException("Sách không tồn tại");
            }

            if (book.getStockQuantity() <= 0) {
                throw new RuntimeException("Sách đã hết hàng");
            }

            Borrowing borrowing = new Borrowing();
            borrowing.setBookId(book);
            borrowing.setReaderId(reader);
            borrowing.setIdentityCard(reader.getIdentityCard());
            borrowing.setBookSeri(book.getBookSeri());
            borrowing.setBorrowedAt(new Date());
            
            // Set due date to 7 days from now
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 7);
            borrowing.setDueDate(cal.getTime());
            
            borrowing.setReturnedAt(null);
            borrowing.setStatus(0); // 0 = borrowed, 1 = returned
            borrowing.setFineAmount(BigDecimal.ZERO);
            borrowing.setFinePaid(false);
            borrowing.setDamageStatus(DamageStatus.NONE);
            borrowing.setDamageFine(BigDecimal.ZERO);
            borrowing.setNotes(notes);

            // Update book quantity
            book.setStockQuantity(book.getStockQuantity() - 1);
            bookRepository.save(book);

            // Update reader's total borrowed
            reader.setTotalBorrowed(reader.getTotalBorrowed() + 1);
            readerRepository.save(reader);

            Borrowing savedBorrowing = borrowingRepository.save(borrowing);
            emailService.sendBorrowingConfirmationEmail(savedBorrowing);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi mượn sách: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean returnBook(int borrowingId, DamageStatus damageStatus, BigDecimal damageFine, String notes) {
        try {
            Borrowing borrowing = borrowingRepository.findById(borrowingId)
                    .orElseThrow(() -> new RuntimeException("Mượn trả không tồn tại"));

            if (borrowing.getStatus() == 1) {
                throw new RuntimeException("Sách đã được trả");
            }

            borrowing.setReturnedAt(new Date());
            borrowing.setStatus(1); // 1 = returned
            borrowing.setDamageStatus(damageStatus);
            borrowing.setDamageFine(damageFine);
            borrowing.setNotes(notes);

            // Calculate fine if overdue
            if (borrowing.getReturnedAt().after(borrowing.getDueDate())) {
                long diffInMillis = borrowing.getReturnedAt().getTime() - borrowing.getDueDate().getTime();
                long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
                BigDecimal fine = new BigDecimal(diffInDays).multiply(new BigDecimal("10000")); // 10,000 VND per day
                borrowing.setFineAmount(fine);
            }

            // Update book quantity
            Book book = borrowing.getBookId();
            book.setStockQuantity(book.getStockQuantity() + 1);
            bookRepository.save(book);

            // Update reader's total overdue if applicable
            if (borrowing.getReturnedAt().after(borrowing.getDueDate())) {
                Reader reader = borrowing.getReaderId();
                reader.setTotalOverdue(reader.getTotalOverdue() + 1);
                readerRepository.save(reader);
            }

            borrowingRepository.save(borrowing);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi trả sách: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<BorrowingDTO> getAllBorrowings() {
        List<Borrowing> borrowingList = borrowingRepository.findAll();
        return borrowingList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getBorrowingsByIdentityCard(String identityCard) {
        List<Borrowing> borrowingList = borrowingRepository.findByIdentityCard(identityCard);
        return borrowingList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getOverdueBorrowings() {
        Date today = new Date();
        List<Borrowing> overdue = borrowingRepository.findByDueDateBeforeAndStatus(today, 0);
        return overdue.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countBorrowingsInWeek(int year, int week) {
        Date weekStart = getWeekStart(year, week);
        Date weekEnd = getWeekEnd(weekStart);
        return borrowingRepository.countByBorrowedAtBetween(weekStart, weekEnd);
    }

    @Override
    public long countBorrowingsInMonth(int year, int month) {
        Date monthStart = getMonthStart(year, month);
        Date monthEnd = getMonthEnd(monthStart);
        return borrowingRepository.countByBorrowedAtBetween(monthStart, monthEnd);
    }

    @Override
    public List<BorrowingDTO> getBorrowingsInWeek(int year, int week) {
        Date weekStart = getWeekStart(year, week);
        Date weekEnd = getWeekEnd(weekStart);
        return borrowingRepository.findByBorrowedAtBetween(weekStart, weekEnd)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowingDTO> getBorrowingsInMonth(int year, int month) {
        Date monthStart = getMonthStart(year, month);
        Date monthEnd = getMonthEnd(monthStart);
        return borrowingRepository.findByBorrowedAtBetween(monthStart, monthEnd)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean payFine(int borrowingId, BigDecimal amount, String notes) {
        try {
            Borrowing borrowing = borrowingRepository.findById(borrowingId)
                    .orElseThrow(() -> new RuntimeException("Mượn trả không tồn tại"));

            if (borrowing.getFinePaid()) {
                throw new RuntimeException("Tiền phạt đã được thanh toán");
            }

            if (amount.compareTo(borrowing.getFineAmount()) < 0) {
                throw new RuntimeException("Số tiền thanh toán không đủ");
            }

            borrowing.setFinePaid(true);
            borrowing.setNotes(notes);
            borrowingRepository.save(borrowing);
            return true;
        } catch (Exception e) {
            System.err.println("Lỗi khi thanh toán tiền phạt: " + e.getMessage());
            return false;
        }
    }

    private BorrowingDTO toDTO(Borrowing b) {
        BorrowingDTO dto = new BorrowingDTO();
        dto.setId(b.getId());
        dto.setReaderName(b.getIdentityCard());
        dto.setIdentityCard(b.getIdentityCard());
        dto.setPhone(b.getIdentityCard());
        dto.setBookSeri(b.getBookSeri());
        dto.setBookName(b.getBookSeri());
        dto.setBorrowedAt(b.getBorrowedAt());
        dto.setReturnedAt(b.getReturnedAt());
        dto.setDueDate(b.getDueDate());
        dto.setStatus(b.getStatus());
        dto.setFineAmount(b.getFineAmount());
        dto.setFinePaid(b.getFinePaid());
        dto.setDamageStatus(b.getDamageStatus());
        dto.setDamageFine(b.getDamageFine());
        dto.setNotes(b.getNotes());
        return dto;
    }

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

