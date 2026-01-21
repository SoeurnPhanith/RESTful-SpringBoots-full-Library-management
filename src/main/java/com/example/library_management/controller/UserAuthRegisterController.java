package com.example.library_management.controller;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.service.service_implementation.UserRegisterService;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (UtilEndPoint.Route + "/api")
public class UserAuthRegisterController {

    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/register")
    public ResponseEntity<APIRespone<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return userRegisterService.createUser(userRequestDTO);
    }
}
