package com.example.library_management.repository;

import com.example.library_management.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

    //customize query
    Optional<UsersEntity> findByName(String name);
    boolean existsByEmail(String email);
}
