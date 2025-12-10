package com.example.library_management.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Setter
@Getter
@Component
@JsonInclude(JsonInclude.Include.NON_NULL) //vea ot show data na deal yg ot hav tv te (ot show data na deal null)
public class BookResponseDTO {

    //todo : show necessary data to client
    private Integer id;

    private String title;

    private Integer authorId;
    private String authorName;

    private Integer categoryId;
    private String categoryName;

    private String imagePath;
    private String imageType;
    private Boolean avaiable;

    private LocalDateTime publishDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public BookResponseDTO(String title, String imagePath, Boolean avaiable){
        this.imagePath = imagePath;
        this.title = title;
        this.avaiable = avaiable;
    }

    public BookResponseDTO(){}

}
