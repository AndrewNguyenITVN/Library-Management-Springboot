package com.library.LibraryManagement.mapper;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderMapper {

    public ReaderDTO toDTO(Reader reader) {
        if (reader == null) {
            return null;
        }

        ReaderDTO dto = new ReaderDTO();
        dto.setId(reader.getId());
        dto.setNameReader(reader.getNameReader());
        dto.setIdentityCard(reader.getIdentityCard());
        dto.setPhone(reader.getPhone());
        dto.setEmail(reader.getEmail());
        dto.setAddress(reader.getAddress());
        dto.setDateOfBirth(reader.getDateOfBirth());
        dto.setCardType(reader.getCardType());
        dto.setCardExpiryDate(reader.getCardExpiryDate());
        dto.setTotalBorrowed(reader.getTotalBorrowed());
        dto.setTotalOverdue(reader.getTotalOverdue());
        dto.setStatus(reader.getStatus());
        dto.setCreatedAt(reader.getCreatedAt());

        return dto;
    }
}

