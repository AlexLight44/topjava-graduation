package com.github.alexlight44.restaurantvoting.error;

import static com.github.alexlight44.restaurantvoting.error.ErrorType.BAD_REQUEST;

public class IllegalRequestDataException extends AppException {
    public IllegalRequestDataException(String msg) {
        super(msg, BAD_REQUEST);
    }
}