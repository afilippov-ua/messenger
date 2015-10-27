package com.filippov.messenger.security;

import com.filippov.messenger.api.user.IUserService;
import com.filippov.messenger.entity.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Inject
    IUserService userService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ResponseEntity<List<User>> responseEntity = userService.getUsers(email);
        User user =  (responseEntity.getBody() == null || responseEntity.getBody().size() == 0) ? null : responseEntity.getBody().get(0);

        if (user != null) {

            Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));

            UserDetails userDetails =
                    new org.springframework.security.core.userdetails.User(user.getEmail(),
                            user.getPassword(),
                            roles);

            return userDetails;

        } else {
            throw new UsernameNotFoundException("No user with email '" + email + "' found!");
        }
    }
}
