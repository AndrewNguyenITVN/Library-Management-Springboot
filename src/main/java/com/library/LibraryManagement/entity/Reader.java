package com.library.LibraryManagement.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_reader", nullable = false)
    private String nameReader;

    @Column(name = "identity_card", unique = true, nullable = false)
    private String identityCard;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Column(name = "card_expiry_date")
    @Temporal(TemporalType.DATE)
    private Date cardExpiryDate;

    @Column(name = "total_borrowed")
    private Integer totalBorrowed;

    @Column(name = "total_overdue")
    private Integer totalOverdue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReaderStatus status;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "readerId")
    private Set<Borrowing> borrowingSet;

    @OneToMany(mappedBy = "readerId")
    private Set<LibraryCard> libraryCards;

    @OneToMany(mappedBy = "readerId")
    private Set<BookReservation> reservations;

    @OneToMany(mappedBy = "readerId")
    private Set<Notification> notifications;

    public enum CardType {
        STUDENT, TEACHER, STAFF, OTHER
    }

    public enum ReaderStatus {
        ACTIVE, SUSPENDED, BLOCKED
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        if (this.totalBorrowed == null) {
            this.totalBorrowed = 0;
        }
        if (this.totalOverdue == null) {
            this.totalOverdue = 0;
        }
        if (this.status == null) {
            this.status = ReaderStatus.ACTIVE;
        }
        if (this.cardType == null) {
            this.cardType = CardType.OTHER;
        }
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameReader() {
        return nameReader;
    }

    public void setNameReader(String nameReader) {
        this.nameReader = nameReader;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public Integer getTotalBorrowed() {
        return totalBorrowed;
    }

    public void setTotalBorrowed(Integer totalBorrowed) {
        this.totalBorrowed = totalBorrowed;
    }

    public Integer getTotalOverdue() {
        return totalOverdue;
    }

    public void setTotalOverdue(Integer totalOverdue) {
        this.totalOverdue = totalOverdue;
    }

    public ReaderStatus getStatus() {
        return status;
    }

    public void setStatus(ReaderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Borrowing> getBorrowingSet() {
        return borrowingSet;
    }

    public void setBorrowingSet(Set<Borrowing> borrowingSet) {
        this.borrowingSet = borrowingSet;
    }

    public Set<LibraryCard> getLibraryCards() {
        return libraryCards;
    }

    public void setLibraryCards(Set<LibraryCard> libraryCards) {
        this.libraryCards = libraryCards;
    }

    public Set<BookReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<BookReservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
