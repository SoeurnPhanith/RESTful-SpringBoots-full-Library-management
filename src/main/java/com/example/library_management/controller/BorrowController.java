package com.example.library_management.controller;

import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.borrow.BorrowResponseDTO;
import com.example.library_management.dto.borrow.ReturnBookDTO;
import com.example.library_management.entity.BorrowEntity;
import com.example.library_management.service.service_implementation.BorrowServiceImpl;
import com.example.library_management.utils.APIRespone;
import com.example.library_management.utils.UtilEndPoint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UtilEndPoint.Route + "/borrow")
public class BorrowController {

    //inject data from BorrowServiceImpl
    @Autowired
    private BorrowServiceImpl borrowService;

    @PostMapping
    public ResponseEntity<APIRespone<BorrowResponseDTO>> borrowBook(
           @Valid @RequestBody BorrowRequestDTO borrow
    ){
        return borrowService.borrow(borrow);
    }

    @PostMapping("/return")
    public ResponseEntity<APIRespone<BorrowEntity>> returnBook(
            @Valid @RequestBody ReturnBookDTO dto
    ){
        return borrowService.returnBook(dto);
    }

    @GetMapping
    public ResponseEntity<APIRespone<List<BorrowResponseDTO>>> checkAllHistoryBookInLibrary(){
        return borrowService.checkAllHistoryOfBorrowed();
    }

}
