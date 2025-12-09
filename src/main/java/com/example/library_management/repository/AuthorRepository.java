package com.example.library_management.repository;

import com.example.library_management.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository <AuthorEntity, Integer> {

    //check exists name
    boolean existsByName(String name);

}
