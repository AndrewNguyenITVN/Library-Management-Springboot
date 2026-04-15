package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.BookDTO;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.BookService;
import com.library.LibraryManagement.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
@Validated
public class BookController {
    @Autowired
    FileService fileService;

    @Autowired
    BookService bookService;

    @GetMapping
    public ResponseEntity<?> showListOfBooks() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách sách thành công");
        responseData.setData(bookService.getAllBook());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addBooks(@RequestParam MultipartFile file,
                                    @Valid @ModelAttribute BookDTO bookDTO) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = bookService.addBook(file, bookDTO.getBookSeri(), bookDTO.getNameBook(), 
                bookDTO.getCategoryId(), bookDTO.getStockQuantity(),
                bookDTO.getAuthor(), bookDTO.getPublisher(), bookDTO.getPublishYear(), 
                bookDTO.getIsbn(), bookDTO.getDescription(), bookDTO.getLanguage(), 
                bookDTO.getEdition(), bookDTO.getPageCount());
        responseData.setData(isSuccess);
        responseData.setSuccess(isSuccess);
        return new ResponseEntity<>(responseData, isSuccess ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookFromList(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        boolean isSuccess = bookService.delBook(id);
        responseData.setSuccess(isSuccess);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, isSuccess ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable int id,
                                      @RequestParam("file") MultipartFile file,
                                      @Valid @ModelAttribute BookDTO bookDTO) {
        boolean isSuccess = bookService.editBook(id, file, bookDTO.getBookSeri(), bookDTO.getNameBook(), 
                bookDTO.getStockQuantity(), bookDTO.getCategoryId(),
                bookDTO.getAuthor(), bookDTO.getPublisher(), bookDTO.getPublishYear(), 
                bookDTO.getIsbn(), bookDTO.getDescription(), bookDTO.getLanguage(), 
                bookDTO.getEdition(), bookDTO.getPageCount());
        
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(isSuccess);
        responseData.setData(isSuccess);
        return new ResponseEntity<>(responseData, isSuccess ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam String bookName) {
        ResponseData responseData = new ResponseData();
        try {
            List<BookDTO> result = bookService.searchBook(bookName);
            responseData.setData(result);
            responseData.setSuccess(true);
        } catch (Exception e) {
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search-by-seri")
    public ResponseEntity<?> searchBookByBookSeri(@RequestParam String bookSeri) {
        ResponseData responseData = new ResponseData();
        try {
            BookDTO result = bookService.searchBookByBookSeri(bookSeri);
            responseData.setData(result);
            responseData.setSuccess(true);
        } catch (Exception e) {
            responseData.setSuccess(false);
        }
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
