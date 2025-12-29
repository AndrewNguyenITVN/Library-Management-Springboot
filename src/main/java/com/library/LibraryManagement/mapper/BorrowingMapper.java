package com.library.LibraryManagement.mapper;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Borrowing;
import org.springframework.stereotype.Component;

@Component
public class BorrowingMapper {

    public BorrowingDTO toDTO(Borrowing borrowing) {
        if (borrowing == null) {
            return null;
        }

        BorrowingDTO dto = new BorrowingDTO();
        dto.setId(borrowing.getId());
        dto.setIdentityCard(borrowing.getIdentityCard());
        dto.setBookSeri(borrowing.getBookSeri());
        dto.setBorrowedAt(borrowing.getBorrowedAt());
        dto.setReturnedAt(borrowing.getReturnedAt());
        dto.setDueDate(borrowing.getDueDate());
        dto.setStatus(borrowing.getStatus());
        dto.setFineAmount(borrowing.getFineAmount());
        dto.setFinePaid(borrowing.getFinePaid());
        dto.setDamageStatus(borrowing.getDamageStatus());
        dto.setDamageFine(borrowing.getDamageFine());
        dto.setNotes(borrowing.getNotes());

        if (borrowing.getReaderId() != null) {
            dto.setReaderName(borrowing.getReaderId().getNameReader());
            dto.setPhone(borrowing.getReaderId().getPhone());
        } else {
            // Fallback if reader relation is missing, though unlikely given DB constraints
            dto.setReaderName(borrowing.getIdentityCard());
            dto.setPhone(borrowing.getIdentityCard());
        }

        if (borrowing.getBookId() != null) {
            dto.setBookName(borrowing.getBookId().getNameBook());
        } else {
            dto.setBookName(borrowing.getBookSeri());
        }

        return dto;
    }
}

