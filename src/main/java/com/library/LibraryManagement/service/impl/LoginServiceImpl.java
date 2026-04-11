package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.AuthResponse;
import com.library.LibraryManagement.dto.UserDTO;
import com.library.LibraryManagement.entity.User;
import com.library.LibraryManagement.repository.UserRepository;
import com.library.LibraryManagement.service.LoginService;
import com.library.LibraryManagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public List<UserDTO> getAllUser() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public Boolean checkLogin(String username, String password) {
        User user = userRepository.findByusername(username);
        if (user == null) return false;
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Boolean createUser(String username, String password, String email, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setRole(User.Role.valueOf(role));
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public void saveRefreshToken(String username, String refreshToken) {
        User user = userRepository.findByusername(username);
        if (user != null) {
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        if (!jwtUtils.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token");
        }

        String username = jwtUtils.getUsernameFromRefreshToken(refreshToken);
        User user = userRepository.findByusername(username);

        if (user == null || !refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        String newAccessToken = jwtUtils.generateAccessToken(username, user.getRole().name());
        return new AuthResponse(newAccessToken, refreshToken, username, user.getRole().name());
    }

    @Override
    public void logout(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken);
        if (user != null) {
            user.setRefreshToken(null);
            userRepository.save(user);
        }
    }
}
