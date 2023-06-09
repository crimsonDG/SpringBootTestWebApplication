package com.core.exception.handler;

import com.core.exception.BadRequestException;
import com.core.exception.NotFoundException;
import com.core.exception.response.ExceptionInfo;
import org.apache.hc.client5.http.HttpHostConnectException;
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ExceptionInfo> handleNotFoundException(NotFoundException e,
                                                                 WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ExceptionInfo> handleBadRequestException(NotFoundException e,
                                                                   WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionInfo> handleGlobalException(Exception e,
                                                               WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(),e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(NOT_ACCEPTABLE)
    public ResponseEntity<ExceptionInfo> handleConnectException(ConnectException e,
                                                                WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, NOT_ACCEPTABLE);
    }

    @ExceptionHandler(HttpHostConnectException.class)
    @ResponseStatus(OK)
    public ResponseEntity<ExceptionInfo> handleHttpHostConnectException(Exception e,
                                                                WebRequest webRequest) {
        ExceptionInfo errorDetails = new ExceptionInfo(new Date(), e.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, OK);
    }
}
