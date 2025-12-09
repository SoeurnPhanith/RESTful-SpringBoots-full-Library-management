package com.example.library_management.dto.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponeDTO {

    //todo : get only necessary data to client
    private String name;
    private String bio;
}
