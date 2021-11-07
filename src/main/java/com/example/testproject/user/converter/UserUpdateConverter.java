package com.example.testproject.user.converter;

import com.example.testproject.shared.BaseConverter;
import com.example.testproject.user.dto.UserUpdateDto;
import com.example.testproject.user.model_repo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class UserUpdateConverter extends BaseConverter<User, UserUpdateDto> {
    @Override
    public Function<UserUpdateDto, User> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            User user = new User();
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());

            return user;
        };
    }

    @Override
    public Function<User, UserUpdateDto> toDto() {
        return user -> {
            if (user == null)
                return null;

            UserUpdateDto dto = new UserUpdateDto();

            dto.setFirstname(user.getFirstname());
            dto.setEmail(user.getEmail());
            dto.setLastname(user.getLastname());
            dto.setUsername(user.getUsername());

            return dto;
        };
    }
}
