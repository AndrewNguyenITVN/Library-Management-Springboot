package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.FinePaymentDTO;
import com.library.LibraryManagement.entity.FinePayment;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.FinePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/fine-payment")
public class FinePaymentController {
    @Autowired
    private FinePaymentService finePaymentService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPayments() {
        ResponseData responseData = new ResponseData();
        List<FinePaymentDTO> payments = finePaymentService.getAllPayments();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách thanh toán thành công");
        responseData.setData(payments);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Integer id) {
        ResponseData responseData = new ResponseData();
        FinePaymentDTO payment = finePaymentService.getPaymentById(id);
        if (payment != null) {
            responseData.setSuccess(true);
            responseData.setDesc("Lấy thông tin thanh toán thành công");
            responseData.setData(payment);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }
        responseData.setSuccess(false);
        responseData.setDesc("Không tìm thấy thanh toán với id = " + id);
        return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/borrowing/{borrowingId}")
    public ResponseEntity<?> getPaymentsByBorrowingId(@PathVariable Integer borrowingId) {
        ResponseData responseData = new ResponseData();
        List<FinePaymentDTO> payments = finePaymentService.getPaymentsByBorrowingId(borrowingId);
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách thanh toán theo lượt mượn thành công");
        responseData.setData(payments);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getPaymentsByStatus(@PathVariable FinePayment.PaymentStatus status) {
        ResponseData responseData = new ResponseData();
        List<FinePaymentDTO> payments = finePaymentService.getPaymentsByStatus(status);
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách thanh toán theo trạng thái thành công");
        responseData.setData(payments);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody FinePaymentDTO paymentDTO) {
        ResponseData responseData = new ResponseData();
        boolean success = finePaymentService.createPayment(paymentDTO);
        responseData.setSuccess(success);
        responseData.setDesc(success ? "Tạo thanh toán thành công" : "Tạo thanh toán thất bại");
        return new ResponseEntity<>(responseData, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePayment(@PathVariable Integer id, @RequestBody FinePaymentDTO paymentDTO) {
        ResponseData responseData = new ResponseData();
        boolean success = finePaymentService.updatePayment(id, paymentDTO);
        responseData.setSuccess(success);
        responseData.setDesc(success ? "Cập nhật thanh toán thành công" : "Cập nhật thanh toán thất bại");
        return new ResponseEntity<>(responseData, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<?> updatePaymentStatus(
            @PathVariable Integer id,
            @RequestParam FinePayment.PaymentStatus status) {
        ResponseData responseData = new ResponseData();
        boolean success = finePaymentService.updatePaymentStatus(id, status);
        responseData.setSuccess(success);
        responseData.setDesc(success ? "Cập nhật trạng thái thanh toán thành công" : "Cập nhật trạng thái thanh toán thất bại");
        return new ResponseEntity<>(responseData, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable Integer id) {
        ResponseData responseData = new ResponseData();
        boolean success = finePaymentService.deletePayment(id);
        responseData.setSuccess(success);
        responseData.setDesc(success ? "Xóa thanh toán thành công" : "Xóa thanh toán thất bại");
        return new ResponseEntity<>(responseData, success ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
