package com.filippov.messenger.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

class CustomRestTemplate extends RestTemplate {

    private HttpHeaders headers = new HttpHeaders();

    public CustomRestTemplate() {
        super();
        setErrorHandler(new CustomResponseErrorHandler());
        List<HttpMessageConverter<?>> messageConverters =
                new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        setMessageConverters(messageConverters);
    }

    public void addHttpHeader(String name, String value){
        headers.set(name, value);
    }

    public void clearHttpHeaders() {
        headers.clear();
    }

    public HttpHeaders getHttpHeaders(){
        return headers;
    }
}