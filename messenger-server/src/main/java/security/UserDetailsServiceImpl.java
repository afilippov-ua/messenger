package security;

import dao.user.IUserDao;
import dao.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Inject
    IUserDao userDao;

    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.getUserByEmail(email);

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
