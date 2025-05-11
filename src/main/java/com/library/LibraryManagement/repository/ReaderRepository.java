package com.library.LibraryManagement.repository;

import com.library.LibraryManagement.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    boolean existsByEmail(String email);
    List<Reader> getAllReaders();
    List<Reader> findByNameReader(String nameReader);
    List<Reader> findByIdentityCard(String identityCard);

}
