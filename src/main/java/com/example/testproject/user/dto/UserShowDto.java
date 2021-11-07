package com.example.testproject.user.dto;

import com.example.testproject.role.dto.RoleShowDto;
import lombok.Data;

import java.util.Set;

@Data
public class UserShowDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Set<RoleShowDto> roles;
}
