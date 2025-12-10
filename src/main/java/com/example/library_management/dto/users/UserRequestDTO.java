package com.example.library_management.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class UserRequestDTO {

    //put only column user must be input to database
    //todo : transfer data from Entity to DTO with column user must be input
    @NotBlank (message = "name can't be empty")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "name must contain only letters")
    private String name;

    @NotBlank (message = "email can't be empty")
    @Email (message = "email must be gmail")
    private String email;

    @NotBlank
    private String password;

}
