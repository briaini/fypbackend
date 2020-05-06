package com.example.fypbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.fypbackend.security.UserRole;

//used in UserService
@Repository("myuserdao")
public class MyUserDao implements UserDao {

    @Autowired
    private PersistUserRepository persistUser;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserDao(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectByUsername(String username) {
//        System.out.println("in my user dao selectbyUsername()");
//        getApplicationUsers().forEach((x)->System.out.println(x.getUsername()));
//        System.out.println("printed users");
        return getApplicationUsers()
                .stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<User> getApplicationUsers() {
        Iterable<PersistUser> persistUsers = persistUser.findAll();
        List<User> users = new ArrayList<>();

        persistUsers.forEach((x) ->
                users.add(new User(x.getUsername().trim(), x.getPassword().trim(), UserRole.valueOf(x.getRole()).getGrantedAuthorities(), x.getAccountNonExpired(), x.getAccountNonLocked(), x.getCredentialsNonExpired(), x.getEnabled())));
//            users.add(new User(x.getUsername(), passwordEncoder.encode(x.getPassword()), UserRole.valueOf(x.getRole()).getGrantedAuthorities(), x.getAccountNonExpired(), x.getAccountNonLocked(), x.getCredentialsNonExpired(), x.getEnabled())));

        return users;
    }
}
