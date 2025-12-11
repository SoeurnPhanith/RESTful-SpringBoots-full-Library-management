package com.example.library_management.mapper;

import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.entity.UsersEntity;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    //map from RequestUserDTO -->> UserEntity
    UsersEntity dtoToEntity(UserRequestDTO dto);

    //Map from UserEntity -->> UserResponseDTO
    UserResponseDTO entityToDTO(UsersEntity entity);

}
