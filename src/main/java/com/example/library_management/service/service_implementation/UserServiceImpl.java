package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.users.UserRequestDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.entity.UsersEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.mapper.mapper_impl.UserMapperImpl;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.service.UserService;
import com.example.library_management.utils.APIRespone;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    //inject data from UserRepository
    @Autowired
    private UserRepository userRepository;

    //inject data from UserResponseDTO
    @Autowired
    private UserMapperImpl userMapper;

    @Nonnull
    @Override
    public ResponseEntity<APIRespone<UserResponseDTO>> createUser(UserRequestDTO userRequestDTO) {
        try{
            //check exist user
            boolean exitsUser = userRepository.existsByEmail(userRequestDTO.getEmail());
            if(exitsUser){
                 throw new DuplicateDataException("This user is already exist");
            }

            //Password validate
            String password = userRequestDTO.getPassword();
            if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")){
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
                    "add student success",
                    responseDTO
            ));
        }catch (Exception exception){
            throw new ResourceNotFoundException(exception.getMessage());
        }
    }

    @Nonnull
    @Override
    public ResponseEntity<APIRespone<List<UserResponseDTO>>> getAllUser() {
        try{
            //get all data from entity
            List<UsersEntity> usersEntity = userRepository.findAll();

            //check empty data in database or not
            if(usersEntity.isEmpty()){
                throw new ResourceNotFoundException("No data record");
            }

            //get all data from userEntity --> userResponseDTO
            List<UserResponseDTO> dtoList = new ArrayList<>();
            for(UsersEntity users : usersEntity){
                UserResponseDTO userResponseDTO = userMapper.entityToDTO(users);

                //add to dtoList
                dtoList.add(userResponseDTO);
            }
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "get all user successful",
                            dtoList
                    )
            );
        }catch (Exception exception){
           throw new GenericException(exception.getMessage());        }
    }


    @Nonnull
    @Override
    public ResponseEntity<APIRespone<UserResponseDTO>> getUserById(Integer id) {
       try{
           //get data from userResponseDTO with id
           Optional<UsersEntity> users = userRepository.findById(id);

           //check it have user or not
           if(!users.isPresent()){
              throw new ResourceNotFoundException("Not found this user");
           }

           //get data from entity --> userResponseDTO
           UsersEntity entity = users.get();

           UserResponseDTO userResponseDTO = userMapper.entityToDTO(entity);
           return ResponseEntity.ok(
                   new APIRespone<>(
                           true,
                           "get student id " + id + " success",
                           userResponseDTO
                   )
           );
       }catch (Exception exception){
           throw new GenericException(exception.getMessage());
       }
    }

    @Nonnull
    @Override
    public ResponseEntity<APIRespone<UserResponseDTO>> updateUser(Integer id, UserRequestDTO userDTO) {
        try{
            //get data from database with id
            Optional<UsersEntity> usersEntity = userRepository.findById(id);

            //check it having or not
            if(!usersEntity.isPresent()){
                throw new ResourceNotFoundException("Not found this user to update");
            }

            UsersEntity updateEntity = usersEntity.get(); //get

            //requestDTO -->>  Entity
            UsersEntity entity = userMapper.updateDtoToEntity(userDTO);

            //save this entity
            UsersEntity savedUpdate = userRepository.save(updateEntity);

            //sent data from entity --> userResponseDTO
            UserResponseDTO userResponseDTO = userMapper.entityToDTO(savedUpdate);
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "update user success!",
                            userResponseDTO
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
    @Nonnull
    @Override
    public ResponseEntity<APIRespone<String>> deleteUser(Integer id) {
        try{
            //get data with id
            Optional<UsersEntity> usersEntity = userRepository.findById(id);

            //check it having or not
            if(!usersEntity.isPresent()){
                throw new ResourceNotFoundException("Not found this user to delete");
            }

            //remove by id found
            userRepository.deleteById(id);
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "remove user success",
                            "Done"
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
