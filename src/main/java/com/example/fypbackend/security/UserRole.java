package com.example.fypbackend.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.fypbackend.security.UserPermission.*;

public enum UserRole {
    PATIENT(Sets.newHashSet(PATIENT_READ, PATIENT_WRITE)),
    ADMIN(Sets.newHashSet(TEST_READ, TEST_WRITE, PATIENT_READ, PATIENT_WRITE)),
    MDT(Sets.newHashSet(PATIENT_READ, PATIENT_WRITE));

    private final Set<UserPermission> permissions;

    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add((new SimpleGrantedAuthority("ROLE_" + name())));
        return permissions;
    }
}
