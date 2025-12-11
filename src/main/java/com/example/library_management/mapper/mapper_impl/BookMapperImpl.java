package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.entity.AuthorEntity;
import com.example.library_management.entity.BookEntity;
import com.example.library_management.entity.CategoryEntity;
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

        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthorId(entity.getAuthor().getId());
        dto.setAuthorName(entity.getAuthor().getName());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setCategoryName(entity.getCategory().getName());
        dto.setImagePath(entity.getImagePath()); // short URL string
        dto.setImageType(entity.getImageType());
        dto.setPublishDate(entity.getPublishDate());
        dto.setCreateAt(entity.getCreate());
        dto.setUpdateAt(entity.getUpdate());
        return dto;
    }
}