package com.core.exception.handler;

import com.core.exception.MainException;
import com.core.exception.response.ExceptionInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.ConnectException;
import java.util.Date;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MainException.class)
    public ResponseEntity<ExceptionInfo> handleMainException(MainException e,
                                                                 WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false), e.getErrorCode().getCode());
        return new ResponseEntity<>(errorDetails, e.getErrorCode().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInfo> handleGlobalException(Exception e,
                                                               WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(),e.getMessage(),
                webRequest.getDescription(false), "500");
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ExceptionInfo> handleConnectException(ConnectException e,
                                                                WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false), "406");
        return new ResponseEntity<>(errorDetails, NOT_ACCEPTABLE);
    }
}
