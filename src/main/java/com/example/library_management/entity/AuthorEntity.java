package com.example.library_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "tbl_author")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer id;

    @Column(name = "author_name")
    private String name;

    private String bio;

    // One Author has many books
    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private List<BookEntity> books;

}
