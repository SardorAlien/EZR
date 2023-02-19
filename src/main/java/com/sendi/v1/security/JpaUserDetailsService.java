package com.sendi.v1.security;

import com.sendi.v1.security.domain.Authority;
import com.sendi.v1.security.domain.User;
import com.sendi.v1.security.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User name: " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                convertToSpringAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        if (authorities != null && authorities.size() > 0) {
            Set<SimpleGrantedAuthority> result = new HashSet<>();
            for (Authority items : authorities) {
                result.add(new SimpleGrantedAuthority(items.getRole()));
            }
            return result;

//            the above code works too
//            return authorities.stream()
//                    .map(Authority::getRole)
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}
