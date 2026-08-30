package ru.javaops.topjava.graduation.error;

import static ru.javaops.topjava.graduation.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}