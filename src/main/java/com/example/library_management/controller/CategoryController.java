package com.example.library_management.controller;

import com.example.library_management.dto.category.CategoryRequestDTO;
import com.example.library_management.dto.category.CategoryResponseDTO;
import com.example.library_management.service.service_implementation.CategoryServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping (UtilEndPoint.Route + "/category")
public class CategoryController {

    //inject data from ServiceImpl
    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<APIRespone<CategoryResponseDTO>> addCategory(@RequestBody @Valid CategoryRequestDTO category){
        return categoryService.addCategory(category);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<CategoryResponseDTO>>> getAllCategory(){
        return categoryService.showAllCategory();
    }

    @GetMapping (path = "/{categoryId}")
    public ResponseEntity<APIRespone<CategoryResponseDTO>> getCategoryById(
            @PathVariable @Valid Integer categoryId
    ){
        return categoryService.showCategoryById(categoryId);
    }

    @PutMapping (path = "/{categoryId}")
    public ResponseEntity<APIRespone<CategoryResponseDTO>> updateCategory(
            @PathVariable @Valid Integer categoryId,
            @RequestBody @Valid CategoryRequestDTO categoryRequestDTO
    ){
        return categoryService.updateCategory(categoryId,categoryRequestDTO);
    }

    @DeleteMapping (path = "/{categoryId}")
    public ResponseEntity<APIRespone<String>> removeCategory(
            @PathVariable @Valid Integer categoryId
    ){
        return categoryService.removeCategory(categoryId);
    }
}
