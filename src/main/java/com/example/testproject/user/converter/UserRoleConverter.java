package com.example.testproject.user.converter;

import com.example.testproject.role.model_repo.ERole;
import com.example.testproject.role.model_repo.Role;
import com.example.testproject.role.model_repo.RoleRepository;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.user.dto.UserRoleDto;
import com.example.testproject.user.model_repo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRoleConverter extends BaseConverter<User, UserRoleDto> {

    private final RoleRepository roleRepository;

    @Override
    public Function<UserRoleDto, User> toEntity() {
        return dto -> {
            if (dto == null)
                return null;

            User user = new User();

            Set<String> strRoles = dto.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "TEACHER":
                            Role adminRole = roleRepository.findByRole(ERole.ROLE_TEACHER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                        default:
                            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }
            user.setRoles(roles);

            return user;
        };
    }

    @Override
    public Function<User, UserRoleDto> toDto() {
        return user -> {
            if (user == null)
                return null;

            UserRoleDto dto = new UserRoleDto();

            dto.setRoles(user.getRoles().stream().map(Role::getRole).map(Enum::toString).collect(Collectors.toSet()));

            return dto;
        };
    }
}
