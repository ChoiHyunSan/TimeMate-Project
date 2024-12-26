package com.ll.timemateproject.global.exception;

public class DuplicateUsernameException extends UserSignupException {
    public DuplicateUsernameException() {
        super("이미 사용중인 사용자 이름입니다.");
    }
}
