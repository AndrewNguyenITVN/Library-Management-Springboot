package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.entity.User.Role;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.LoginServiceImp;
import com.library.LibraryManagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImp loginServiceImp;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String username, @RequestParam String password) {
        ResponseData responseData = new ResponseData();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateToken(username);
            responseData.setData(token);
            responseData.setSuccess(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setData("Login failed: " + e.getMessage());
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String role) {
        ResponseData responseData = new ResponseData();

        if (loginServiceImp.createUser(username, password, email, role)) {
            responseData.setData("");
            responseData.setSuccess(true);
        } else {
            responseData.setData("");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
