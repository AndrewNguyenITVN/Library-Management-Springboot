package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, String password) {
        ResponseData responseData = new ResponseData();

        if(loginServiceImp.checkLogin(username, password)){
//            String token = jwtUtils.generateToken(username);
//            responseData.setData(token);
            responseData.setData("");
            responseData.setSuccess(true);
        }else {
            responseData.setData("");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }


}
