package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BookServiceImp;
import com.library.LibraryManagement.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/book")
public final class BookController {
    @Autowired
    FileServiceImp fileServiceImp;

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

    @PostMapping("/add-book")
    public ResponseEntity<?> addBooks(@RequestParam MultipartFile file, String nameBook, int categoryId, int stockQuantity)
    {

        ResponseData responseData = new ResponseData();
        boolean isSuccess = bookServiceImp.addBook(file, nameBook, categoryId, stockQuantity);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/delete-book")
    public ResponseEntity<?> deleteBookFromList(@RequestParam int id)
    {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = bookServiceImp.delBook(id);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
