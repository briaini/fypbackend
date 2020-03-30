package com.example.fypbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.fypbackend.security.UserRole;

import static com.example.fypbackend.security.UserRole.ADMIN;
import static com.example.fypbackend.security.UserRole.PATIENT;
import static com.example.fypbackend.security.UserRole.ASISSTINANTTOTHEADMIN;

@Repository("fake")
public class FakeUserDaoService implements UserDao {

    @Autowired
    private PersistUserRepository persistUser;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<User> getApplicationUsers() {
        Iterable<PersistUser> persistUsers = persistUser.findAll();
        List<User> users = new ArrayList<>();

        persistUsers.forEach((x) ->
            users.add(new User(x.getUsername(), passwordEncoder.encode(x.getPassword()), UserRole.valueOf(x.getRole()).getGrantedAuthorities(), x.getAccountNonExpired(), x.getAccountNonLocked(), x.getCredentialsNonExpired(), x.getEnabled())));

//        List<User> users = Lists.newArrayList(
//                 new User(
//                        "brian",
//                        passwordEncoder.encode("password"),
//                        PATIENT.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                        ),
//                new User(
//                        "rose",
//                        passwordEncoder.encode("password"),
//                        ADMIN.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                        ),
//                new User(
//                        "skye",
//                        passwordEncoder.encode("password"),
//                        ASISSTINANTTOTHEADMIN.getGrantedAuthorities(),
//                        true,
//                        true,
//                        true,
//                        true
//                        )
//        );

        return users;
    }
}
