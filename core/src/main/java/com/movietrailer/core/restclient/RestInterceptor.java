package com.movietrailer.core.restclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class RestInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        requestTemplate.header("User-Agent", "AEM/ServerBackend");
    }
}
