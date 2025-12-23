package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.UserDTO;

import java.util.List;

public interface LoginService {
    List<UserDTO> getAllUser();
    Boolean checkLogin(String usename, String password);
    Boolean createUser(String usename, String password, String email, String role);
}
