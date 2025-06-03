package com.library.LibraryManagement.repository;

import com.library.LibraryManagement.entity.FinePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinePaymentRepository extends JpaRepository<FinePayment, Integer> {
    List<FinePayment> findByBorrowingId(Integer borrowingId);
    List<FinePayment> findByStatus(FinePayment.PaymentStatus status);
}