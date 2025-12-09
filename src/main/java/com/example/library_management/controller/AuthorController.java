package com.example.library_management.controller;

import com.example.library_management.dto.author.AuthorRequestDTO;
import com.example.library_management.dto.author.AuthorResponeDTO;
import com.example.library_management.service.service_implementation.AuthorServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping (UtilEndPoint.Route + "/author")
public class AuthorController {

    //inject data from AuthorServiceImpl
    @Autowired
    private AuthorServiceImpl authorService;

    @PostMapping
    public ResponseEntity<APIRespone<AuthorResponeDTO>> addAuthor(
            @RequestBody @Valid AuthorRequestDTO author
    ){
        return authorService.addAuthor(author);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<AuthorResponeDTO>>> getAllAuthor(){
        return authorService.checkAllAuthor();
    }

    @GetMapping (path = "/{authorId}")
    public ResponseEntity<APIRespone<AuthorResponeDTO>> getAuthorById(
            @PathVariable @Valid Integer authorId
    ){
        return authorService.checkAuthorById(authorId);
    }

    @PutMapping ("/{authorId}")
    public ResponseEntity<APIRespone<AuthorResponeDTO>> updateAuthor(
            @PathVariable @Valid Integer authorId,
            @RequestBody @Valid AuthorRequestDTO authorRequestDTO
    ){
        return authorService.updateAuthor(authorId, authorRequestDTO);
    }

    @DeleteMapping ("/{authorId}")
    public ResponseEntity<APIRespone<AuthorResponeDTO>> removeAuthor(
                @PathVariable @Valid Integer authorId
    ){
        return removeAuthor(authorId);
    }
}
