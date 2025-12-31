package com.library.LibraryManagement.mapper;

import com.library.LibraryManagement.dto.FinePaymentDTO;
import com.library.LibraryManagement.entity.FinePayment;
import org.springframework.stereotype.Component;

@Component
public class FinePaymentMapper {

    public FinePaymentDTO toDTO(FinePayment payment) {
        if (payment == null) {
            return null;
        }

        FinePaymentDTO dto = new FinePaymentDTO();
        dto.setId(payment.getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setStatus(payment.getStatus());
        dto.setNotes(payment.getNotes());
        
        if (payment.getBorrowing() != null) {
            dto.setBorrowingId(payment.getBorrowing().getId());
            
            if (payment.getBorrowing().getReaderId() != null) {
                dto.setReaderName(payment.getBorrowing().getReaderId().getNameReader());
            }
            
            if (payment.getBorrowing().getBookId() != null) {
                dto.setBookName(payment.getBorrowing().getBookId().getNameBook());
            }
        }
        
        return dto;
    }
}

