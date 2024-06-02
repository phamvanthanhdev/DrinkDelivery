package com.phuclong.milktea.milktea.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
