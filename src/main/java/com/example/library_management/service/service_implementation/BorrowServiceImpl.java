package com.example.library_management.service.service_implementation;

import com.example.library_management.dto.users.UserResponseDTO;
import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.borrow.BorrowResponseDTO;
import com.example.library_management.dto.borrow.ReturnBookDTO;
import com.example.library_management.entity.BookEntity;
import com.example.library_management.entity.BorrowEntity;
import com.example.library_management.entity.UsersEntity;
import com.example.library_management.exception.GenericException;
import com.example.library_management.exception.ResourceNotFoundException;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowRepository;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.service.BorrowService;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    //inject data from BorrowRepository, UserRepository, BookRepository
    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public @NonNull ResponseEntity<APIRespone<BorrowResponseDTO>> borrow(BorrowRequestDTO borrow) {
       try{
            //check exists borrow
           // 1. get user and book
           BookEntity book = bookRepository.findById(borrow.getBook())
                   .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

           UsersEntity user = userRepository.findById(borrow.getUser())
                   .orElseThrow(() -> new ResourceNotFoundException("user not found"));
           // 2. Check if available
           if (!book.getAvailable()) {
               return ResponseEntity.status(HttpStatus.CONFLICT).body(
                       new APIRespone<>(
                               false,
                               "This book is not available now!",
                               null
                       )
               );
           }

           // 3. Change status to unavailable because use borrow it
           book.setAvailable(false);
           bookRepository.save(book);

           //map data from requestDTO to Entity and save it to database
           BorrowEntity entity = new BorrowEntity();
           entity.setUser(user);
           entity.setBook(book);
           entity.setReturnDate(borrow.getDueDate());
           BorrowEntity savedBorrow = borrowRepository.save(entity);

           //map data from Entity to ResponseDTO
           BorrowResponseDTO responseDTO = new BorrowResponseDTO();
           responseDTO.setUser(new UserResponseDTO(
                   savedBorrow.getUser().getName(),
                   savedBorrow.getUser().getEmail()
           ));
           responseDTO.setBook(new BookResponseDTO(
                  savedBorrow.getBook().getTitle(),
                  savedBorrow.getBook().getImagePath(),
                  savedBorrow.getBook().getAvailable()
           ));
           responseDTO.setBorrowDate(savedBorrow.getBorrowDate());
           responseDTO.setReturnDate(savedBorrow.getDueDate());
           return ResponseEntity.ok(new APIRespone<>(
                   true,
                   "Book borrow success",
                   responseDTO
           ));

       }catch (Exception exception){
           throw new GenericException(exception.getMessage());
       }
    }

    //return book to library
    public ResponseEntity<APIRespone<BorrowEntity>> returnBook(ReturnBookDTO dto) {
        try{
            //get bookId use borrowed
            BorrowEntity borrow = borrowRepository.findById(dto.getBorrowId())
                    .orElseThrow(() -> new RuntimeException("Borrow record not found"));

            //if found id of book user borrowed
            borrow.setReturnDate(dto.getReturnDate());
            borrow.setIs_returned(true);

            // Calculate late days
            long daysLate = ChronoUnit.DAYS.between(
                    borrow.getDueDate().toLocalDate(),
                    dto.getReturnDate().toLocalDate()
            );

            //calculate fineAmount in one day
            double fine = 0;
            if (daysLate > 0) {
                fine = daysLate * 0.50; //if late on day fine 0.50$
            }
            //set fineAmount when return late
            borrow.setFine_amount(fine);

            //update and save avialable book status in bookTable
            BookEntity bookEntity = borrow.getBook();
            bookEntity.setAvailable(borrow.getIs_returned());
            bookRepository.save(bookEntity);

            //save to database
            borrowRepository.save(borrow);
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "book return to library! Thanks you",
                    borrow
            ));
        }catch (Exception exception){
            throw new GenericException(exception.getMessage());
        }

    }

    @Override
    public ResponseEntity<APIRespone<List<BorrowResponseDTO>>> checkAllHistoryOfBorrowed() {
        try {
            List<BorrowEntity> borrowData = borrowRepository.findAll();
            if (borrowData.isEmpty()) {
                throw new ResourceNotFoundException("No data record!");
            }

            List<BorrowResponseDTO> dtoList = new ArrayList<>();
            for (BorrowEntity data : borrowData) {
                BorrowResponseDTO responseDTO  = new BorrowResponseDTO();
                responseDTO.setBorrowId(data.getId());
                responseDTO.setUser(new UserResponseDTO(data.getUser().getName(), data.getUser().getEmail()));
                responseDTO.setBook(new BookResponseDTO(
                        data.getBook().getTitle(),
                        data.getBook().getImagePath(),
                        data.getBook().getAvailable()
                ));
                responseDTO.setBorrowDate(data.getBorrowDate());
                responseDTO.setDueTime(data.getDueDate());
                responseDTO.setReturnDate(data.getReturnDate());
                responseDTO.setFineAmount(data.getFine_amount());

                dtoList.add(responseDTO);

            }
            return ResponseEntity.ok(new APIRespone<>(
                    true,
                    "check borrow book success",
                     dtoList
            ));
        }catch (Exception exception) {
            throw new GenericException(exception.getMessage());
        }
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<BorrowResponseDTO>> checkHistoryOfBorrowedById(Integer id) {
        return null;
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<BorrowResponseDTO>> updateHistory(Integer id, BorrowRequestDTO borrow) {
        return null;
    }

    @Override
    public @NonNull ResponseEntity<APIRespone<String>> clearHistory(Integer id) {
        return null;
    }
}



