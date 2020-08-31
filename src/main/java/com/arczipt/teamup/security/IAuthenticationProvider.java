package com.arczipt.teamup.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationProvider {
    String getUsername();
    Long getId();
}
