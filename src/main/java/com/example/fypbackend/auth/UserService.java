package com.example.fypbackend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("myuserdao") UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("loading by username "+ username);

        System.out.println("user service load user " + userDao.selectByUsername(username).get().getUsername()+ " " +userDao.selectByUsername(username).get().getPassword());
        return userDao.selectByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %S not found ", username)));
    }
}
