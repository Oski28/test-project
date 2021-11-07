package com.example.testproject.blackListToken.web;

import com.example.testproject.blackListToken.model_repo.BlackListToken;
import com.example.testproject.blackListToken.model_repo.BlackListTokenRepository;
import com.example.testproject.user.model_repo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListTokenServiceImplementation implements BlackListTokenService {

    private final BlackListTokenRepository blackListTokenRepository;

    @Value("${com.example.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Override
    public BlackListToken create(String token, User user) {
        BlackListToken blackListToken = new BlackListToken();
        blackListToken.setToken(token);
        blackListToken.setUser(user);
        blackListToken.setExpiryDate(LocalDateTime.now().plusSeconds(jwtExpirationMs / 1000));
        return this.blackListTokenRepository.save(blackListToken);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public void removeExpiredTokens() {
        List<BlackListToken> blackListTokenList = this.blackListTokenRepository
                .getAllByExpiryDateBefore(LocalDateTime.now());

        blackListTokenList.forEach(
                blackListToken -> {
                    if (blackListToken.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
                        this.blackListTokenRepository.delete(blackListToken);
                    }
                }
        );
    }

    @Override
    public Boolean isExistByToken(String token) {
        return this.blackListTokenRepository.existsByToken(token);
    }
}
