package com.example.library_management.controller;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.service.service_implementation.UserServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (UtilEndPoint.Route + "/user")
public class UserController {

    //inject data from userService impl
    @Autowired(required = true)
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<APIRespone<UserResponseDTO>> createUser(
            @Valid @RequestBody UserRequestDTO users
    ){
        return userService.createUser(users);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<UserResponseDTO>>> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping ("/{userId}")
    public ResponseEntity<APIRespone<UserResponseDTO>> getUserById(
            @PathVariable @Valid Integer userId
    ){
        return userService.getUserById(userId);
    }

    @PutMapping ("/{userId}")
    public ResponseEntity<APIRespone<UserResponseDTO>> updateUser(
            @PathVariable @Valid Integer userId,
            @RequestBody @Valid UserRequestDTO userRequestDTO
    ){
        return userService.updateUser(userId, userRequestDTO);
    }

    @DeleteMapping ("/{userId}")
    public ResponseEntity<APIRespone<String>> deleteUser(
            @PathVariable @Valid Integer userId
    ){
        return userService.deleteUser(userId);
    }


}
