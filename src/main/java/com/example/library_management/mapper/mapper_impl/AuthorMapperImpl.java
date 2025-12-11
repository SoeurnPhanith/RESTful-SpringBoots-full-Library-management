package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.author.AuthorRequestDTO;
import com.example.library_management.dto.author.AuthorResponeDTO;
import com.example.library_management.entity.AuthorEntity;
import com.example.library_management.mapper.AuthorMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements AuthorMapper {
    @Override
    public AuthorEntity dtoToEntity(AuthorRequestDTO dto) {
        AuthorEntity entity = new AuthorEntity();

        entity.setName(dto.getName());
        entity.setBio(dto.getBio());
        return entity;
    }

    @Override
    public AuthorResponeDTO entityToDto(AuthorEntity entity) {
        AuthorResponeDTO dto = new AuthorResponeDTO();

        dto.setName(entity.getName());
        dto.setBio(entity.getBio());
        return dto;
    }
}
