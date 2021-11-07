package com.example.testproject.role.web;


import com.example.testproject.role.model_repo.ERole;
import com.example.testproject.role.model_repo.Role;

public interface RoleService {

    Role findByRole(ERole role);
}
