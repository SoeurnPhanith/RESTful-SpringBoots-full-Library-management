package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.book.BookRequestDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.entity.AuthorEntity;
import com.example.library_management.entity.BookEntity;
import com.example.library_management.entity.CategoryEntity;
import com.example.library_management.exception.DuplicateDataException;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.mapper.mapper_impl.BookMapperImpl;
import com.example.library_management.repository.AuthorRepository;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.CategoryRepository;
import com.example.library_management.service.BookService;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    //inject BookMapperImpl
    @Autowired
    private BookMapperImpl bookMapper;
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
            BookResponseDTO responseDTO = bookMapper.entityToDto(saved);

            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "Add book to library success",
                    responseDTO
            ));

        } catch (Exception exception) {
            throw new GenericException(exception.getMessage());
        }
    }


    // Get all books with pagination & sorting
    @Override
    public @NonNull ResponseEntity<APIRespone<List<BookResponseDTO>>> showAllBookInLibrary(
            int page, int size,
            String sortBy, String sortDir
    ) {
        //check sort with Sort object
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        //Pageable or Pagination Object
        Pageable pageable = PageRequest.of(page, size, sort);
        //Pageable is parameter pass ទៅ repository ដើម្បី query database ជា page ទិន្នន័យ

        //get all data query from Pageable
        Page<BookEntity> bookPage = bookRepository.findAll(pageable);

        // map entity -->> ResponseDTO
        List<BookResponseDTO> dtoList = new ArrayList<>();
        for (BookEntity book : bookPage.getContent()) {
            BookResponseDTO responseDTO = bookMapper.entityToDto(book);
            dtoList.add(responseDTO);
        }

        return ResponseEntity.ok(new APIRespone<>(
                true,
                "List of books with pagination & sorting",
                dtoList     // ✅ Return only list → clean JSON (no metadata)
        ));
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
            BookResponseDTO responseDTO = bookMapper.entityToDto(entity);
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
           BookResponseDTO responseDTO = bookMapper.entityToDto(saved);
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
