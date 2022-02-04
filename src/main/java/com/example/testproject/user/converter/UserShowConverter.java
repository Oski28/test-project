package com.example.testproject.user.converter;

import com.example.testproject.role.converter.RoleShowConverter;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.user.dto.UserShowDto;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserController;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
