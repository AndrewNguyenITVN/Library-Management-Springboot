package com.library.LibraryManagement.service.imp;

import com.library.LibraryManagement.entity.Category;

import java.util.List;

public interface CategoryServiceImp {
    Boolean createCategory(String categoryName);
    Boolean updateCategory(int id, String category);
    List<Category> getAllCategories();
}
