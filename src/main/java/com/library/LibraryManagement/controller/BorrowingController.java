package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BorrowBookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowBookServiceImp borrowBookServiceImp;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam int readerId,
                                        @RequestParam int bookId) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.borrowBook(readerId, bookId);
            resp.setSuccess(true);
            resp.setData(isSuccess);
            resp.setDesc("Mượn sách thành công");
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setDesc("Mượn sách thất bại: " + e.getMessage());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PutMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam int borrowingId) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.returnBook(borrowingId);
            resp.setSuccess(true);
            resp.setData(isSuccess);
            resp.setDesc("Trả sách thành công");
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setDesc("Trả sách thất bại: " + e.getMessage());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
