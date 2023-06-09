package com.core.client.config;

import com.core.exception.BadRequestException;
import com.core.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class AuthFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad request. Incorrect method or params!");
            case 404 -> new NotFoundException("Not found. Incorrect uri!");
            default -> new Exception("Internal Server Error. Something went wrong...");
        };
    }
}
