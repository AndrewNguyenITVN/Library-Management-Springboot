package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.FinePaymentDTO;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.entity.FinePayment;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.repository.FinePaymentRepository;
import com.library.LibraryManagement.service.imp.FinePaymentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FinePaymentService implements FinePaymentServiceImp {
    @Autowired
    private FinePaymentRepository finePaymentRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Override
    public List<FinePaymentDTO> getAllPayments() {
        List<FinePayment> payments = finePaymentRepository.findAll();
        return convertToDTOList(payments);
    }

    @Override
    public FinePaymentDTO getPaymentById(Integer id) {
        Optional<FinePayment> payment = finePaymentRepository.findById(id);
        return payment.map(this::convertToDTO).orElse(null);
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

    private FinePaymentDTO convertToDTO(FinePayment payment) {
        FinePaymentDTO dto = new FinePaymentDTO();
        dto.setId(payment.getId());
        dto.setBorrowingId(payment.getBorrowing().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setNotes(payment.getNotes());
        
        // Set additional display fields
        dto.setReaderName(payment.getBorrowing().getReaderId().getNameReader());
        dto.setBookName(payment.getBorrowing().getBookId().getNameBook());
        
        return dto;
    }

    private List<FinePaymentDTO> convertToDTOList(List<FinePayment> payments) {
        List<FinePaymentDTO> dtoList = new ArrayList<>();
        for (FinePayment payment : payments) {
            dtoList.add(convertToDTO(payment));
        }
        return dtoList;
    }
} 