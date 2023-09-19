package com.example.lab3.models;

import org.springframework.security.core.GrantedAuthority;

public enum roleEnum implements GrantedAuthority {
    USER, EMPLOYEE, ADMIN;
    @Override
    public String getAuthority()
    {
        return name();
    }
}
