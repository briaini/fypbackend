package com.example.fypbackend.security;

public enum UserPermission {
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    TEST_READ("test:read"),
    TEST_WRITE("test:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
