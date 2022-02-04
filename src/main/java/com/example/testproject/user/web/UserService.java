package com.example.testproject.user.web;

import com.example.testproject.user.dto.UserPasswordDto;
import com.example.testproject.user.model_repo.User;
import org.springframework.data.domain.Page;


public interface UserService {

    User findByUsername(String username);

    boolean existByUsername(String username);

    boolean existByEmail(String email);

    boolean updatePassword(Long id, UserPasswordDto dto);

    boolean updateRoles(Long id, User userRole);

    User getAuthUser();
}
