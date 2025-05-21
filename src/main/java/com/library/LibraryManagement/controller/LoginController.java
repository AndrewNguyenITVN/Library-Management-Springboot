package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
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


    @PostMapping("/add-user")
    public ResponseEntity<?> createUSer(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String role){
        ResponseData responseData = new ResponseData();

        if(loginServiceImp.createUser(username,password, email, role)){
            responseData.setData("");
            responseData.setSuccess(true);
        }else {
            responseData.setData("");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
