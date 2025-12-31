package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.FinePaymentDTO;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.entity.FinePayment;
import com.library.LibraryManagement.mapper.FinePaymentMapper;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.repository.FinePaymentRepository;
import com.library.LibraryManagement.service.FinePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FinePaymentServiceImpl implements FinePaymentService {
    @Autowired
    private FinePaymentRepository finePaymentRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private FinePaymentMapper finePaymentMapper;

    @Override
    public List<FinePaymentDTO> getAllPayments() {
        List<FinePayment> payments = finePaymentRepository.findAll();
        return convertToDTOList(payments);
    }

    @Override
    public FinePaymentDTO getPaymentById(Integer id) {
        Optional<FinePayment> payment = finePaymentRepository.findById(id);
        return payment.map(finePaymentMapper::toDTO).orElse(null);
    }

    @Override
    public List<FinePaymentDTO> getPaymentsByBorrowingId(Integer borrowingId) {
        List<FinePayment> payments = finePaymentRepository.findByBorrowingId(borrowingId);
        return convertToDTOList(payments);
    }

    @Override
    public List<FinePaymentDTO> getPaymentsByStatus(FinePayment.PaymentStatus status) {
        List<FinePayment> payments = finePaymentRepository.findByStatus(status);
        return convertToDTOList(payments);
    }

    @Override
    public boolean createPayment(FinePaymentDTO paymentDTO) {
        try {
            FinePayment payment = new FinePayment();

            payment.setAmount(paymentDTO.getAmount());
            payment.setPaymentMethod(paymentDTO.getPaymentMethod());
            payment.setStatus(paymentDTO.getStatus() != null ? paymentDTO.getStatus() : FinePayment.PaymentStatus.PENDING);
            payment.setNotes(paymentDTO.getNotes());
            payment.setPaymentDate(LocalDateTime.now());

            Optional<Borrowing> borrowing = borrowingRepository.findById(paymentDTO.getBorrowingId());
            if (borrowing.isPresent()) {
                payment.setBorrowing(borrowing.get());
                finePaymentRepository.save(payment);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error creating payment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePayment(Integer id, FinePaymentDTO paymentDTO) {
        try {
            Optional<FinePayment> existingPayment = finePaymentRepository.findById(id);
            if (existingPayment.isPresent()) {
                FinePayment payment = existingPayment.get();
                payment.setAmount(paymentDTO.getAmount());
                payment.setPaymentMethod(paymentDTO.getPaymentMethod());
                payment.setStatus(paymentDTO.getStatus());
                payment.setNotes(paymentDTO.getNotes());

                Optional<Borrowing> borrowing = borrowingRepository.findById(paymentDTO.getBorrowingId());
                if (borrowing.isPresent()) {
                    payment.setBorrowing(borrowing.get());
                    finePaymentRepository.save(payment);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error updating payment: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePaymentStatus(Integer id, FinePayment.PaymentStatus status) {
        try {
            Optional<FinePayment> payment = finePaymentRepository.findById(id);
            if (payment.isPresent()) {
                FinePayment existingPayment = payment.get();
                existingPayment.setStatus(status);
                finePaymentRepository.save(existingPayment);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error updating payment status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePayment(Integer id) {
        try {
            finePaymentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting payment: " + e.getMessage());
            return false;
        }
    }

    private List<FinePaymentDTO> convertToDTOList(List<FinePayment> payments) {
        return payments.stream()
                .map(finePaymentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
