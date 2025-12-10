package com.example.library_management.repository;

import com.example.library_management.entity.BorrowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRepository extends JpaRepository<BorrowEntity, Integer> {

    boolean existsByBookId(Integer bookId);

}
