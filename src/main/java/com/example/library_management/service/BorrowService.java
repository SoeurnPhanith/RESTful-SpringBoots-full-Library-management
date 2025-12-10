package com.example.library_management.service;

import com.example.library_management.dto.borrow.BorrowRequestDTO;
import com.example.library_management.dto.borrow.BorrowResponseDTO;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowService {

    @NonNull
    ResponseEntity<APIRespone<BorrowResponseDTO>> borrow(BorrowRequestDTO borrow);

    ResponseEntity<APIRespone<List<BorrowResponseDTO>>> checkAllHistoryOfBorrowed();

    @NonNull
    ResponseEntity<APIRespone<BorrowResponseDTO>> checkHistoryOfBorrowedById(Integer id);

    @NonNull
    ResponseEntity<APIRespone<BorrowResponseDTO>> updateHistory(Integer id, BorrowRequestDTO borrow);

    @NonNull
    ResponseEntity<APIRespone<String>> clearHistory(Integer id);
}

