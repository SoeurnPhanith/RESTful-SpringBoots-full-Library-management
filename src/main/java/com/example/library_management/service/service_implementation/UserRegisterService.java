package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.entity.UsersEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.mapper.mapper_impl.UserMapperImpl;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.utils.APIRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {

    //inject data from UserRepository
    @Autowired
    private UserRepository userRepository;

    //inject data from UserResponseDTO
    @Autowired
    private UserMapperImpl userMapper;

    public ResponseEntity<APIRespone<UserResponseDTO>> createUser(UserRequestDTO userRequestDTO) {
        try {
            //check exist user
            boolean exitsUser = userRepository.existsByEmail(userRequestDTO.getEmail());
            if (exitsUser) {
                throw new DuplicateDataException("This user is already exist");
            }

            //Password validate
            String password = userRequestDTO.getPassword();
            if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new APIRespone<>(
                                false,
                                "Password must contain 1 uppercase, 1 lowercase, 1 number, min 6 chars",
                                null
                        ));
            }

            //map from dto-->entity
//            UsersEntity entity = new UsersEntity();
//            entity.setName(userRequestDTO.getName());
//            entity.setEmail(userRequestDTO.getEmail());
//            entity.setPassword(userRequestDTO.getPassword());
            UsersEntity entity = userMapper.dtoToEntity(userRequestDTO);

            //save to entity
            UsersEntity savedEntity = userRepository.save(entity);

            //map from entity -->> dto
            UserResponseDTO responseDTO = userMapper.entityToDTO(savedEntity);

            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "user create successful",
                    responseDTO
            ));
        } catch (Exception exception) {
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }
}
