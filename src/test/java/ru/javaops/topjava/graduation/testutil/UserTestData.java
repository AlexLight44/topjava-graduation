package ru.javaops.topjava.graduation.testutil;

import ru.javaops.topjava.graduation.model.Role;
import ru.javaops.topjava.graduation.model.User;

public class UserTestData {

    public static final int USER_ID = 1;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
}
