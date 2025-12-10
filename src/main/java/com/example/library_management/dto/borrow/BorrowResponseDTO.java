package com.example.library_management.dto.borrow;

import com.example.library_management.dto.book.BookResponseDTO;
import com.example.library_management.dto.users.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowResponseDTO {

    private int borrowId;

    private UserResponseDTO user;    // ✅ must be DTO
    private BookResponseDTO book;    // ✅ must be DTO

    private LocalDateTime borrowDate;
    private LocalDateTime dueTime;

    private Double fineAmount;
    private LocalDateTime returnDate;
}
