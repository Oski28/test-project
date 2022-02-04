package com.example.testproject.user.web;

import com.example.testproject.exceptions.DuplicateObjectException;
import com.example.testproject.shared.*;
import com.example.testproject.shared.hateoas_response.Embedded;
import com.example.testproject.shared.hateoas_response.PageInfo;
import com.example.testproject.shared.hateoas_response.PaginationAndHateoasResponse;
import com.example.testproject.user.converter.UserRoleConverter;
import com.example.testproject.user.assembler.UserShowDtoAssembler;
import com.example.testproject.user.converter.UserUpdateConverter;
import com.example.testproject.user.dto.UserPasswordDto;
import com.example.testproject.user.dto.UserRoleDto;
import com.example.testproject.user.dto.UserShowDto;
import com.example.testproject.user.dto.UserUpdateDto;
import com.example.testproject.user.model_repo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UserController extends BaseController<User> {

    private final UserServiceImplementation userService;

    private final UserRoleConverter userRoleConverter;

    private final UserUpdateConverter userUpdateConverter;

    private final UserShowDtoAssembler assembler;

    @Autowired
    public UserController(BaseService<User> service, UserServiceImplementation userService,
                          UserRoleConverter userRoleConverter, UserUpdateConverter userUpdateConverter,
                          UserShowDtoAssembler assembler) {
        super(service);
        this.userService = userService;
        this.userRoleConverter = userRoleConverter;
        this.userUpdateConverter = userUpdateConverter;
        this.assembler = assembler;
    }

    /* GET */

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserShowDto> getOne(@PathVariable final Long id) {
        return ResponseEntity.ok(assembler.toModel(userService.getById(id)));
    }

    @GetMapping(value = "", produces = "application/hal+json")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaginationAndHateoasResponse<UserShowDto>> getAll(@RequestParam(defaultValue = "0") final int page,
                                                                            @RequestParam(defaultValue = "20") final int size,
                                                                            @RequestParam(defaultValue = "id") final String column,
                                                                            @RequestParam(defaultValue = "ASC") final String direction,
                                                                            @RequestParam(defaultValue = "") final String filter) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<User> userPage;
        CollectionModel<UserShowDto> collectionModel;
        if (filter.equals("")) {
            userPage = this.userService.getAll(page, size, column, sortDir);
            collectionModel = assembler.toCollectionModel(userPage, null);
        } else {
            userPage = this.userService.getAllWithFilter(page, size, column, sortDir, filter);
            collectionModel = assembler.toCollectionModel(userPage, filter);
        }
        return new ResponseEntity<>(
                new PaginationAndHateoasResponse<>(
                        new Embedded<>(collectionModel.getContent(), new PageInfo(userPage)),
                        collectionModel.getLinks().toList()), HttpStatus.OK);
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
