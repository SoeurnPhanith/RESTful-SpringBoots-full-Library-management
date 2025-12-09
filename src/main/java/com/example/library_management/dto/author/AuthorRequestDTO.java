package com.example.library_management.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequestDTO {

    //todo : make author input only neccessary data
    @NotBlank (message = "name can't be empty or blank data!")
    private String name;

    @NotBlank (message = "Bio or description can't be empty")
    @Size(min = 5, max = 60, message = "must be between 5 to 60 characters")
    private String bio;

}
