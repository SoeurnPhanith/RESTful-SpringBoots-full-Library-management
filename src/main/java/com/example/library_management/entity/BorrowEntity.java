package com.example.library_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

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

    @CreationTimestamp
    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;

    private LocalDateTime dueDate; //gai song

    @Column(name = "return_date")
    private LocalDateTime returnDate; //gai song book pit

    @Column(name = "is_returned")
    private Boolean is_returned = false;

    @Column(name = "fine_amount")
    private Double fine_amount; //loy piney

}
