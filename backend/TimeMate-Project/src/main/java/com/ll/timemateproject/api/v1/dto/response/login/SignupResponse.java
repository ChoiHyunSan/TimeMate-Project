package com.ll.timemateproject.api.v1.dto.response.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse {
    private String username;
    private String email;
}
