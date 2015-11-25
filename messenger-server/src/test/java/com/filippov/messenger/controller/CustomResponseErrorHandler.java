package com.filippov.messenger.controller;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        // do nothing
    }
}
