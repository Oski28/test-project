package com.example.testproject.user.dto;

import com.example.testproject.role.dto.RoleShowDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserShowDto extends RepresentationModel<UserShowDto> {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Set<RoleShowDto> roles;
}
