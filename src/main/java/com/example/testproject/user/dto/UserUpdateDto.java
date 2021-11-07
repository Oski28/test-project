package com.example.testproject.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserUpdateDto {

    @NotBlank(message = "Firstname cannot be blank.")
    @Size(min = 3, max = 50, message = "Firstname must contain between 3 and 50 characters.")
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank.")
    @Size(min = 3, max = 50, message = "Lastname must contain between 3 and 50 characters.")
    private String lastname;

    @NotBlank(message = "Username cannot be blank.")
    @Size(min = 3, max = 50, message = "Username must contain between 3 and 50 characters.")
    private String username;

    @Email(message = "Email must be correct.")
    @NotBlank(message = "Email cannot be null.")
    private String email;
}
