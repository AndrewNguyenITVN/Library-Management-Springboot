package com.library.LibraryManagement.repository;

import com.library.LibraryManagement.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
    List<Borrowing> findByIdentityCardIdentityCard(String identityCard);
    List<Borrowing> findByDueDateBeforeAndStatusFalse(Date date);
}
