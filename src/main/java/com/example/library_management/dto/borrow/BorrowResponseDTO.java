package com.example.library_management.dto.borrow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowResponseDTO {

    private Integer userId;
    private String userName;

    private Integer bookId;
    private String bookTitle;
    private String bookImage;

    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

}
