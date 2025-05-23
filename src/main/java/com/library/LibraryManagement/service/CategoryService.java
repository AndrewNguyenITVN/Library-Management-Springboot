package com.library.LibraryManagement.service;

import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.repository.CategoryRepository;
import com.library.LibraryManagement.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceImp {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Boolean createCategory(String categoryName) {
        Category c = new Category();
        c.setNameCate(categoryName);
        categoryRepository.save(c);
        return true;
    }

    @Override
    public Boolean updateCategory(int id, String categoryName) {
        Optional<Category> opt = categoryRepository.findById(id);
        if (opt.isEmpty()) {
            return false;
        }
        Category c = opt.get();
        c.setNameCate(categoryName);
        categoryRepository.save(c);
        return true;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
