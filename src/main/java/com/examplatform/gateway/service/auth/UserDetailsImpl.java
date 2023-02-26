package com.examplatform.gateway.service.auth;

import com.examplatform.gateway.persistence.entity.Role;
import com.examplatform.gateway.persistence.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String fullName;
    private final String username;
    private final String password;

    private final Set<Role> userRoles;
    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetails build(User user) {
        Collection<GrantedAuthority> authorities = user.getRole()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toSet());

        return UserDetailsImpl.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .password(user.getPassword())
                .userRoles(user.getRole())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
