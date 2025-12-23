package com.library.LibraryManagement.service;

import com.library.LibraryManagement.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    Boolean createCategory(String categoryName);
    Boolean updateCategory(int id, String category);
    List<CategoryDTO> getAllCategories();
}
