package ru.javaops.topjava.graduation.user;

import ru.javaops.topjava.graduation.common.model.User;

public class UserTestData {

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final String USER_PASSWORD = "password";
    public static final String ADMIN_PASSWORD = "admin";

    public static final User user = new User();
    public static final User admin = new User();

}
