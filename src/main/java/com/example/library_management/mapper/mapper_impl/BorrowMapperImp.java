package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.borrow.BorrowResponseDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.entity.BorrowEntity;
import com.example.library_management.mapper.BorrowMapper;
import org.springframework.stereotype.Component;

@Component
public class BorrowMapperImp implements BorrowMapper {
    @Override
    public BorrowEntity dtoToEntity(BorrowRequestDTO dto) {
        BorrowEntity entity = new BorrowEntity();

        entity.setDueDate(dto.getDueDate());
        return entity;
    }

    @Override
    public BorrowResponseDTO entityToDto(BorrowEntity entity) {
        BorrowResponseDTO dto = new BorrowResponseDTO();

        dto.setBorrowId(entity.getId());
        dto.setUser(new UserResponseDTO(
                entity.getUser().getName(),
                entity.getUser().getEmail()
        ));
        dto.setBook(new BookResponseDTO(
                entity.getBook().getTitle(),
                entity.getBook().getImagePath(),
                entity.getBook().getAvailable()
        ));
        dto.setBorrowDate(entity.getBorrowDate());
        dto.setReturnDate(entity.getDueDate());
        return dto;
    }
}
