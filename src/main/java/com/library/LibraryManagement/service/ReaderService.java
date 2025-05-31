package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.entity.Reader.CardType;
import com.library.LibraryManagement.entity.Reader.ReaderStatus;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.imp.ReaderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReaderService implements ReaderServiceImp {
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public boolean insertReader(String fullName, String idCard, String phone, String email, String address,
                              String dateOfBirth, CardType cardType, String cardExpiryDate) {
        boolean isInsertSuccess = false;
        try {
            if (readerRepository.existsByIdentityCard(idCard)) {
                System.out.println("ID card already exists");
                return false;
            }

            Reader reader = new Reader();
            reader.setNameReader(fullName);
            reader.setIdentityCard(idCard);
            reader.setPhone(phone);
            reader.setEmail(email);
            reader.setAddress(address);
            
            if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                reader.setDateOfBirth(sdf.parse(dateOfBirth));
            }
            
            reader.setCardType(cardType != null ? cardType : CardType.OTHER);
            
            if (cardExpiryDate != null && !cardExpiryDate.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                reader.setCardExpiryDate(sdf.parse(cardExpiryDate));
            }

            readerRepository.save(reader);
            isInsertSuccess = true;
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm độc giả: " + e.getMessage());
        }
        return isInsertSuccess;
    }

    @Override
    public List<ReaderDTO> getAllReaders() {
        List<Reader> readerList = readerRepository.findAll();
        List<ReaderDTO> readerDTOList = new ArrayList<>();

        for (Reader reader : readerList) {
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
            readerDTOList.add(dto);
        }

        return readerDTOList;
    }

    @Override
    public List<ReaderDTO> searchReaderByName(String name) {
        List<Reader> readers = readerRepository.findByNameReaderContainingIgnoreCase(name);
        List<ReaderDTO> result = new ArrayList<>();

        for (Reader reader : readers) {
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
            result.add(dto);
        }

        return result;
    }

    @Override
    public ReaderDTO searchReaderByIdentityCard(String identityCard) {
        Reader reader = readerRepository.findByIdentityCard(identityCard);
        ReaderDTO result = new ReaderDTO();
        result.setId(reader.getId());
        result.setNameReader(reader.getNameReader());
        result.setIdentityCard(reader.getIdentityCard());
        result.setPhone(reader.getPhone());
        result.setEmail(reader.getEmail());
        result.setAddress(reader.getAddress());
        result.setDateOfBirth(reader.getDateOfBirth());
        result.setCardType(reader.getCardType());
        result.setCardExpiryDate(reader.getCardExpiryDate());
        result.setTotalBorrowed(reader.getTotalBorrowed());
        result.setTotalOverdue(reader.getTotalOverdue());
        result.setStatus(reader.getStatus());
        result.setCreatedAt(reader.getCreatedAt());
        return result;
    }

    @Override
    public boolean updateReader(int id, String nameReader, String phone, String email, String address,
                              String dateOfBirth, CardType cardType, String cardExpiryDate, ReaderStatus status) {
        try {
            Reader reader = readerRepository.findById(id).orElse(null);
            if (reader != null) {
                if (nameReader != null) reader.setNameReader(nameReader);
                if (phone != null) reader.setPhone(phone);
                if (email != null) reader.setEmail(email);
                if (address != null) reader.setAddress(address);
                if (dateOfBirth != null && !dateOfBirth.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    reader.setDateOfBirth(sdf.parse(dateOfBirth));
                }
                if (cardType != null) reader.setCardType(cardType);
                if (cardExpiryDate != null && !cardExpiryDate.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    reader.setCardExpiryDate(sdf.parse(cardExpiryDate));
                }
                if (status != null) reader.setStatus(status);
                
                readerRepository.save(reader);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật độc giả: " + e.getMessage());
        }
        return false;
    }

}
