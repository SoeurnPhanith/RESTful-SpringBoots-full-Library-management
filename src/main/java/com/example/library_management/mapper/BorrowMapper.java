package com.example.library_management.mapper;

import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.borrow.BorrowResponseDTO;
import com.example.library_management.entity.BorrowEntity;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public interface BorrowMapper {

    BorrowEntity dtoToEntity (BorrowRequestDTO dto);
    BorrowResponseDTO entityToDto(BorrowEntity entity);

}
