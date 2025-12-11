package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.category.CategoryRequestDTO;
import com.example.library_management.dto.category.CategoryResponseDTO;
import com.example.library_management.entity.CategoryEntity;
import com.example.library_management.mapper.CategoryMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryEntity dtoToEntity(CategoryRequestDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getCategoryName());
        return entity;
    }

    @Override
    public CategoryResponseDTO entityToDto(CategoryEntity entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();

        dto.setId(entity.getId());
        dto.setCateName(entity.getName());
        return dto;
    }
}
