package com.example.fypbackend.jwt;

import com.example.fypbackend.auth.PersistUser;
import com.example.fypbackend.auth.PersistUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Autowired
    PersistUserRepository persistUserRepository;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
            System.out.println("in auth filter: ");
            System.out.println(authenticationRequest.getUsername());
            System.out.println(authenticationRequest.getPassword());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );
            System.out.println("before authenticate");

            Authentication authenticate = authenticationManager.authenticate(authentication);
            System.out.println(authenticate.isAuthenticated());
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
//        System.out.println("\n AuthResult: " +authResult);
        String key = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
        System.out.println(authResult.getName());
        System.out.println(authResult.getName());
        System.out.println(authResult.getName());
        System.out.println(authResult.getName());

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        response.addHeader("Authorization", "Bearer " + token);

//        response.getWriter().write(userRepository.getUserId(authResult.getName()));
//        response.getWriter().flush();
//        response.getWriter().close();

//        System.out.println("username: " + authResult.getName().trim());
//        System.out.println();

//        String test = persistUserRepository.getUserId("rose");

//        System.out.println("userID: " + userRepository.getUserId(authResult.getName()));
//        .setStatus(HttpServletResponse.SC_OK);

//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
