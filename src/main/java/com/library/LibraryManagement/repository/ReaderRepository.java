package com.library.LibraryManagement.repository;

import com.library.LibraryManagement.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    boolean existsByIdentityCard(String identityCard);
    //List<Reader> getAllReaders();
    List<Reader> findByNameReaderContainingIgnoreCase(String nameReader);
    Reader findByIdentityCard(String identityCard);

}
