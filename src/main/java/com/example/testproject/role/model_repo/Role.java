package com.example.testproject.role.model_repo;


import com.example.testproject.shared.BaseEntity;
import com.example.testproject.user.model_repo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true, doNotUseGetters = true)
@ToString(callSuper = true, doNotUseGetters = true)
@Entity(name = "role")
@Table(name = "role")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", unique = true, nullable = false, length = 20)
    private ERole role;

    /* RELATIONS */

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<User> users;
}
