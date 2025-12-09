package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.entity.AuthorEntity;
import com.example.library_management.entity.BookEntity;
import com.example.library_management.entity.CategoryEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.repository.AuthorRepository;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.CategoryRepository;
import com.example.library_management.service.BookService;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    //inject data from BookRepository, AuthorRepository, CategoryRepository
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public @NonNull ResponseEntity<APIRespone<BookResponseDTO>> addBook(BookRequestDTO book) {
            try{
                //check existsBook
                boolean exists = bookRepository.existsByTitleAndCategoryId(
                        book.getTitle(),
                        book.getCategoryId()
                );
                if(exists){
                    throw new DuplicateDataException("This book already exists");
                }

                //getId of author and category
                AuthorEntity author = authorRepository.findById(book.getAuthorId())
                        .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
                CategoryEntity category = categoryRepository.findById(book.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                //map data from RequestDTO to Entity
                BookEntity entity = new BookEntity();
                entity.setTitle(book.getTitle());
                entity.setAuthor(author);
                entity.setCategory(category);
                entity.setPublishDate(book.getPublishDate());

                //save to entity
                BookEntity saved = bookRepository.save(entity);

                //map data from Entity to ResponseDTO
                BookResponseDTO responseDTO = new BookResponseDTO();
                responseDTO.setId(saved.getId());
                responseDTO.setTitle(saved.getTitle());
                responseDTO.setAuthorId(saved.getAuthor().getId());
                responseDTO.setAuthorName(saved.getAuthor().getName());
                responseDTO.setCategoryId(saved.getCategory().getId());
                responseDTO.setCategoryName(saved.getCategory().getName());
                responseDTO.setPublishDate(saved.getPublishDate());
                responseDTO.setCreateAt(saved.getCreate());
                responseDTO.setUpdateAt(saved.getUpdate());
                return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "add book to library success",
                    responseDTO
                ));
            }catch (Exception exception){
                throw new GenericException(exception.getMessage());
            }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<List<BookResponseDTO>>> showAllBookInLibrary() {
        try{
            //get all data from database
            List<BookEntity> allDataFromEntity = bookRepository.findAll();

            //check having or not
            if(allDataFromEntity.isEmpty()){
                throw new ResourceNotFoundException("No book found!");
            }

            //get all data from entity to ResponseDTO
            List<BookResponseDTO> dtoList = new ArrayList<>();
            for(BookEntity book : allDataFromEntity){
                BookResponseDTO responseDTO = new BookResponseDTO();

                responseDTO.setId(book.getId());
                responseDTO.setTitle(book.getTitle());
                responseDTO.setAuthorId(book.getAuthor().getId());
                responseDTO.setAuthorName(book.getAuthor().getName());
                responseDTO.setCategoryId(book.getCategory().getId());
                responseDTO.setCategoryName(book.getCategory().getName());
                responseDTO.setPublishDate(book.getPublishDate());
                responseDTO.setCreateAt(book.getCreate());
                responseDTO.setUpdateAt(book.getUpdate());

                //save to dtoList
                dtoList.add(responseDTO);
            }
            return ResponseEntity.ok(new APIRespone<>(
                true,
                "Get data success",
                dtoList
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<BookResponseDTO>> showBookInLibraryById(Integer id) {
        try{
            //find data in database with id
            Optional<BookEntity> getDataById = bookRepository.findById(id);
            if(!getDataById.isPresent()){
                throw new ResourceNotFoundException("This book not found!");
            }

            //get from id you was found
            BookEntity entity = getDataById.get();

            //map data from Entity to ResponseDTO
            BookResponseDTO responseDTO = new BookResponseDTO();
            responseDTO.setId(entity.getId());
            responseDTO.setTitle(entity.getTitle());
            responseDTO.setAuthorId(entity.getAuthor().getId());
            responseDTO.setAuthorName(entity.getAuthor().getName());
            responseDTO.setCategoryId(entity.getCategory().getId());
            responseDTO.setCategoryName(entity.getCategory().getName());
            responseDTO.setPublishDate(entity.getPublishDate());
            responseDTO.setCreateAt(entity.getCreate());
            responseDTO.setUpdateAt(entity.getUpdate());
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "Get book by id " + id + " success",
                    responseDTO
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<BookResponseDTO>> updateBookInLibrary(Integer id, BookRequestDTO book) {
        return null;
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<String>> removeBookInLibrary(Integer id) {
        return null;
    }
}
