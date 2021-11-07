package com.example.testproject.role.web;

import com.example.testproject.role.model_repo.ERole;
import com.example.testproject.role.model_repo.Role;
import com.example.testproject.role.model_repo.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByRole(ERole eRole) {
        Optional<Role> role = roleRepository.findByRole(eRole);
        return role.orElse(null);
    }
}
