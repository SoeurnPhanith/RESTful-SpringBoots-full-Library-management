package com.example.library_management.service;

import com.example.library_management.dto.author.AuthorRequestDTO;
import com.example.library_management.dto.author.AuthorResponeDTO;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {

    @NonNull
    ResponseEntity<APIRespone<AuthorResponeDTO>> addAuthor(AuthorRequestDTO author);

    @NonNull
    ResponseEntity<APIRespone<List<AuthorResponeDTO>>> checkAllAuthor();

    @NonNull
    ResponseEntity<APIRespone<AuthorResponeDTO>> checkAuthorById(Integer id);

    @NonNull
    ResponseEntity<APIRespone<AuthorResponeDTO>> updateAuthor(Integer id, AuthorRequestDTO author);

    @NonNull
    ResponseEntity<APIRespone<String>> removeAuthor(Integer id);

}
