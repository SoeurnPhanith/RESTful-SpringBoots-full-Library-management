package com.example.library_management.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table (name = "tbl_book")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    @Column(name = "book_title", nullable = false)
    private String title;

    //Relationship
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnore
    private AuthorEntity author;

    // Correct Relationship ManyToOne
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "publish_datetime")
    private LocalDateTime publishDate;

    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime create;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime update;

}

