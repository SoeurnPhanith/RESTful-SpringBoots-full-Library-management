package com.example.library_management.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO {

    //todo : respone category name to admin

    private Integer id;
    private String cateName;

    public CategoryResponseDTO(int id){
        this.id = id;
    }

}
