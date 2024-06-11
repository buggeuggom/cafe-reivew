package com.cafe.review.exception;

import com.cafe.review.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cafe.review.exception.ErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        log.error("[에러][MethodArgumentNotValidException] -> {}", e.toString());

        var response = ErrorResponse.builder()
                .code(400)
                .message("잘못된 요청입니다.")
                .build();

        for (FieldError fieldError : e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ErrorResponse> applicationHandler(ReviewException e) {
        log.error("[에러][ReviewException] -> {}", e.toString());
        var code = e.getErrorCode().getStatus().value();

        return ResponseEntity.status(code)
                .body(ErrorResponse.builder()
                        .code(code)
                        .message(e.getMessage())
                        .build());
    }
}
