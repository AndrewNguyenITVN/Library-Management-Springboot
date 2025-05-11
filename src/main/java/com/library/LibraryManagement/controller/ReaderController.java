package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.ReaderServiceImp;
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
    ReaderServiceImp readerServiceImp;

    @PostMapping("/insert-reader")
    public ResponseEntity<?> insertReader(@RequestParam String nameReader,
                                                        @RequestParam String identityCard,
                                                        @RequestParam String phone) {
        ResponseData responseData = new ResponseData();
        boolean success = readerServiceImp.insertReader(nameReader, identityCard, phone);
        responseData.setSuccess(true);

        responseData.setData(success);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllReaders() {
        ResponseData responseData = new ResponseData();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh nguoi doc thành công");
        responseData.setData(readerServiceImp.getAllReaders());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchReader(@RequestParam String nameReader) {
        ResponseData responseData = new ResponseData();
        responseData.setData(readerServiceImp.searchReaderByName(nameReader));
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh nguoi doc thành công");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/search-by-idcard")
    public ResponseEntity<?> searchByIdentityCard(@RequestParam String identityCard) {
        ResponseData responseData = new ResponseData();
        responseData.setData(readerServiceImp.searchReaderByName(identityCard));
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh nguoi doc thành công");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}

