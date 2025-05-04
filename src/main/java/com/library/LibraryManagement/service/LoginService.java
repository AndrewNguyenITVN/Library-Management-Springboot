package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.UserDTO;
import com.library.LibraryManagement.entity.User;
import com.library.LibraryManagement.repository.UserRepository;
import com.library.LibraryManagement.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserDTO> getAllUser() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (User user: listUser){
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setPassword(user.getPassword());
            userDTOList.add(userDTO);
        }
        return userDTOList;

    }

    @Override
    public Boolean checkLogin(String username, String password) {
        User user = userRepository.findByusername(username);
        return  password.equals(user.getPassword());
    }
}


