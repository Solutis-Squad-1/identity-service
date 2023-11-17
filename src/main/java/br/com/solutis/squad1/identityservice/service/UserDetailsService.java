package br.com.solutis.squad1.identityservice.service;

import br.com.solutis.squad1.identityservice.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Find user by username
     * @param username
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Finding user by username {}", username);
        return userRepository.findByUsername(username);
    }
}
