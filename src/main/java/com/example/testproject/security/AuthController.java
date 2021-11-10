package com.example.testproject.security;

import com.example.testproject.blackListToken.web.BlackListTokenService;
import com.example.testproject.exceptions.DuplicateObjectException;
import com.example.testproject.exceptions.TokenRefreshException;
import com.example.testproject.payload.request.LoginRequest;
import com.example.testproject.payload.request.LogoutRequest;
import com.example.testproject.payload.request.SignupRequest;
import com.example.testproject.payload.request.TokenRefreshRequest;
import com.example.testproject.payload.response.JwtResponse;
import com.example.testproject.payload.response.MessageResponse;
import com.example.testproject.payload.response.TokenRefreshResponse;
import com.example.testproject.refreshToken.model_repo.RefreshToken;
import com.example.testproject.refreshToken.web.RefreshTokenServiceImplementation;
import com.example.testproject.security.jwt.JwtUtils;
import com.example.testproject.security.services.UserDetailsImplementation;
import com.example.testproject.shared.BaseService;
import com.example.testproject.user.converter.UserConverter;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final BaseService<User> userBaseService;
    private final UserServiceImplementation userService;
    private final JwtUtils jwtUtils;
    private final RefreshTokenServiceImplementation refreshTokenService;
    private final UserConverter userConverter;
    private final BlackListTokenService blackListTokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, BaseService<User> userBaseService,
                          UserServiceImplementation userService, JwtUtils jwtUtils,
                          RefreshTokenServiceImplementation refreshTokenServiceImplementation, UserConverter userConverter,
                          BlackListTokenService blackListTokenService) {
        this.authenticationManager = authenticationManager;
        this.userBaseService = userBaseService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenServiceImplementation;
        this.userConverter = userConverter;
        this.blackListTokenService = blackListTokenService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImplementation userDetails = (UserDetailsImplementation) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getFirstname(), userDetails.getLastname(),
                userDetails.getEmail(), roles));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest) {
        String reguestRefreshToken = refreshRequest.getRefreshToken();

        return refreshTokenService.findByToken(reguestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, reguestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(reguestRefreshToken,
                        "Refresh token wygasł."));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid LogoutRequest logoutRequest) {
        refreshTokenService.deleteByUserId(logoutRequest.getUserId());
        blackListTokenService.create(logoutRequest.getToken(), userService.getById(logoutRequest.getUserId()));
        return ResponseEntity.ok(new MessageResponse("Użytkownik poprawnie wylogowany."));
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> register(@RequestBody @Valid SignupRequest signupRequest) {

        if (userService.existByEmail(signupRequest.getEmail())) {
            throw new DuplicateObjectException("Użytkownik o adresie email " + signupRequest.getEmail() + " istnieje w aplikacji.");
        }

        if (userService.existByUsername(signupRequest.getUsername())) {
            throw new DuplicateObjectException("Użytkownik o nicku " + signupRequest.getUsername() + " istnieje w aplikacji.");
        }

        User savedUser = userBaseService.save(userConverter.toEntity().apply(signupRequest));
        return ResponseEntity.ok(new MessageResponse("Użytkownik poprawnie zarejestrowany."));
    }
}
