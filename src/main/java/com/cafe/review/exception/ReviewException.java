package com.cafe.review.exception;

import lombok.Getter;

public class ReviewException extends RuntimeException{


    @Getter
    private ErrorCode errorCode;
    private String message;

    public ReviewException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ReviewException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if (message == null){
            return errorCode.getMessage();
        }

        return message;
    }
}
