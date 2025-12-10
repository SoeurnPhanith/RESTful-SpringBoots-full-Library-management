package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.category.CategoryRequestDTO;
import com.example.library_management.dto.category.CategoryResponseDTO;
import com.example.library_management.entity.CategoryEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.repository.CategoryRepository;
import com.example.library_management.service.CategoryService;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    //inject data from
    @Autowired
    private CategoryRepository categoryRepository;

    //inject from ResponseDTO
    @Override
    public @NonNull ResponseEntity<APIRespone<CategoryResponseDTO>> addCategory(CategoryRequestDTO category) {
        try{
            //check existCategory
            boolean isExist = categoryRepository.existsByName(category.getCategoryName());
            if(isExist){
                throw new DuplicateDataException("This category name already exists");
            }

            //map data from requestDTO to Entity
            CategoryEntity catEntity = new CategoryEntity();
            catEntity.setName(category.getCategoryName());

            //save to repository
            CategoryEntity saved = categoryRepository.save(catEntity);

            //map data from Entity to responeDTO
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setCateName(saved.getName());
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "category saved success",
                    responseDTO
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<List<CategoryResponseDTO>>> showAllCategory() {
        try{
            //get all data from database(Entity)
            List<CategoryEntity> allCategory = categoryRepository.findAll();

            //check empty or not
            if(allCategory.isEmpty()){
                throw new ResourceNotFoundException("No data record");
            }
            //sent all data from entity to category responseDTO
            List<CategoryResponseDTO> dtoList = new ArrayList<>();
            for(CategoryEntity entity : allCategory){
                CategoryResponseDTO responseDTO = new CategoryResponseDTO();
                responseDTO.setCateName(entity.getName());

                //add to dtoList
                dtoList.add(responseDTO);
            }
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "get all category success",
                    dtoList
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<CategoryResponseDTO>> showCategoryById(Integer id) {
        try{
            //get data from database by id
            Optional<CategoryEntity> getEntity = categoryRepository.findById(id);

            //check having or not
            if(!getEntity.isPresent()){
               throw new ResourceNotFoundException("This category id not found");
            }

            //get data from entity to response DTO
            CategoryEntity entity = getEntity.get();

            //map data from entity to ---> response DTO
            CategoryResponseDTO responseDTO = new CategoryResponseDTO();
            responseDTO.setCateName(entity.getName());
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "get data success",
                    responseDTO
            ));
        }catch (Exception e){
            throw new GenericException(e.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<CategoryResponseDTO>> updateCategory(Integer id, CategoryRequestDTO category) {
         try{
                //get data from database entity
                Optional<CategoryEntity> getEntity = categoryRepository.findById(id);
                if(!getEntity.isPresent()){
                    throw new ResourceNotFoundException("This category id not found");
                }

                //get it if found
                CategoryEntity update = getEntity.get();
                update.setName(category.getCategoryName());

                //save to repository
                CategoryEntity saved = categoryRepository.save(update);

                //map data from entity to responseDTO
                CategoryResponseDTO responseDTO = new CategoryResponseDTO();
                responseDTO.setCateName(saved.getName());
                return ResponseEntity.ok(new APIRespone<>(
                        true,
                        "update data success!",
                        responseDTO
                ));
         }catch (Exception e){
             throw new GenericException(e.getMessage());
         }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<String>> removeCategory(Integer id) {
        try{
            //find data in database
            Optional<CategoryEntity> findData = categoryRepository.findById(id);
            if(!findData.isPresent()){
                throw new ResourceNotFoundException("Not found this category id");
            }

            //remove from database
            categoryRepository.deleteById(id);
            return ResponseEntity.ok(
                    new APIRespone<>(
                        true,
                        "remove this category success",
                        "delete already"
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
