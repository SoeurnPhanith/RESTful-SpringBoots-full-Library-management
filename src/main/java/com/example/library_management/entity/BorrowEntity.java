package com.example.library_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table (name = "tbl_borrow_book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Integer id;

    // MANY TO ONE → USER
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    // MANY TO ONE → BOOK
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

}
