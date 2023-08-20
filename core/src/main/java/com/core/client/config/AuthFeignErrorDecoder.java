package com.core.client.config;

import com.core.exception.ErrorCode;
import com.core.exception.MainException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class AuthFeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new MainException(ErrorCode.DEMO_BAD_REQUEST);
            case 404 -> new MainException(ErrorCode.DEMO_NOT_FOUND);
            default -> new Exception("Internal Server Error. Something went wrong...");
        };
    }
}
