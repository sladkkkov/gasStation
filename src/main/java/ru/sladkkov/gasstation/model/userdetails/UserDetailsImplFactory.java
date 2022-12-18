package ru.sladkkov.gasstation.model.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.sladkkov.gasstation.model.User;

import java.util.List;

public final class UserDetailsImplFactory {

    private UserDetailsImplFactory() {
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user));
    }

    private static List<SimpleGrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }
}

