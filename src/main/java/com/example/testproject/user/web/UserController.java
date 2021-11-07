package com.example.testproject.user.web;

import com.example.testproject.exceptions.DuplicateObjectException;
import com.example.testproject.payload.request.SignupRequest;
import com.example.testproject.role.web.RoleService;
import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import com.example.testproject.user.converter.UserConverter;
import com.example.testproject.user.converter.UserRoleConverter;
import com.example.testproject.user.converter.UserShowConverter;
import com.example.testproject.user.converter.UserUpdateConverter;
import com.example.testproject.user.dto.UserPasswordDto;
import com.example.testproject.user.dto.UserRoleDto;
import com.example.testproject.user.dto.UserShowDto;
import com.example.testproject.user.dto.UserUpdateDto;
import com.example.testproject.user.model_repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UserController extends BaseController<User> {

    private final UserServiceImplementation userService;

    private final UserConverter userConverter;

    private final UserShowConverter userShowConverter;

    private final RoleService roleService;

    private final UserRoleConverter userRoleConverter;

    private final UserUpdateConverter userUpdateConverter;

    @Autowired
    public UserController(BaseService<User> service, UserServiceImplementation userService,
                          UserConverter userConverter, UserShowConverter userShowConverter,
                          RoleService roleService, UserRoleConverter userRoleConverter,
                          UserUpdateConverter userUpdateConverter) {
        super(service);
        this.userService = userService;
        this.userConverter = userConverter;
        this.userShowConverter = userShowConverter;
        this.roleService = roleService;
        this.userRoleConverter = userRoleConverter;
        this.userUpdateConverter = userUpdateConverter;
    }

    /* GET */

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserShowDto> getOne(@PathVariable final Long id) {
        return super.getOne(id, this.userShowConverter.toDto());
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<UserShowDto>> getAll(@RequestParam(defaultValue = "0") final int page,
                                                    @RequestParam(defaultValue = "20") final int size,
                                                    @RequestParam(defaultValue = "id") final String column,
                                                    @RequestParam(defaultValue = "ASC") final String direction,
                                                    @RequestParam(defaultValue = "") final String filter) {
        if (filter.equals("")) {
            Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Page<User> userPage = this.userService.getAll(page, size, column, sortDir);
            return ResponseEntity.ok(userPage.map(this.userShowConverter.toDto()));
        } else {
            return ResponseEntity.ok(this.userService.getAllWithFilter(page, size, column, direction, filter)
                    .map(this.userShowConverter.toDto()));
        }
    }

    /* POST */

    /* PUT */

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id and hasRole('USER')")
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody @Valid final UserUpdateDto dto) {
        if (this.userService.existByEmail(dto.getEmail()) && !this.userService.getById(id).getEmail().equals(dto.getEmail())) {
            throw new DuplicateObjectException("Użytkownik o adresie email "
                    + dto.getEmail() + " istnieje w aplikacji");
        }
        if (this.userService.existByUsername(dto.getUsername()) && !this.userService.getById(id).getUsername().equals(dto.getUsername())) {
            throw new DuplicateObjectException("Użytkownik o nicku "
                    + dto.getUsername() + " istnieje w aplikacji");
        }
        return super.update(id, this.userUpdateConverter.toEntity().apply(dto));
    }

    /* PATCH */

    @PatchMapping("/{id}/password")
    @PreAuthorize("#id == authentication.principal.id and hasRole('USER')")
    public ResponseEntity<Void> updatePassword(@PathVariable final Long id, @RequestBody @Valid final UserPasswordDto dto) {
        if (this.userService.updatePassword(id, dto)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateRoles(@PathVariable final Long id, @RequestBody final UserRoleDto dto) {
        if (this.userService.updateRoles(id, this.userRoleConverter.toEntity().apply(dto)))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    /* DELETE */

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id and hasRole('USER')")
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        if (this.userService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
