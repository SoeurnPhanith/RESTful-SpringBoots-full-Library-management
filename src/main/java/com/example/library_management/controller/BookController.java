package com.example.library_management.controller;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.service.service_implementation.BookServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping (UtilEndPoint.Route + "/book")
public class BookController {

    //inject data from BookServiceImpl
    @Autowired
    private BookServiceImpl bookService;

    @PostMapping
    public ResponseEntity<APIRespone<BookResponseDTO>> addBook(
            @RequestBody @Valid BookRequestDTO book
    ){
        return bookService.addBook(book);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<BookResponseDTO>>> showAllBookInLibrary(){
        return bookService.showAllBookInLibrary();
    }

    @GetMapping ("/{bookId}")
    public ResponseEntity<APIRespone<BookResponseDTO>> showBookInLibraryById(
            @PathVariable @Valid Integer bookId
    ){
        return bookService.showBookInLibraryById(bookId);
    }
}
