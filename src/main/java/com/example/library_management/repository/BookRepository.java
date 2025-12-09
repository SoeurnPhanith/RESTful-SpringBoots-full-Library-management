package com.example.library_management.repository;

import com.example.library_management.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository <BookEntity, Integer> {

    boolean existsByTitleAndCategoryId(String title, Integer id);

}
