package com.example.library_management.service;

import com.example.library_management.entity.BorrowEntity;
import com.example.library_management.utils.APIRespone;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowService {

    @NonNull
    ResponseEntity<APIRespone<BorrowEntity>> borrow(BorrowEntity borrow);

    @NonNull
    ResponseEntity<List<APIRespone<BorrowEntity>>> checkAllHistoryOfBorrowed();

    @NonNull
    ResponseEntity<APIRespone<BorrowEntity>> checkHistoryOfBorrowedById(Integer id);

    @NonNull
    ResponseEntity<APIRespone<BorrowEntity>> updateHistory(Integer id, BorrowEntity borrow);

    @NonNull
    ResponseEntity<APIRespone<String>> clearHistory(Integer id);
}
