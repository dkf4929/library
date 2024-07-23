package com.project.library.global.exception;

import com.project.library.global.enums.Errors;
import lombok.Getter;

@Getter
public class ReferentialIntegrityException extends RuntimeException {
    private final Errors errors = Errors.REFERENTIAL_DATA;
    public ReferentialIntegrityException(String message) {
        super(message);
    }
}
