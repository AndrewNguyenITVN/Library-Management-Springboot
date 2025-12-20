package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.UserDTO;
import com.library.LibraryManagement.entity.User;
import com.library.LibraryManagement.repository.UserRepository;
import com.library.LibraryManagement.service.LoginService;
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

    @Override
    public List<UserDTO> getAllUser() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user: listUser){
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
        return  passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public Boolean createUser(String usename, String password, String email, String role) {
        User user = new User();
        user.setUsername(usename);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        User.Role roleEnum = User.Role.valueOf(role);
        user.setRole(roleEnum);
        try {
            userRepository.save(user);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }


    }
}

