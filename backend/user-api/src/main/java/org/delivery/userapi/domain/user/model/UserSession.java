package org.delivery.userapi.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.user.enums.UserRole;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSession implements UserDetails {

    private Long id;

    private String email;

    private String password;

    private String name;

    private UserStatus status;

    private UserRole role;

    private String address;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private LocalDateTime lastLoginAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == UserStatus.REGISTERED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == UserStatus.REGISTERED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == UserStatus.REGISTERED;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
