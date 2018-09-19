package com.polling.polling_project.domain;

import org.springframework.security.core.GrantedAuthority;

public enum EUserRole implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
