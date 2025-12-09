package com.example.library_management.service;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponeDTO;
import com.example.library_management.utils.APIRespone;
import jakarta.annotation.Nonnull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    @Nonnull
    ResponseEntity<APIRespone<UserResponeDTO>> createUser(UserRequestDTO userRequestDTO);

    @Nonnull
    ResponseEntity<APIRespone<List<UserResponeDTO>>> getAllUser();

    @Nonnull
    ResponseEntity<APIRespone<UserResponeDTO>> getUserById(Integer id);

    @Nonnull
    ResponseEntity<APIRespone<UserResponeDTO>> updateUser(Integer id, UserRequestDTO users);

    @Nonnull
    ResponseEntity<APIRespone<String>> deleteUser(Integer id);
}
