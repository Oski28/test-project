package com.example.testproject.user.converter;

import com.example.testproject.payload.request.SignupRequest;
import com.example.testproject.role.model_repo.ERole;
import com.example.testproject.role.model_repo.Role;
import com.example.testproject.role.model_repo.RoleRepository;
import com.example.testproject.shared.BaseConverter;
import com.example.testproject.user.model_repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
public class UserConverter extends BaseConverter<User, SignupRequest> {


    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    @Autowired
    public UserConverter(PasswordEncoder encoder, RoleRepository roleRepository) {
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }


    @Override
    public Function<SignupRequest, User> toEntity() {
        return signupRequest -> {
            if (signupRequest == null)
                return null;

            User user = new User();
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setFirstname(signupRequest.getFirstname());
            user.setLastname(signupRequest.getLastname());
            user.setPassword(encoder.encode(signupRequest.getPassword()));
            user.setEnabled(true);
            user.setDeleteDate(null);

            Set<String> strRoles = signupRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role.toUpperCase()) {
                        case "TEACHER":
                            Role teacherRole = roleRepository.findByRole(ERole.ROLE_TEACHER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(teacherRole);
                        case "USER":
                            Role userRole = roleRepository.findByRole(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                            break;
                        default:
                            Role defaultRole = roleRepository.findByRole(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(defaultRole);
                    }
                });
            }
            user.setRoles(roles);
            user.setAddDate(LocalDateTime.now());
            return user;
        };
    }

    @Override
    public Function<User, SignupRequest> toDto() {
        return null;
    }
}
