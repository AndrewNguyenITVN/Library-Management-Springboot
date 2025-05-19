package com.library.LibraryManagement.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity(name = "reader")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name_reader")
    private String nameReader;
    @Column(name = "identity_card", unique = true, nullable = false)
    private String identityCard;

    @Column(name = "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "created_at", insertable=false)
    private Date createdAt;

    @OneToMany(mappedBy = "identityCard")
    private Set<Borrowing> borrowingSet;

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
}
