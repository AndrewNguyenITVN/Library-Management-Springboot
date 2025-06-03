package com.library.LibraryManagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Entity(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "identity_card", nullable = false)
    private String identityCard;

    @Column(name = "book_seri", nullable = false)
    private String bookSeri;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader readerId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    @Column(name = "borrowed_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedAt;

    @Column(name = "due_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    @Column(name = "returned_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedAt;

    @Column(name = "status", nullable = false)
    private Integer status; // 0: Đang mượn, 1: Đã trả, 2: Quá hạn

    @Column(name = "fine_amount", precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Column(name = "fine_paid")
    private Boolean finePaid;

    @Enumerated(EnumType.STRING)
    @Column(name = "damage_status")
    private DamageStatus damageStatus;

    @Column(name = "damage_fine", precision = 10, scale = 2)
    private BigDecimal damageFine;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "borrowing")
    private Set<FinePayment> finePayments;

    public enum DamageStatus {
        NONE, MINOR, MAJOR, LOST
    }

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = 0; // Đang mượn
        }
        if (this.fineAmount == null) {
            this.fineAmount = BigDecimal.ZERO;
        }
        if (this.finePaid == null) {
            this.finePaid = false;
        }
        if (this.damageStatus == null) {
            this.damageStatus = DamageStatus.NONE;
        }
        if (this.damageFine == null) {
            this.damageFine = BigDecimal.ZERO;
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBookSeri() {
        return bookSeri;
    }

    public void setBookSeri(String bookSeri) {
        this.bookSeri = bookSeri;
    }

    public Reader getReaderId() {
        return readerId;
    }

    public void setReaderId(Reader readerId) {
        this.readerId = readerId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Date getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(Date borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(Date returnedAt) {
        this.returnedAt = returnedAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Boolean getFinePaid() {
        return finePaid;
    }

    public void setFinePaid(Boolean finePaid) {
        this.finePaid = finePaid;
    }

    public DamageStatus getDamageStatus() {
        return damageStatus;
    }

    public void setDamageStatus(DamageStatus damageStatus) {
        this.damageStatus = damageStatus;
    }

    public BigDecimal getDamageFine() {
        return damageFine;
    }

    public void setDamageFine(BigDecimal damageFine) {
        this.damageFine = damageFine;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<FinePayment> getFinePayments() {
        return finePayments;
    }

    public void setFinePayments(Set<FinePayment> finePayments) {
        this.finePayments = finePayments;
    }
}
