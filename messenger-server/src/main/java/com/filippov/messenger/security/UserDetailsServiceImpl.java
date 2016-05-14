package com.filippov.messenger.security;

import com.filippov.messenger.service.user.IUserService;
import com.filippov.messenger.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userService.getUserByEmail(email);
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
