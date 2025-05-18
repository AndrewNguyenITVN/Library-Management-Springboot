package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BorrowBookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowBookServiceImp borrowBookServiceImp;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam String identityCard,
                                        @RequestParam String bookSeri) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.borrowBook(identityCard, bookSeri);
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

//    @GetMapping("/all")
//    public ResponseEntity<?> getAll() {
//        ResponseData resp = new ResponseData();
//        List<BorrowingDTO> list = borrowBookServiceImp.getAllBorrowings();
//        resp.setSuccess(true);
//        resp.setData(list);
//        resp.setDesc("Danh sách mượn trả");
//        return new ResponseEntity<>(resp, HttpStatus.OK);
//    }

//    @GetMapping("/by-reader")
//    public ResponseEntity<?> getByReader(@RequestParam int readerId) {
//        ResponseData resp = new ResponseData();
//        List<BorrowingDTO> list = borrowBookServiceImp.getBorrowingsByReaderId(readerId);
//        resp.setSuccess(true);
//        resp.setData(list);
//        resp.setDesc("Danh sách mượn trả của độc giả");
//        return new ResponseEntity<>(resp, HttpStatus.OK);
//    }
}
