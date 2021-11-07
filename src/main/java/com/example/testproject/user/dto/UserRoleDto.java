package com.example.testproject.user.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserRoleDto {

    private Set<String> roles;
}
