package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader;

import java.util.List;

public interface ReaderServiceImp {
    boolean insertReader(String fullName, String idCard, String phone, String email, String address,
                         String dateOfBirth, Reader.CardType cardType, String cardExpiryDate);
    boolean updateReader(int id, String nameReader, String phone, String email, String address,
                         String dateOfBirth, Reader.CardType cardType, String cardExpiryDate, Reader.ReaderStatus status);
    List<ReaderDTO> getAllReaders();
    List<ReaderDTO> searchReaderByName(String name);
    ReaderDTO searchReaderByIdentityCard(String identityCard);
}
