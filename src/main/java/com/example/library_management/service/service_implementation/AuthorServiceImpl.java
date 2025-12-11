package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.author.AuthorRequestDTO;
import com.example.library_management.dto.author.AuthorResponeDTO;
import com.example.library_management.entity.AuthorEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.mapper.mapper_impl.AuthorMapperImpl;
import com.example.library_management.repository.AuthorRepository;
import com.example.library_management.service.AuthorService;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    //inject data from AuthorRepository
    @Autowired
    private AuthorRepository authorRepository;

    //inject data from AuthorMapperImpl
    @Autowired
    private AuthorMapperImpl authorMapper;

    @Override
    public @NonNull ResponseEntity<APIRespone<AuthorResponeDTO>> addAuthor(AuthorRequestDTO author) {
        try {
            //check it exists
            boolean existsAuthor = authorRepository.existsByName(author.getName());
            if (existsAuthor) {
                throw new DuplicateDataException("This author already existing");
            }

            //map data from requestDTO -->> Entity
            AuthorEntity authorEntity = authorMapper.dtoToEntity(author);

            //save to entity or repository
            AuthorEntity savedEntity = authorRepository.save(authorEntity);

            //map data from Entity --->> responseDTO
            AuthorResponeDTO authorResponeDTO = authorMapper.entityToDto(savedEntity);
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "create author success",
                            authorResponeDTO
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<List<AuthorResponeDTO>>> checkAllAuthor() {
        try{
            //get all author from entity or database
            List<AuthorEntity> author = authorRepository.findAll();

            //get all data from entity for sent all data to responseDTO
            List<AuthorResponeDTO> DTOList = new ArrayList<>();
            for(AuthorEntity authorEntity : author){
                AuthorResponeDTO responseDTO = authorMapper.entityToDto(authorEntity);

                //save into DTOList
                DTOList.add(responseDTO);
            }
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "get all data success",
                            DTOList
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<AuthorResponeDTO>> checkAuthorById(Integer id) {
        try{
            //find data in database with id
            Optional<AuthorEntity> isFound = authorRepository.findById(id);
            if(!isFound.isPresent()){
                throw new ResourceNotFoundException("Not found this id");
            }

            //get data who you found
            AuthorEntity getAuthor = isFound.get();

            //map entity -->> responseDTO
            AuthorResponeDTO responseDTO = authorMapper.entityToDto(getAuthor);
            return ResponseEntity.ok(
                    new APIRespone<>(
                            true,
                            "get author id : " + id + " success",
                            responseDTO
                    )
            );
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<AuthorResponeDTO>> updateAuthor(Integer id, AuthorRequestDTO author) {
        try{
            //find author in database
            Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
            if(!authorEntity.isPresent()){
                throw new ResourceNotFoundException("This author id " + id + " not found!");
            }

            //get data if you found it on database and update from user request
            AuthorEntity update = authorEntity.get();
            update.setName(author.getName());
            update.setBio(author.getBio());

            //save to entity
            AuthorEntity savedEntity = authorRepository.save(update);

            //map Entity -->> ResponseDTO for update
            AuthorResponeDTO responseDTO = authorMapper.entityToDto(savedEntity);
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "update data on author id " + id + " success",
                    responseDTO
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<String>> removeAuthor(Integer id) {
        try{
            //find author in database with id
            Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
            if(!authorEntity.isPresent()){
                throw new ResourceNotFoundException("This student id " + id + " not found");
            }

            //remove
            authorRepository.deleteById(id);
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "remove student success",
                    "Done !!!"
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
