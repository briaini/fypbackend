package com.example.fypbackend.security;

//import com.example.fypbackend.auth.ApplicationUserService;

import com.example.fypbackend.auth.PersistUserRepository;
import com.example.fypbackend.auth.UserService;
import com.example.fypbackend.jwt.JwtTokenVerifier;
import com.example.fypbackend.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.fypbackend.security.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    PersistUserRepository persistUserRepository;


    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder,
                             UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*", "/login", "/users/*").permitAll()
                .antMatchers("/posts/*").permitAll()

                .antMatchers("/comments/*").hasAnyRole(ADMIN.name(), MDT.name(), PATIENT.name())
                .antMatchers("/groups/*").hasAnyRole(ADMIN.name(), MDT.name(), PATIENT.name())
//                .antMatchers("/posts/*").hasAnyRole(ADMIN.name(), MDT.name())
                .antMatchers("/mdt/*").hasAnyRole(ADMIN.name(), MDT.name())

                //groupcontroller

                .anyRequest()
                .authenticated();

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        System.out.println("calling daoauthprov()");
        DaoAuthenticationProvider provider = new DaoAuthProv(persistUserRepository);
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        System.out.println("finished daoauthprov()");

        String result = passwordEncoder.encode("myPassword");
        System.out.print("test encoder: "+passwordEncoder.matches("myPassword", result));

        return provider;
    }

//    //how to retrieve users from database
//    @Override
//    @Bean
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        UserDetails brian = User.builder()
//                .username("brian")
//                .password(passwordEncoder.encode("password"))
////                .roles(STUDENT.name./))
////                .roles("STUDENT")
//                .authorities(STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails rose = User.builder()
//                .username("rose")
//                .password(passwordEncoder.encode("password"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails skye = User.builder()
//                .username("skye")
//                .password(passwordEncoder.encode("password"))
////                .roles(AppUserRole.STUDENT.name())
//                .authorities(ASISSTINANTTOTHEADMIN.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(brian, rose);
//    }
}
