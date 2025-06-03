package com.library.LibraryManagement.dto;

import com.library.LibraryManagement.entity.FinePayment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinePaymentDTO {
    private Integer id;
    private Integer borrowingId;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private FinePayment.PaymentMethod paymentMethod;
    private FinePayment.PaymentStatus status;
    private String notes;
    
    // Additional fields for display
    private String readerName;
    private String bookName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBorrowingId() {
        return borrowingId;
    }

    public void setBorrowingId(Integer borrowingId) {
        this.borrowingId = borrowingId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public FinePayment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(FinePayment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public FinePayment.PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(FinePayment.PaymentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}