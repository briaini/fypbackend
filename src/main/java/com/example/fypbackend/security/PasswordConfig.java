package com.example.fypbackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10, SecureRandom.getInstance("SHA1PRNG"));

        String result = encoder.encode("testing");
        assert encoder.matches("testing", result);

        return encoder;
    }
}
