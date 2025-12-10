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
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.awt.print.Book;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
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

    //inject BookResponseDTO
    @Autowired
    private BookResponseDTO responseDTO;
    @Override
    public @NonNull ResponseEntity<APIRespone<BookResponseDTO>> addBook(BookRequestDTO book, MultipartFile imageFile) {
        try {
            // Check if book already exists
            boolean exists = bookRepository.existsByTitleAndCategoryId(
                    book.getTitle(),
                    book.getCategoryId()
            );
            if (exists) {
                throw new DuplicateDataException("This book already exists");
            }

            // Get author and category
            AuthorEntity author = authorRepository.findById(book.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
            CategoryEntity category = categoryRepository.findById(book.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            // Map RequestDTO to Entity
            BookEntity entity = new BookEntity();
            entity.setTitle(book.getTitle());
            entity.setAuthor(author);
            entity.setCategory(category);
            entity.setPublishDate(book.getPublishDate());
            if (imageFile != null && !imageFile.isEmpty()) {
                // ️save image file to folder uploads
                String uploadDir = System.getProperty("user.dir") + "/uploads/"; // get Image save to Path deal jg tuk
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //get file name from machine when user choose it
                Path filePath = Paths.get(uploadDir + fileName); //full path of image

                // create folder if not exists
                Files.createDirectories(filePath.getParent());
                imageFile.transferTo(filePath.toFile()); //save file pii client to uploads directory

                // save imagePath to Database
                entity.setImagePath("/uploads/" + fileName);

                // save image type to image type
                entity.setImageType(imageFile.getContentType()); // example: image/jpeg, image/png
            }

            // Save entity
            BookEntity saved = bookRepository.save(entity);

            // Map Entity to ResponseDTO
            responseDTO.setId(saved.getId());
            responseDTO.setTitle(saved.getTitle());
            responseDTO.setAuthorId(saved.getAuthor().getId());
            responseDTO.setAuthorName(saved.getAuthor().getName());
            responseDTO.setCategoryId(saved.getCategory().getId());
            responseDTO.setCategoryName(saved.getCategory().getName());
            responseDTO.setImagePath(saved.getImagePath()); // short URL string
            responseDTO.setImageType(saved.getImageType());
            responseDTO.setPublishDate(saved.getPublishDate());
            responseDTO.setCreateAt(saved.getCreate());
            responseDTO.setUpdateAt(saved.getUpdate());

            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "Add book to library success",
                    responseDTO
            ));

        } catch (Exception exception) {
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
                responseDTO.setImagePath(book.getImagePath()); // short URL string
                responseDTO.setImageType(book.getImageType());
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
            responseDTO.setImagePath(entity.getImagePath()); // short URL string
            responseDTO.setImageType(entity.getImageType());
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
    public @NonNull ResponseEntity<APIRespone<BookResponseDTO>> updateBookInLibrary(
            Integer id,
            BookRequestDTO book,
            MultipartFile imageFile
    ) {
       try{
            //find data in database with id
           Optional<BookEntity> getDataById = bookRepository.findById(id);
           if(!getDataById.isPresent()){
               throw new ResourceNotFoundException("This book not found!");
           }

           //getId of author and category
           AuthorEntity author = authorRepository.findById(book.getAuthorId())
                   .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
           CategoryEntity category = categoryRepository.findById(book.getCategoryId())
                   .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

           //get from id you was found
           BookEntity update = getDataById.get();

           //update data in entity
           update.setTitle(book.getTitle());
           update.setAuthor(author);
           update.setCategory(category);
           update.setPublishDate(book.getPublishDate());
           update.setUpdate(LocalDateTime.now());
           if (imageFile != null && !imageFile.isEmpty()) {
               // ️save image file to folder uploads
               String uploadDir = System.getProperty("user.dir") + "/uploads/"; // get Image save to Path deal jg tuk
               String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename(); //get file name from machine when user choose it
               Path filePath = Paths.get(uploadDir + fileName); //full path of image

               // create folder if not exists
               Files.createDirectories(filePath.getParent());
               imageFile.transferTo(filePath.toFile()); //save file pii client to uploads directory

               // save imagePath to Database
               update.setImagePath("/uploads/" + fileName);

               // save image type to image type
               update.setImageType(imageFile.getContentType()); // example: image/jpeg, image/png
           }


           //save entity who we updated
           BookEntity saved = bookRepository.save(update);
           //map data from Entity to ResponseDTO
           BookResponseDTO responseDTO = new BookResponseDTO();
           responseDTO.setId(saved.getId());
           responseDTO.setTitle(saved.getTitle());
           responseDTO.setAuthorId(saved.getAuthor().getId());
           responseDTO.setAuthorName(saved.getAuthor().getName());
           responseDTO.setCategoryId(saved.getCategory().getId());
           responseDTO.setCategoryName(saved.getCategory().getName());
           responseDTO.setImagePath(saved.getImagePath()); // short URL string
           responseDTO.setImageType(saved.getImageType());
           responseDTO.setPublishDate(saved.getPublishDate());
           responseDTO.setCreateAt(saved.getCreate());
           responseDTO.setUpdateAt(saved.getUpdate());
           return ResponseEntity.ok(new APIRespone<>(
                   true,
                   "update this book success",
                   responseDTO
           ));

       }catch (Exception exception){
           throw new GenericException(exception.getMessage());
       }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<String>> removeBookInLibrary(Integer id) {
        try{
            //find book in database with id
            BookEntity findBook = bookRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Not found this book in id "));

            bookRepository.deleteById(id);
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "remove data succeess",
                    ""
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }
    }
}
