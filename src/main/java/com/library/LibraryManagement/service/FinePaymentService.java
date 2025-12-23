package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.FinePaymentDTO;
import com.library.LibraryManagement.entity.FinePayment;
import java.util.List;

public interface FinePaymentService {
    List<FinePaymentDTO> getAllPayments();
    FinePaymentDTO getPaymentById(Integer id);
    List<FinePaymentDTO> getPaymentsByBorrowingId(Integer borrowingId);
    List<FinePaymentDTO> getPaymentsByStatus(FinePayment.PaymentStatus status);
    boolean createPayment(FinePaymentDTO paymentDTO);
    boolean updatePayment(Integer id, FinePaymentDTO paymentDTO);
    boolean updatePaymentStatus(Integer id, FinePayment.PaymentStatus status);
    boolean deletePayment(Integer id);
}
