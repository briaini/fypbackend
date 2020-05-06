package com.example.fypbackend.security;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DaoAuthProv extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()) == null)
            return null;
        PasswordEncoder passwordEncoder = getPasswordEncoder();

        if (passwordEncoder.matches((String) authentication.getCredentials(),getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()).getPassword()))
            return createSuccessAuthentication(authentication.getPrincipal(), authentication, getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()));
        else return null;
//        return super.authenticate(authentication);
    }
}
