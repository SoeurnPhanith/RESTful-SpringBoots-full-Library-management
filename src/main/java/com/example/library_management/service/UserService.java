package com.example.library_management.service;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.utils.APIRespone;
import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    @Nonnull
    ResponseEntity<APIRespone<UserResponseDTO>> createUser(UserRequestDTO userRequestDTO);

    @Nonnull
    ResponseEntity<APIRespone<List<UserResponseDTO>>> getAllUser();

    @Nonnull
    ResponseEntity<APIRespone<UserResponseDTO>> getUserById(Integer id);

    @Nonnull
    ResponseEntity<APIRespone<UserResponseDTO>> updateUser(Integer id, UserRequestDTO users);

    @Nonnull
    ResponseEntity<APIRespone<String>> deleteUser(Integer id);
}
