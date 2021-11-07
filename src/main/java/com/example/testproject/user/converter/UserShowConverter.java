package com.example.testproject.user.converter;

import com.example.testproject.role.converter.RoleShowConverter;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.user.dto.UserShowDto;
import com.example.testproject.user.model_repo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserShowConverter extends BaseConverter<User, UserShowDto> {

    private final RoleShowConverter roleShowConverter;

    @Override
    public Function<UserShowDto, User> toEntity() {
        return null;
    }

    @Override
    public Function<User, UserShowDto> toDto() {
        return user -> {
            if (user == null)
                return null;

            UserShowDto dto = new UserShowDto();
            dto.setEmail(user.getEmail());
            dto.setFirstname(user.getFirstname());
            dto.setId(user.getId());
            dto.setLastname(user.getLastname());
            dto.setRoles(
                    user.getRoles()
                            .stream()
                            .map(this.roleShowConverter.toDto())
                            .collect(Collectors.toSet()));
            dto.setUsername(user.getUsername());

            return dto;
        };
    }
}
