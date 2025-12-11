package com.example.library_management.mapper;

import com.example.library_management.dto.category.CategoryRequestDTO;
import com.example.library_management.dto.category.CategoryResponseDTO;
import com.example.library_management.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public interface CategoryMapper {

    CategoryEntity dtoToEntity(CategoryRequestDTO dto);
    CategoryResponseDTO entityToDto(CategoryEntity entity);

}
