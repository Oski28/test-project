package com.example.testproject.user.dto;

import com.example.testproject.validator.ValidPassword;
import lombok.Data;

@Data
public class UserPasswordDto {

    @ValidPassword
    private String oldPassword;

    @ValidPassword
    private String newPassword;
}
