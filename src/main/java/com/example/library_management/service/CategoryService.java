package com.example.library_management.service;

import com.example.library_management.dto.category.CategoryRequestDTO;
import com.example.library_management.dto.category.CategoryResponseDTO;
import com.example.library_management.entity.CategoryEntity;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    @NonNull
    ResponseEntity<APIRespone<CategoryResponseDTO>> addCategory(CategoryRequestDTO category);

    @NonNull
    ResponseEntity<APIRespone<List<CategoryResponseDTO>>>  showAllCategory();

    @NonNull
    ResponseEntity<APIRespone<CategoryResponseDTO>> showCategoryById(Integer id);

    @NonNull
    ResponseEntity<APIRespone<CategoryResponseDTO>> updateCategory(Integer id, CategoryRequestDTO category);

    @NonNull
    ResponseEntity<APIRespone<String>> removeCategory(Integer id);

}
