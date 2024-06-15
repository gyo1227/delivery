package org.delivery.storeapi.domain.storeuser.service;

import lombok.RequiredArgsConstructor;
import org.delivery.storeapi.domain.storeuser.model.StoreUserSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final StoreUserService storeUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var userEntity = storeUserService.getRegisterUser(username);

        return userEntity.map(it -> {
            var userSession = StoreUserSession.builder()
                    .id(it.getId())
                    .email(it.getEmail())
                    .password(it.getPassword())
                    .name(it.getName())
                    .status(it.getStatus())
                    .role(it.getRole())
                    .registeredAt(it.getRegisteredAt())
                    .lastLoginAt(it.getLastLoginAt())
                    .unregisteredAt(it.getUnregisteredAt())
                    .build();
            return userSession;
        })
        .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
