package com.cafe.review.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //401
    INVALID_PASSWORD(UNAUTHORIZED, "유효하지 않은 비밀번호"),
    //404
    CAFE_NOT_FOUND(NOT_FOUND, "없는 카페"),
    DIRECTION_NOT_FOUND(NOT_FOUND, "없는 거리 검색"),
    REVIEW_NOT_FOUND(NOT_FOUND, "없는 리뷰"),
    //500
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 에러");

    private final HttpStatus status;
    private final String message;
}
