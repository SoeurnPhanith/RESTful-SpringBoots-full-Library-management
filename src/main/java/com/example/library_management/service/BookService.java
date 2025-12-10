package com.example.library_management.service;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BookService {

    @NonNull
    ResponseEntity<APIRespone<BookResponseDTO>> addBook(BookRequestDTO book,  MultipartFile imageFile);

    @NonNull
    ResponseEntity<APIRespone<List<BookResponseDTO>>> showAllBookInLibrary();

    @NonNull
    ResponseEntity<APIRespone<BookResponseDTO>> showBookInLibraryById(Integer id);

    @NonNull
    ResponseEntity<APIRespone<BookResponseDTO>> updateBookInLibrary(
            Integer id,
            BookRequestDTO book,
            MultipartFile imageFile
    );

    @NonNull
    ResponseEntity<APIRespone<String>> removeBookInLibrary(Integer id);
}
