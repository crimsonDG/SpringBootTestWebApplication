package com.core.exception.response;

import java.util.Date;

public class ExceptionInfo {
    private Date timestamp;
    private String message;
    private String details;

    private String code;

    public ExceptionInfo(Date timestamp, String message, String details, String code) {
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public String getCode() {
        return code;
    }
}
