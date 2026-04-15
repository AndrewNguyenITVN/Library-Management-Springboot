package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.AuthResponse;
import com.library.LibraryManagement.dto.LoginRequest;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.LoginService;
import com.library.LibraryManagement.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseData responseData = new ResponseData();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String role = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("")
                    .replace("ROLE_", "");

            String accessToken = jwtUtils.generateAccessToken(loginRequest.getUsername(), role);
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());

            loginService.saveRefreshToken(loginRequest.getUsername(), refreshToken);

            AuthResponse authResponse = new AuthResponse(
                    accessToken, refreshToken, loginRequest.getUsername(), role
            );
            responseData.setData(authResponse);
            responseData.setSuccess(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setData("Login failed: " + e.getMessage());
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        ResponseData responseData = new ResponseData();
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null || refreshToken.isBlank()) {
            responseData.setData("refreshToken is required");
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }
        try {
            AuthResponse authResponse = loginService.refreshAccessToken(refreshToken);
            responseData.setData(authResponse);
            responseData.setSuccess(true);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (Exception e) {
            responseData.setData(e.getMessage());
            responseData.setSuccess(false);
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> request) {
        ResponseData responseData = new ResponseData();
        String refreshToken = request.get("refreshToken");
        if (refreshToken != null && !refreshToken.isBlank()) {
            loginService.logout(refreshToken);
        }
        responseData.setData("Logged out successfully");
        responseData.setSuccess(true);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        ResponseData responseData = new ResponseData();
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");
        String role = request.get("role");

        if (loginService.createUser(username, password, email, role)) {
            responseData.setData("User created successfully");
            responseData.setSuccess(true);
        } else {
            responseData.setData("Failed to create user");
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
