package com.example.library_management.controller;

import com.example.library_management.entity.BookEntity;
import com.example.library_management.entity.BorrowEntity;
import com.example.library_management.entity.UsersEntity;
import com.example.library_management.repository.BookRepository;
import com.example.library_management.repository.BorrowRepository;
import com.example.library_management.repository.UserRepository;
import com.example.library_management.utils.UtilEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UtilEndPoint.Route + "/borrow")
public class BorrowController {

    //inject data from BorrowRepository, UserRepository, BookRepository
    @Autowired
    private BorrowRepository borrowRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    @PostMapping
    public BorrowEntity borrowBook(
            @RequestBody BorrowEntity borrow,
            UsersEntity users,
            BookEntity book
    ){
        UsersEntity usersEntity = userRepository.findById(users.getId()).orElseThrow();
        BookEntity bookEntity = bookRepository.findById(book.getId()).orElseThrow();

        //set it into borrow entity
        borrow.setBook(bookEntity);
        borrow.setUser(usersEntity);

        //save it into entity
        return borrowRepository.save(borrow);
    }

}
