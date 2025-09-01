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

    // identifier can be email, mobile number, or immutable user ID
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        log.debug("Entering loadUserByUsername with identifier={}", identifier);
        User user = userRepository.findByEmailIgnoreCase(identifier)
                .orElseGet(() -> userRepository.findByMobileNo(identifier)
                        .orElseGet(() -> userRepository.findById(identifier)
                                .orElseThrow(() -> {
                                    log.warn("User not found: {}", identifier);
                                    return new UsernameNotFoundException("User not found: " + identifier);
                                })));

        UserAdapter adapter = new UserAdapter(user);
        log.debug("Exiting loadUserByUsername with userId={}", user.getId());
        return adapter;
    }
}
