package com.ll.timemateproject.api.v1.dto.request.login;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String email;
    private String password;
    private String passwordCheck;
}
