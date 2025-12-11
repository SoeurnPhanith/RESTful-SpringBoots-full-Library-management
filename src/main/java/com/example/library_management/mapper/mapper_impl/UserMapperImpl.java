package com.example.library_management.mapper.mapper_impl;

import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.entity.UsersEntity;
import com.example.library_management.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UsersEntity dtoToEntity(UserRequestDTO dto) {
        //set dto to entity
        UsersEntity entity = new UsersEntity();

        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    @Override
    public UserResponseDTO entityToDTO(UsersEntity entity) {
        UserResponseDTO dto = new UserResponseDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
