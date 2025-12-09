package com.example.library_management.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    //todo : make user input neccessary data

    @JsonProperty("cat_name")
    @NotEmpty(message = "Category name can't be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Category name must contain only letters")
    @Size(min = 4, max = 30, message = "Category name must be between 4 to 30 characters")
    private String categoryName;


    //Note @Min & @Max use ban tea number te


}
