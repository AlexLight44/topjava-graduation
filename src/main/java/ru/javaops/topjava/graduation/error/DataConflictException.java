package ru.javaops.topjava.graduation.error;

import static ru.javaops.topjava.graduation.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}