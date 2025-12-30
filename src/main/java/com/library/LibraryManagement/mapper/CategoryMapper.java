package com.library.LibraryManagement.mapper;

import com.library.LibraryManagement.dto.CategoryDTO;
import com.library.LibraryManagement.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setNameCate(category.getNameCate());
        return categoryDTO;
    }
}

