package com.easyreach.backend.auth.service;

import com.easyreach.backend.auth.entity.UserAdapter;
import com.easyreach.backend.entity.User;
import com.easyreach.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // <-- this registers a UserDetailsService bean
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // username is email here
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Entering loadUserByUsername with email={}", email);
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.warn("User not found: {}", email);
                    return new UsernameNotFoundException("User not found: " + email);
                });
        UserAdapter adapter = new UserAdapter(user);
        log.debug("Exiting loadUserByUsername with userId={}", user.getId());
        return adapter;
    }
}
