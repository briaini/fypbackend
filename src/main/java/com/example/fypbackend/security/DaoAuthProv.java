package com.example.fypbackend.security;

import com.example.fypbackend.auth.PersistUserRepository;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DaoAuthProv extends DaoAuthenticationProvider {
    private final PersistUserRepository persistUserRepository;

    public DaoAuthProv(PersistUserRepository persistUserRepository) {
        this.persistUserRepository = persistUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()) == null)
            return null;
        PasswordEncoder passwordEncoder = getPasswordEncoder();

        if (passwordEncoder.matches((String) authentication.getCredentials(),getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()).getPassword())) {
            persistUserRepository.getUserId(authentication.getPrincipal().toString());
            return createSuccessAuthentication(authentication.getPrincipal(), authentication, getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()));

        }
        else return null;
//        return super.authenticate(authentication);
    }
}
