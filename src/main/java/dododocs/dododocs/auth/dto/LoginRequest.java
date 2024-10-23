package dododocs.dododocs.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private final String code;

    public LoginRequest(final String code) {
        this.code = code;
    }
}
