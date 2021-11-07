package com.example.testproject.refreshToken.web;

public interface RefreshTokenService {

    void removeExpiredTokens() throws InterruptedException;
}
