package com.library.LibraryManagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fine_payment")
public class FinePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "borrowing_id", nullable = false)
    private Borrowing borrowing;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    public enum PaymentMethod {
        CASH, CARD, TRANSFER, OTHER
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }

    @PrePersist
    protected void onCreate() {
        if (this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Borrowing getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Borrowing borrowing) {
        this.borrowing = borrowing;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}