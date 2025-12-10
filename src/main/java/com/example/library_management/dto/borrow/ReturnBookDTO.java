package com.example.library_management.dto.borrow;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReturnBookDTO {

    @NotNull(message = "Borrow ID is required")
    @JsonProperty("borrowId")
    private Integer borrowId;

    @NotNull(message = "Return date is required")
    private LocalDateTime returnDate;
}
