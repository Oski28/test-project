package com.example.testproject.user.model_repo;

import com.example.testproject.shared.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Page<User> findAllByFirstnameContainsAndEnabledTrueAndUsernameNotOrLastnameContainsAndEnabledTrueAndUsernameNot(String firstname, String username1, String lastname, String username2, Pageable pageable);

}
