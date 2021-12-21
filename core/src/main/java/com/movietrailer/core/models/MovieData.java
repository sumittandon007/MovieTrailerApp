package com.movietrailer.core.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieData {

    @JsonProperty("id")
    @JsonAlias("imDbId")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("rank")
    private String rank;

    @JsonProperty("year")
    private String year;

    @JsonProperty("image")
    private String image;

    @JsonProperty("videoUrl")
    private String videoUrl;
}
