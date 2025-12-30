package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.entity.Reader.CardType;
import com.library.LibraryManagement.entity.Reader.ReaderStatus;
import com.library.LibraryManagement.mapper.ReaderMapper;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderMapper readerMapper;

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
        return readerList.stream()
                .map(readerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReaderDTO> searchReaderByName(String name) {
        List<Reader> readers = readerRepository.findByNameReaderContainingIgnoreCase(name);
        return readers.stream()
                .map(readerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReaderDTO searchReaderByIdentityCard(String identityCard) {
        Reader reader = readerRepository.findByIdentityCard(identityCard);
        return readerMapper.toDTO(reader);
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
