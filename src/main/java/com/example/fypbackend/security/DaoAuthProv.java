package com.example.fypbackend.security;

import com.example.fypbackend.auth.PersistUser;
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
        String requesterUsername = authentication.getPrincipal().toString();

        int userId = persistUserRepository.getUserId(requesterUsername);
        System.out.print("this guys userid is: " + userId);
        if (passwordEncoder.matches((String) authentication.getCredentials(), getUserDetailsService().loadUserByUsername(requesterUsername).getPassword()) && (getUserDetailsService().loadUserByUsername(requesterUsername).isAccountNonLocked())) {
            PersistUser user = persistUserRepository.findById(userId).get();
            System.out.println("the user is: " + user.getId());
            Integer newInt = user.getLoginattempts()+1;
            user.setLoginattempts(newInt);
            return createSuccessAuthentication(authentication.getPrincipal(), authentication, getUserDetailsService().loadUserByUsername(authentication.getPrincipal().toString()));
        } else {
            System.out.println("WRONG PASSWORD");
            //get current count
            //if +1==3 set nonlocked = 0
            PersistUser user = persistUserRepository.findById(userId).get();
            Integer preLoginAttempts = user.getLoginattempts();
            if (preLoginAttempts == 2){
                user.setLoginattempts(0);
                user.setAccountNonLocked(0);
        }else{
                user.setLoginattempts(preLoginAttempts+1);
            }
            persistUserRepository.save(user);
            return null;
        }
//        return super.authenticate(authentication);
    }
}
