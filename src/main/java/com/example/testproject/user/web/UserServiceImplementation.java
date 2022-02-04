package com.example.testproject.user.web;

import com.example.testproject.exceptions.OldPasswordMismatchException;
import com.example.testproject.refreshToken.web.RefreshTokenServiceImplementation;
import com.example.testproject.shared.BaseService;
import com.example.testproject.user.dto.UserPasswordDto;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.model_repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;


@Service
public class UserServiceImplementation implements UserService, BaseService<User> {

    private final UserRepository userRepository;

    private RefreshTokenServiceImplementation refreshTokenService;

    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Autowired
    public void setRefreshTokenService(RefreshTokenServiceImplementation refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updatePassword(Long id, UserPasswordDto dto) {
        if (isExists(id)) {
            User userById = getById(id);
            if (encoder.matches(dto.getOldPassword(), userById.getPassword())) {
                userById.setPassword(encoder.encode(dto.getNewPassword()));
            } else throw new OldPasswordMismatchException("Podane aktualne hasło nie jest poprawne");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean updateRoles(Long id, User userRole) {
        if (isExists(id)) {
            User userById = getById(id);
            userById.setRoles(userRole.getRoles());
            return true;
        }
        return false;
    }

    @Override
    public Page<User> getAll(int page, int size, String column, Sort.Direction direction) {
        Sort sort = Sort.by(new Sort.Order(direction, column));
        return this.userRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean update(Long id, User entity) {
        if (isExists(id)) {
            User user = getById(id);

            user.setEmail(entity.getEmail());
            user.setFirstname(entity.getFirstname());
            user.setLastname(entity.getLastname());
            user.setUsername(entity.getUsername());

            return true;
        } else return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
    public boolean delete(Long id) {
        if (isExists(id)) {
            User user = getById(id);
            user.setDeleteDate(LocalDateTime.now());
            user.setEnabled(false);
            this.refreshTokenService.deleteByUserId(user.getId());
            return true;
        } else return false;
    }

    @Override
    public User save(User entity) {
        return this.userRepository.save(entity);
    }

    @Override
    public User getById(Long id) {
        return this.userRepository.getById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }


    @Override
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(authentication.getName());
    }


    public Page<User> getAllWithFilter(int page, int size, String column, Sort.Direction direction, String filter) {
        Sort sort = Sort.by(new Sort.Order(direction, column));
        User user = getAuthUser();
        return this.userRepository.findAllByFirstnameContainsAndEnabledTrueAndUsernameNotOrLastnameContainsAndEnabledTrueAndUsernameNot(
                filter, user.getUsername(), filter, user.getUsername(), PageRequest.of(page, size, sort
                ));
    }

}
