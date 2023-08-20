package com.core.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    DEMO_BAD_REQUEST("BAD_REQUEST", "Bad request. Incorrect method or params!", HttpStatus.BAD_REQUEST),
    DEMO_NOT_FOUND("NOT_FOUND", "Not found. Incorrect endpoint description!", HttpStatus.NOT_FOUND),
    DEMO_USER_NOT_FOUND("USER_NOT_FOUND", "User not found. Incorrect id or name!", HttpStatus.NOT_FOUND),
    DEMO_ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Role not found. Incorrect id or name! ", HttpStatus.NOT_FOUND),
    DEMO_CREDENTIAL_NOT_FOUND("CREDENTIAL_NOT_FOUND", "Credential not found. Incorrect id or name! ", HttpStatus.NOT_FOUND);

    private final String code;
    private final String description;
    private final HttpStatus status;

    ErrorCode(String code, String description, HttpStatus status) {
        this.description = description;
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
