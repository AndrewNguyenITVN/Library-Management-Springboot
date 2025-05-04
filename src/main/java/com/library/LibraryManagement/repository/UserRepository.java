package com.library.LibraryManagement.repository;

import com.library.LibraryManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByusername(String username);
}
