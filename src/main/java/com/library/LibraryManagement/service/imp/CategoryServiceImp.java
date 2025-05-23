package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.dto.CategoryDTO;

import java.util.List;

public interface CategoryServiceImp {
    Boolean createCategory(String categoryName);
    Boolean updateCategory(int id, String category);
    List<CategoryDTO> getAllCategories();
}
