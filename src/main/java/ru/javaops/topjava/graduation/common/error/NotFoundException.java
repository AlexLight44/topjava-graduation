package ru.javaops.topjava.graduation.common.error;

import static ru.javaops.topjava.graduation.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}