package org.delivery.userapi.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.userapi.domain.user.model.UserSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userEntity = userService.getRegisterUser(username);

        return userEntity.map(it -> {
            var userSession = UserSession.builder()
                    .id(it.getId())
                    .email(it.getEmail())
                    .password(it.getPassword())
                    .name(it.getName())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .address(it.getAddress())
                    .registeredAt(it.getRegisteredAt())
                    .lastLoginAt(it.getLastLoginAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .build();

            return userSession;
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
