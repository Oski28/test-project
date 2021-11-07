package com.example.testproject.blackListToken.web;

import com.example.testproject.blackListToken.model_repo.BlackListToken;
import com.example.testproject.user.model_repo.User;

public interface BlackListTokenService {

    BlackListToken create(String token, User user);

    void removeExpiredTokens();

    Boolean isExistByToken(String token);
}
