package com.project.library.global.utils;

import com.project.library.global.enums.Errors;
import com.project.library.global.exception.DuplicationDataException;
import com.project.library.global.exception.EntityNotFoundException;
import com.project.library.global.exception.ErrorResponse;
import com.project.library.global.exception.ReferentialIntegrityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionControllerAdvice {

    // 엔티티 notFound exception 처리
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity handlerEntityNotFoundException(EntityNotFoundException e) {
        Errors error = e.getErrors();
        String errorMessage = Optional.ofNullable(e.getLocalizedMessage()).orElse(error.getMessage());
        String errorCode = error.getCode();
        HttpStatus statusCode = error.getHttpStatus();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorMessage);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    // 중복 데이터 exception 처리
    @ExceptionHandler(DuplicationDataException.class)
    protected ResponseEntity handlerDuplicationDataException(DuplicationDataException e) {
        Errors error = e.getErrors();
        String errorMessage = Optional.ofNullable(e.getLocalizedMessage()).orElse(error.getMessage());
        String errorCode = error.getCode();
        HttpStatus statusCode = error.getHttpStatus();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorMessage);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    // 입력값 검증 exception 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();

        Errors errors = Errors.BAD_REQUEST_INVALID_VALUE;
        e.getBindingResult().getFieldErrors()
                .stream()
                .forEach(fieldError -> {
                    String error = fieldError.getDefaultMessage();
                    errorMessages.add(error);
                });

        ErrorResponse errorResponse = ErrorResponse.of(errors.getCode(), errorMessages.stream().collect(Collectors.joining(",")));

        return ResponseEntity.status(errors.getHttpStatus()).body(errorResponse);
    }

    // 외래키 삭제 exception 처리
    @ExceptionHandler(ReferentialIntegrityException.class)
    protected ResponseEntity handlerReferentialIntegrityException(ReferentialIntegrityException e) {
        Errors error = e.getErrors();
        String errorMessage = Optional.ofNullable(e.getLocalizedMessage()).orElse(error.getMessage());
        String errorCode = error.getCode();
        HttpStatus statusCode = error.getHttpStatus();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, errorMessage);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}
