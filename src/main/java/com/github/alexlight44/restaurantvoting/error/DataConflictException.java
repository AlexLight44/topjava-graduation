package com.github.alexlight44.restaurantvoting.error;

import static com.github.alexlight44.restaurantvoting.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}