package com.ll.timemateproject.global.exception;

import com.ll.timemateproject.api.v1.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public Result<Void> handleResourceNotFoundException(BadCredentialsException e) {
        log.error("인증 관련 입력이 잘못되었습니다. : {}", e.getMessage(), e);
        return Result.error(401, "입력이 잘못되었습니다.");
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e){
        return Result.error(401, "로그인 정보가 잘못되었습니다.");
    }
}
