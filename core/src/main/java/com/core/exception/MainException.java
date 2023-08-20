package com.core.exception;

public class MainException extends RuntimeException {

    private ErrorCode errorCode;

    public MainException() {
        super();
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public MainException(Throwable cause) {
        super(cause);
    }

    public MainException(String message) {
        super(message);
    }

    public MainException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
