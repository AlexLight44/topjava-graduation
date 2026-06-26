package ru.javaops.topjava.graduation.common.error;

import static ru.javaops.topjava.graduation.common.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}