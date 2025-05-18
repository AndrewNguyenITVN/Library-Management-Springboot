package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.ReaderDTO;

import java.util.List;

public interface ReaderServiceImp {
    boolean insertReader(String fullName, String idCard, String phone);
    List<ReaderDTO> getAllReaders();
    List<ReaderDTO> searchReaderByName(String name);
    ReaderDTO searchReaderByIdentityCard(String identityCard);

}
