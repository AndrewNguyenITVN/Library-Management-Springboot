package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.BorrowingDTO;
import com.library.LibraryManagement.entity.Borrowing.DamageStatus;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.imp.BorrowBookServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/borrowing")
public class BorrowingController {
    @Autowired
    private BorrowBookServiceImp borrowBookServiceImp;

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam String identityCard,
                                      @RequestParam String bookSeri,
                                      @RequestParam(required = false) String notes) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.borrowBook(identityCard, bookSeri, notes);
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
    public ResponseEntity<?> returnBook(@RequestParam int borrowingId,
                                      @RequestParam(required = false) DamageStatus damageStatus,
                                      @RequestParam(required = false) BigDecimal damageFine,
                                      @RequestParam(required = false) String notes) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.returnBook(borrowingId, damageStatus, damageFine, notes);
            resp.setSuccess(true);
            resp.setData(isSuccess);
            resp.setDesc("Trả sách thành công");
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setDesc("Trả sách thất bại: " + e.getMessage());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        ResponseData resp = new ResponseData();
        List<BorrowingDTO> list = borrowBookServiceImp.getAllBorrowings();
        resp.setSuccess(true);
        resp.setData(list);
        resp.setDesc("Danh sách mượn trả");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/by-reader")
    public ResponseEntity<?> getBorrowingsByIdentityCard(@RequestParam String identityCard) {
        ResponseData resp = new ResponseData();
        List<BorrowingDTO> list = borrowBookServiceImp.getBorrowingsByIdentityCard(identityCard);
        resp.setSuccess(true);
        resp.setData(list);
        resp.setDesc("Danh sách mượn trả của độc giả");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueBorrowings() {
        ResponseData resp = new ResponseData();
        List<BorrowingDTO> list = borrowBookServiceImp.getOverdueBorrowings();
        resp.setSuccess(true);
        resp.setData(list);
        resp.setDesc("Danh sách mượn quá hạn");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<ResponseData> getBorrowingStats(
            @RequestParam String type,
            @RequestParam int year,
            @RequestParam(required = false) Integer week,
            @RequestParam(required = false) Integer month) {

        if ("week".equalsIgnoreCase(type) && week == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Missing 'week' when type=week");
        }
        if ("month".equalsIgnoreCase(type) && month == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Missing 'month' when type=month");
        }

        long count;
        List<BorrowingDTO> list;
        if ("week".equalsIgnoreCase(type)) {
            count = borrowBookServiceImp.countBorrowingsInWeek(year, week);
            list = borrowBookServiceImp.getBorrowingsInWeek(year, week);
        } else if ("month".equalsIgnoreCase(type)) {
            count = borrowBookServiceImp.countBorrowingsInMonth(year, month);
            list = borrowBookServiceImp.getBorrowingsInMonth(year, month);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid type, must be 'week' or 'month'");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("type", type.toLowerCase());
        data.put("year", year);
        if ("week".equalsIgnoreCase(type)) data.put("week", week);
        else data.put("month", month);
        data.put("count", count);
        data.put("borrowings", list);

        ResponseData resp = new ResponseData();
        resp.setSuccess(true);
        resp.setDesc("Thống kê số lượt mượn và danh sách chi tiết cho " + type);
        resp.setData(data);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/pay-fine")
    public ResponseEntity<?> payFine(@RequestParam int borrowingId,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam(required = false) String notes) {
        ResponseData resp = new ResponseData();
        try {
            Boolean isSuccess = borrowBookServiceImp.payFine(borrowingId, amount, notes);
            resp.setSuccess(true);
            resp.setData(isSuccess);
            resp.setDesc("Thanh toán tiền phạt thành công");
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setDesc("Thanh toán tiền phạt thất bại: " + e.getMessage());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
