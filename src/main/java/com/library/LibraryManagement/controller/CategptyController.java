package com.library.LibraryManagement.controller;

import com.library.LibraryManagement.dto.CategoryDTO;
import com.library.LibraryManagement.payload.ResponseData;
import com.library.LibraryManagement.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
@Validated
public class CategptyController {
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        ResponseData responseData = new ResponseData();
        boolean success = categoryService.createCategory(categoryDTO.getNameCate());
        responseData.setSuccess(success);
        responseData.setDesc(success
                ? "Thêm danh mục thành công"
                : "Thêm danh mục thất bại");
        responseData.setData(success);
        return new ResponseEntity<>(responseData, success ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable int id,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        ResponseData responseData = new ResponseData();
        boolean success = categoryService.updateCategory(id, categoryDTO.getNameCate());
        responseData.setSuccess(success);
        responseData.setDesc(success
                ? "Cập nhật danh mục thành công"
                : "Không tìm thấy danh mục với id = " + id);
        responseData.setData(success);
        return new ResponseEntity<>(responseData,
                success ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        ResponseData responseData = new ResponseData();
        List<CategoryDTO> list = categoryService.getAllCategories();
        responseData.setSuccess(true);
        responseData.setDesc("Lấy danh sách danh mục thành công");
        responseData.setData(list);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
