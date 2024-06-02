package com.phuclong.milktea.milktea.response;

import com.phuclong.milktea.milktea.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
