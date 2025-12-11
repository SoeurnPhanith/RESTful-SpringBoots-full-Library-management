package com.example.library_management.controller;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.service.service_implementation.BookServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping (UtilEndPoint.Route + "/book")
public class BookController {

    //inject data from BookServiceImpl
    @Autowired
    private BookServiceImpl bookService;

    @PostMapping
    public ResponseEntity<APIRespone<BookResponseDTO>> addBook(
            @ModelAttribute BookRequestDTO bookDTO,
            //use model attribute dermby aj upload image ban ber use @RequestBody ban tea Json format only
            //muy tt vea accept oy yg bos data tv pii form data ban
            @RequestParam("image") MultipartFile imageFile
    ) throws IOException {
        return bookService.addBook(bookDTO, imageFile);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<BookResponseDTO>>> getBooks(
            @RequestParam(defaultValue = "0") int page,          // page number (default 0)
            @RequestParam(defaultValue = "10") int size,         // page size (default 10)
            @RequestParam(defaultValue = "id") String sortBy,    // field to sort (default id)
            @RequestParam(defaultValue = "asc") String sortDir   // sort direction (asc or desc)
    ) {
        return bookService.showAllBookInLibrary(page, size, sortBy, sortDir);
    }

    @GetMapping ("/{bookId}")
    public ResponseEntity<APIRespone<BookResponseDTO>> showBookInLibraryById(
            @PathVariable @Valid Integer bookId
    ){
        return bookService.showBookInLibraryById(bookId);
    }

    @PutMapping ("/{bookId}")
    public ResponseEntity<APIRespone<BookResponseDTO>> updateBook(
            @PathVariable @Valid Integer bookId,
            @ModelAttribute @Valid BookRequestDTO book,
            @RequestParam (value = "image", required = false) MultipartFile image
    )throws IOException{
        return bookService.updateBookInLibrary(bookId, book, image);
    }
    /*nakប្រើ throws IOException → ប្រាប់ Spring Boot:
    ប្រសិនបើមាន error during file upload → Spring Boot handle error automatically (500 Internal Server Error)*/

    @DeleteMapping ("/{bookId}")
    public ResponseEntity<APIRespone<String>> removeBookFromLibrary(
            @PathVariable @Valid Integer bookId
    ){
        return bookService.removeBookInLibrary(bookId);
    }

}
