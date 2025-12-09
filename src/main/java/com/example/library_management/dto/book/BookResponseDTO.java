package com.example.library_management.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    //todo : show necessary data to client
    private Integer id;

    private String title;

    private Integer authorId;
    private String authorName;

    private Integer categoryId;
    private String categoryName;

    private LocalDateTime publishDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
