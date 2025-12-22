package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader.CardType;
import com.library.LibraryManagement.entity.Reader.ReaderStatus;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    ReaderService readerService;

    @PostMapping("/insert-reader")
    public ResponseEntity<?> insertReader(@RequestParam String nameReader,
                                        @RequestParam String identityCard,
                                        @RequestParam String phone,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String address,
                                        @RequestParam(required = false) String dateOfBirth,
                                        @RequestParam(required = false) CardType cardType,
                                        @RequestParam(required = false) String cardExpiryDate) {
        ResponseData responseData = new ResponseData();
        boolean success = readerService.insertReader(nameReader, identityCard, phone, email, address,
                dateOfBirth, cardType, cardExpiryDate);
        responseData.setSuccess(true);
        responseData.setData(success);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllReaders() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách người đọc thành công");
        responseData.setData(readerService.getAllReaders());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchReader(@RequestParam String nameReader) {
        ResponseData responseData = new ResponseData();
        responseData.setData(readerService.searchReaderByName(nameReader));
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách người đọc thành công");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search-by-idcard")
    public ResponseEntity<?> searchByIdentityCard(@RequestParam String identityCard) {
        ResponseData responseData = new ResponseData();
        responseData.setData(readerService.searchReaderByIdentityCard(identityCard));
        responseData.setSuccess(true);
        responseData.setDesc("Tìm danh sách người đọc thành công");
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReader(@PathVariable int id,
                                        @RequestParam(required = false) String nameReader,
                                        @RequestParam(required = false) String phone,
                                        @RequestParam(required = false) String email,
                                        @RequestParam(required = false) String address,
                                        @RequestParam(required = false) String dateOfBirth,
                                        @RequestParam(required = false) CardType cardType,
                                        @RequestParam(required = false) String cardExpiryDate,
                                        @RequestParam(required = false) ReaderStatus status) {
        ResponseData responseData = new ResponseData();
        boolean success = readerService.updateReader(id, nameReader, phone, email, address,
                dateOfBirth, cardType, cardExpiryDate, status);
        responseData.setSuccess(success);
        responseData.setData(success);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
