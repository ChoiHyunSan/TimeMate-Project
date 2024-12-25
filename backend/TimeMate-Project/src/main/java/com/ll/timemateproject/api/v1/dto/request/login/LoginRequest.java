package com.ll.timemateproject.api.v1.dto.request.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
