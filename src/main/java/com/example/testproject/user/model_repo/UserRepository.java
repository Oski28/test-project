package com.example.testproject.user.model_repo;

import com.example.testproject.shared.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}
