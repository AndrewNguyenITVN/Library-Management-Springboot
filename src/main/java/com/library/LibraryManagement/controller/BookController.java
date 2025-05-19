package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BookServiceImp;
import com.library.LibraryManagement.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/book")
public final class BookController {
    @Autowired
    FileServiceImp fileServiceImp;

    @Autowired
    BookServiceImp bookServiceImp;

    @GetMapping("/show-all-books")
    public ResponseEntity<?> showListOfBooks() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách sách thành công");
        responseData.setData(bookServiceImp.getAllBook());
        return new ResponseEntity<>(responseData, HttpStatus.OK);

    }

    @PostMapping("/add-book")
    public ResponseEntity<?> addBooks(@RequestParam MultipartFile file, String bookSeri,  String nameBook, int categoryId, int stockQuantity)
    {

        ResponseData responseData = new ResponseData();
        boolean isSuccess = bookServiceImp.addBook(file, bookSeri, nameBook, categoryId, stockQuantity);
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

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("bookSeri") String bookSeri,
                                        @RequestParam("nameBook") String nameBook,
                                        @RequestParam("stockQuantity") int stockQuantity,
                                        @RequestParam("categoryId") int categoryId) {
        boolean isSuccess = bookServiceImp.editBook(id, file, bookSeri, nameBook, stockQuantity, categoryId);
        if (isSuccess) {
            return ResponseEntity.ok("Cập nhật sách thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thất bại.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam String bookName) {
        ResponseData responseData = new ResponseData();
        try {
            List<BookDTO> result = bookServiceImp.searchBook(bookName);
            responseData.setData(result);
            responseData.setSuccess(true);
        }catch (Exception e) {
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search-seri")
    public ResponseEntity<?> searchBookByBookSeri(@RequestParam String bookSeri) {
        ResponseData responseData = new ResponseData();
        try {
            BookDTO result = bookServiceImp.searchBookByBookSeri(bookSeri);
            responseData.setData(result);
            responseData.setSuccess(true);
        }catch (Exception e) {
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
