package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.AuthResponse;
import com.library.LibraryManagement.dto.UserDTO;

import java.util.List;

public interface LoginService {
    List<UserDTO> getAllUser();
    Boolean checkLogin(String username, String password);
    Boolean createUser(String username, String password, String email, String role);
    void saveRefreshToken(String username, String refreshToken);
    AuthResponse refreshAccessToken(String refreshToken);
    void logout(String refreshToken);
}
