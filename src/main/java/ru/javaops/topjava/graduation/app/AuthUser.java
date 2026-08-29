package ru.javaops.topjava.graduation.app;

import lombok.Getter;
import org.springframework.lang.NonNull;
import ru.javaops.topjava.graduation.common.model.Role;
import ru.javaops.topjava.graduation.common.model.User;

@Getter
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public int id() {
        return user.id();
    }

    public boolean hasRole(Role role) {
        return user.hasRole(role);
    }

    @Override
    public String toString() {
        return "AuthUser:" + id() + '[' + user.getEmail() + ']';
    }
}