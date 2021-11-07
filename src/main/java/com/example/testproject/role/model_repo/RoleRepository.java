package com.example.testproject.role.model_repo;

import com.example.testproject.shared.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByRole(ERole role);
}
