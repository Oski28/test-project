package com.example.testproject.refreshToken.model_repo;

import com.example.testproject.shared.BaseRepository;
import com.example.testproject.user.model_repo.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);

    List<RefreshToken> getAllByExpiryDateBefore(LocalDateTime localDateTime);
}
