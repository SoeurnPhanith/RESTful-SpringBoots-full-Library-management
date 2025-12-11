package com.example.library_management.mapper;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public interface BookMapper {

    BookEntity dtoToEntity(BookRequestDTO dto);
    BookResponseDTO entityToDto(BookEntity entity);

}
