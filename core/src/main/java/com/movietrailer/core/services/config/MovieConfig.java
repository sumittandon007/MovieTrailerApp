package com.movietrailer.core.services.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Movie Trailer Config", description = "Configure Movie Trailer Service")
public @interface MovieConfig {

    // k_h65rtjr7 k_rua1ha8j k_j5z8qim6 k_sd4kzqwb
    @AttributeDefinition(name = "IMDB API Key", description = "Enter IMDB API Key", type = AttributeType.STRING)
    String imdbApiKey() default "k_h65rtjr7";

    @AttributeDefinition(name = "IMDB API Endpoint", description = "Enter IMDB API Endpoint", type = AttributeType.STRING)
    String imdbApiEndpoint() default "https://imdb-api.com/en/API";

    @AttributeDefinition(name = "Result Limit", description = "Result Limit", type = AttributeType.INTEGER)
    int limit() default 10;
}
