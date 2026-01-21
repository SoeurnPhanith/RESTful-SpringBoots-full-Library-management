package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.entity.BookEntity;
import com.example.library_management.mapper.BookMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper{

    @Override
    public BookEntity dtoToEntity(BookRequestDTO dto) {
       BookEntity entity = new BookEntity();

       entity.setTitle(dto.getTitle());
       entity.setPublishDate(dto.getPublishDate());
       return entity;
    }

    @Override
    public BookResponseDTO entityToDto(BookEntity entity) {
        BookResponseDTO dto = new BookResponseDTO();
        String imageUrl = "http://localhost:8888" + entity.getImagePath();

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorName(entity.getAuthor().getName());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setCategoryName(entity.getCategory().getName());
        dto.setImagePath(imageUrl); // short URL string
        dto.setPublishDate(entity.getPublishDate());
        dto.setCreateAt(entity.getCreate());
        dto.setUpdateAt(entity.getUpdate());
        return dto;
    }
}