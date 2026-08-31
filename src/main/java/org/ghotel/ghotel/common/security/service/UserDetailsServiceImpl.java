package org.ghotel.ghotel.common.security.service;

import org.ghotel.ghotel.entity.User;
import org.ghotel.ghotel.repository.UserRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Employee with username: " + username + " not found."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .roles(user.getRole().name())
                .build();

    }
}
