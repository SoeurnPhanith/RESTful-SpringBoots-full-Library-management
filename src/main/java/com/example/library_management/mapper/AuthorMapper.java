package com.example.library_management.mapper;

import com.example.library_management.dto.author.AuthorRequestDTO;
import com.example.library_management.dto.author.AuthorResponeDTO;
import com.example.library_management.entity.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public interface AuthorMapper {

    AuthorEntity dtoToEntity(AuthorRequestDTO dto);
    AuthorResponeDTO entityToDto(AuthorEntity entity);

}
