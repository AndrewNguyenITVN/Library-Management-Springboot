package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public final class BookController {
    @Autowired
    BookServiceImp bookServiceImp;

    @GetMapping("/show-listof-all-books")
    public ResponseEntity<?> showListOfBooks() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách sách thành công");
        responseData.setData(bookServiceImp.getAllBook());
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }
}
