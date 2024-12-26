package com.ll.timemateproject.global.exception;

public class PasswordMismatchException extends UserSignupException {
  public PasswordMismatchException() {
    super("비밀번호가 일치하지 않습니다.");
  }
}
