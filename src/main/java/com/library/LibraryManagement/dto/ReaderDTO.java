package com.library.LibraryManagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.LibraryManagement.entity.Reader.CardType;
import com.library.LibraryManagement.entity.Reader.ReaderStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.util.Date;

public class ReaderDTO {
    private int id;
    
    @NotBlank(message = "Reader name cannot be blank")
    private String nameReader;
    
    @NotBlank(message = "Identity card cannot be blank")
    private String identityCard;
    
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String phone;
    
    @Email(message = "Invalid email format")
    private String email;
    
    private String address;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
    
    private CardType cardType;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date cardExpiryDate;
    
    private Integer totalBorrowed;
    private Integer totalOverdue;
    private ReaderStatus status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

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
}
