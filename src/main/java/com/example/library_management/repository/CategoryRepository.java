package com.example.library_management.repository;

import com.example.library_management.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    //exists by name
    boolean existsByName(String name);

}
