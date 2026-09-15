package com.github.alexlight44.restaurantvoting.error;

import static com.github.alexlight44.restaurantvoting.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}