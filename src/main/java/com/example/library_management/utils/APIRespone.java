package com.example.library_management.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class APIRespone <T> {

    private Boolean isSuccess;
    private String message;
    private T data;

}
