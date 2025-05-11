package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader;
import com.library.LibraryManagement.repository.ReaderRepository;
import com.library.LibraryManagement.service.imp.ReaderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService implements ReaderServiceImp {
    @Autowired
    private ReaderRepository readerRepository;

    @Override
    public boolean insertReader(String fullName, String idCard, String phone) {
        boolean isInsertSuccess = false;
        try {
            // Kiểm tra email đã tồn tại
            if (readerRepository.existsByEmail(idCard)) {
                System.out.println("ID card already exists");
                return false;
            }

            Reader reader = new Reader();
            reader.setNameReader(fullName);
            reader.setIdentityCard(idCard);
            reader.setPhone(phone);

            readerRepository.save(reader);
            isInsertSuccess = true;
        } catch (Exception e) {
            System.out.println("Lỗi khi insert reader: " + e.getMessage());
        }
        return isInsertSuccess;
    }

    @Override
    public List<ReaderDTO> getAllReaders() {
        List<Reader> readerList = readerRepository.findAll();
        List<ReaderDTO> readerDTOList = new ArrayList<>();

        for (Reader reader : readerList) {
            ReaderDTO dto = new ReaderDTO();
            dto.setNameReader(reader.getNameReader());
            dto.setIdentityCard(reader.getIdentityCard());
            dto.setPhone(reader.getPhone());
            readerDTOList.add(dto);
        }

        return readerDTOList;
    }

    @Override
    public List<ReaderDTO> searchReaderByName(String name) {
        List<Reader> readers = readerRepository.findByNameReader(name);
        List<ReaderDTO> result = new ArrayList<>();

        for (Reader reader : readers) {
            ReaderDTO dto = new ReaderDTO();
            dto.setNameReader(reader.getNameReader());
            dto.setIdentityCard(reader.getIdentityCard());
            dto.setPhone(reader.getPhone());
            result.add(dto);
        }

        return result;
    }
}
