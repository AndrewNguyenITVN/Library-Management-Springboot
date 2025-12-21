package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.dto.CategoryDTO;
import com.library.LibraryManagement.entity.Category;
import com.library.LibraryManagement.repository.CategoryRepository;
import com.library.LibraryManagement.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

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
    public List<CategoryDTO> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category: categoryList){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setNameCate(category.getNameCate());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}

