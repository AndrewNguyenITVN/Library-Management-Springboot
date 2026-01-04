package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.ReaderDTO;
import com.library.LibraryManagement.entity.Reader.CardType;
import com.library.LibraryManagement.entity.Reader.ReaderStatus;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.ReaderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reader")
@Validated
public class ReaderController {
    @Autowired
    ReaderService readerService;

    @PostMapping("/insert-reader")
    public ResponseEntity<?> insertReader(@Valid @RequestBody ReaderDTO readerDTO) {
        ResponseData responseData = new ResponseData();
        // Convert Date objects to Strings for service compatibility if needed, or update service to accept DTO/Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dob = readerDTO.getDateOfBirth() != null ? sdf.format(readerDTO.getDateOfBirth()) : null;
        String expiry = readerDTO.getCardExpiryDate() != null ? sdf.format(readerDTO.getCardExpiryDate()) : null;
        
        boolean success = readerService.insertReader(readerDTO.getNameReader(), readerDTO.getIdentityCard(), 
                readerDTO.getPhone(), readerDTO.getEmail(), readerDTO.getAddress(),
                dob, readerDTO.getCardType(), expiry);
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
                                        @Valid @RequestBody ReaderDTO readerDTO) {
        ResponseData responseData = new ResponseData();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dob = readerDTO.getDateOfBirth() != null ? sdf.format(readerDTO.getDateOfBirth()) : null;
        String expiry = readerDTO.getCardExpiryDate() != null ? sdf.format(readerDTO.getCardExpiryDate()) : null;

        boolean success = readerService.updateReader(id, readerDTO.getNameReader(), readerDTO.getPhone(), 
                readerDTO.getEmail(), readerDTO.getAddress(),
                dob, readerDTO.getCardType(), expiry, readerDTO.getStatus());
        responseData.setSuccess(success);
        responseData.setData(success);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
