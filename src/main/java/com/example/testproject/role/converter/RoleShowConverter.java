package com.example.testproject.role.converter;

import com.example.testproject.role.dto.RoleShowDto;
import com.example.testproject.role.model_repo.Role;
import com.example.testproject.shared.BaseConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class RoleShowConverter extends BaseConverter<Role, RoleShowDto> {
    @Override
    public Function<RoleShowDto, Role> toEntity() {
        return null;
    }

    @Override
    public Function<Role, RoleShowDto> toDto() {
        return role -> {
            if (role == null)
                return null;

            RoleShowDto dto = new RoleShowDto();
            dto.setId(role.getId());
            dto.setRole(role.getRole());
            return dto;
        };
    }
}
