package com.example.fypbackend.security;

public enum UserPermission {
    PATIENT_READ("patient:read"),
    PATIENT_WRITE("patient:write"),
    COURSE_READ("course:read"),
    COURSE_WRITE("course:write");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
